package ru.ulmc.school.storm.spouts;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import ru.ulmc.school.storm.jms.*;

import javax.jms.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static ru.ulmc.school.storm.Names.Fields.TWEET_FIELD;

public class ArtemisSpout extends BaseRichSpout implements MessageListener {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ArtemisSpout.class);
    private final Object recoveryMutex = BigDecimal.valueOf(Long.parseLong("100000000"));
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private final TreeSet<JmsMessageId> toCommit = new TreeSet<>();
    private final HashMap<JmsMessageId, Message> pendingMessages = new HashMap<>();
    private int jmsAcknowledgeMode = Session.AUTO_ACKNOWLEDGE;
    private JmsContextHolder jmsContextHolder;
    private JmsToTupleConverter converter;
    private SpoutOutputCollector collector;
    private transient Connection connection;
    private transient Session session;
    private boolean hasFailures = false;
    private Timer recoveryTimer = null;
    private long recoveryPeriod = -1; // default to disabled
    private long messageSequence = 0;

    private static final String toDeliveryModeString(int deliveryMode) {
        switch (deliveryMode) {
            case Session.AUTO_ACKNOWLEDGE:
                return "AUTO_ACKNOWLEDGE";
            case Session.CLIENT_ACKNOWLEDGE:
                return "CLIENT_ACKNOWLEDGE";
            case Session.DUPS_OK_ACKNOWLEDGE:
                return "DUPS_OK_ACKNOWLEDGE";
            default:
                return "UNKNOWN";
        }
    }

    @Override
    public void close() {
        try {
            this.connection.close();
            this.session.close();
        } catch (JMSException e) {
            log.warn("Exception {}", e.getMessage());
        }
    }

    /*
     * Will only be called if we're transactional or not AUTO_ACKNOWLEDGE
     */
    public void ack(Object msgId) {
        Message msg = this.pendingMessages.remove(msgId);
        JmsMessageId oldest = this.toCommit.first();
        if (msgId.equals(oldest)) {
            if (msg != null) {
                try {
                    log.debug("Committing...");
                    msg.acknowledge();
                    log.debug("JMS Message acked: " + msgId);
                    this.toCommit.remove(msgId);
                } catch (JMSException e) {
                    log.warn("Error acknowldging JMS message: " + msgId, e);
                }
            } else {
                log.warn("Couldn't acknowledge unknown JMS message ID: " + msgId);
            }
        } else {
            this.toCommit.remove(msgId);
        }

    }

    /*
     * Will only be called if we're transactional or not AUTO_ACKNOWLEDGE
     */
    public void fail(Object msgId) {
        log.warn("Message failed: " + msgId);
        this.pendingMessages.clear();
        this.toCommit.clear();
        synchronized (this.recoveryMutex) {
            this.hasFailures = true;
        }
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }

    @Override
    public void onMessage(Message message) {
        try {
            log.debug("Queuing message {}", message.getJMSMessageID());
        } catch (JMSException e) {
            log.error("on handling JMS message error", e);
        }
        this.queue.offer(message);
    }

    @Override
    public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        this.jmsContextHolder = new DefaultJmsHolder();
        this.converter = new DefaultConverter();
        Long topologyTimeout = (Long) conf.get("topology.message.timeout.secs");
        topologyTimeout = topologyTimeout == null ? 30 : topologyTimeout;
        if ((topologyTimeout * 1000) > this.recoveryPeriod) {
            log.warn("*** WARNING *** : " +
                    "Recovery period (" + this.recoveryPeriod + " ms.) is less then the configured " +
                    "'topology.message.timeout.secs' of " + topologyTimeout +
                    " secs. This could lead to a message replay flood!");
        }
        try {
            ConnectionFactory cf = jmsContextHolder.getConnectionFactory();
            Destination dest = jmsContextHolder.getDestination();
            this.connection = cf.createConnection();
            this.session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(dest);
            consumer.setMessageListener(this);
            this.connection.start();

        } catch (Exception e) {
            log.warn("Error creating JMS connection.", e);
        }

    }

    @Override
    public void nextTuple() {
        Message msg = this.queue.poll();
        if (msg == null) {
            Utils.sleep(50);
        } else {

            log.debug("sending tuple: " + msg);
            // get the tuple from the handler
            try {
                Values vals = this.converter.convert(msg);
                // ack if we're not in AUTO_ACKNOWLEDGE mode, or the message requests ACKNOWLEDGE
                log.debug("Requested deliveryMode: " + toDeliveryModeString(msg.getJMSDeliveryMode()));
                log.debug("Our deliveryMode: " + toDeliveryModeString(this.jmsAcknowledgeMode));
                if (this.isDurableSubscription()) {
                    log.debug("Requesting acks.");
                    JmsMessageId messageId = new JmsMessageId(this.messageSequence++, msg.getJMSMessageID());
                    this.collector.emit(vals, messageId);

                    // at this point we successfully emitted. Store
                    // the message and message ID so we can do a
                    // JMS acknowledge later
                    this.pendingMessages.put(messageId, msg);
                    this.toCommit.add(messageId);
                } else {
                    this.collector.emit(vals);
                }
            } catch (JMSException e) {
                log.warn("Unable to convert JMS message: " + msg);
            }

        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(TWEET_FIELD));
    }

    /**
     * Returns <code>true</code> if the spout has received failures
     * from which it has not yet recovered.
     */
    public boolean hasFailures() {
        return this.hasFailures;
    }

    protected void recovered() {
        this.hasFailures = false;
    }

    /**
     * Sets the periodicity of the timer task that
     * checks for failures and recovers the JMS session.
     */
    public void setRecoveryPeriod(long period) {
        this.recoveryPeriod = period;
    }

    protected Session getSession() {
        return this.session;
    }

    private boolean isDurableSubscription() {
        return (this.jmsAcknowledgeMode != Session.AUTO_ACKNOWLEDGE);
    }

    private class RecoveryTask extends TimerTask {

        public void run() {
            synchronized (ArtemisSpout.this.recoveryMutex) {
                if (ArtemisSpout.this.hasFailures()) {
                    try {
                        log.info("Recovering from a message failure.");
                        ArtemisSpout.this.getSession().recover();
                        ArtemisSpout.this.recovered();
                    } catch (JMSException e) {
                        log.warn("Could not recover jms session.", e);
                    }
                }
            }
        }

    }
}
