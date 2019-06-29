package ru.ulmc.school.storm.bolts;

import org.apache.storm.Constants;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;

import java.util.Map;

import static ru.ulmc.school.storm.Names.Fields.STORE_FIELD;

public class StoreBolt extends BaseRichBolt {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(StoreBolt.class);
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
            Object field = input.getValueByField(STORE_FIELD);
            log.info("!!!!!!!!!!!!!!!!!! stored to console {}", field);
        } else if (isTickTuple(input)) {
            log.trace("Tick tuple got");
        }
        collector.ack(input);
    }

    private boolean isOurTuple(Tuple tuple) {
        return tuple.getFields().contains(STORE_FIELD)
                && tuple.getValueByField(STORE_FIELD) != null;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
