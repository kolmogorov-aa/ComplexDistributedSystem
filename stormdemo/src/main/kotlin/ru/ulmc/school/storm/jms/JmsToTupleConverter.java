package ru.ulmc.school.storm.jms;

import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import javax.jms.Message;

public interface JmsToTupleConverter {
    Values convert(Message msg);
}
