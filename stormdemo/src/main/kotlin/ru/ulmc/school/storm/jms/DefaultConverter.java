package ru.ulmc.school.storm.jms;

import org.apache.storm.tuple.Values;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;

import ru.ulmc.school.api.entity.TweetMsg;

public class DefaultConverter implements JmsToTupleConverter, Serializable {

    @Override
    public Values convert(Message msg) {
        try {
            return new Values(msg.getBody(TweetMsg.class));
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
