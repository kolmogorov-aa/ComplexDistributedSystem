package ru.ulmc.school.storm.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

public interface JmsContextHolder {
    ConnectionFactory getConnectionFactory();

    Destination getDestination();
}
