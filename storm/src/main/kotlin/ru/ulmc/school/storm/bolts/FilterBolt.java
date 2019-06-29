package ru.ulmc.school.storm.bolts;

import org.apache.storm.Constants;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import ru.ulmc.school.api.entity.TweetMsg;

import java.util.Map;

import static ru.ulmc.school.storm.Names.Fields.TWEET_FIELD;

public class FilterBolt extends BaseRichBolt {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(FilterBolt.class);
    private OutputCollector collector;

    private static boolean isTickTuple(Tuple tuple) {
        return tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID)
                && tuple.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID);
    }

    @Override
    public void prepare(Map<String, Object> topoConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        if (isOurTuple(input)) {
            Object field = input.getValueByField(TWEET_FIELD);
            if (field instanceof TweetMsg) {
                TweetMsg tweet = (TweetMsg) field;
                log.debug("checking out tweet {}", tweet);
                String text = tweet.getText();
                if (text != null
                        && text.length() >= 40
                        && text.length() <= 140) {
                    collector.emit(new Values(tweet));
                } else {
                    log.info("filtered out tweet {}", tweet);
                }
            }
        } else if (isTickTuple(input)) {
            log.trace("Tick tuple got");
        }
        collector.ack(input);
    }

    private boolean isOurTuple(Tuple tuple) {
        return tuple.getFields().contains(TWEET_FIELD)
                && tuple.getValueByField(TWEET_FIELD) != null;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(TWEET_FIELD));
    }
}
