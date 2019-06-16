package ru.ulmc.school.storm.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;

public interface JmsContextHolder {
    ConnectionFactory getConnectionFactory();

    Destination getDestination();
}
