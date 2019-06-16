package ru.ulmc.school.storm.jms;

import java.io.Serializable;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class DefaultJmsHolder implements JmsContextHolder, Serializable {
    private final Destination queue;
    private final ConnectionFactory factory;

    public DefaultJmsHolder() {

        try {
            InitialContext initialContext = new InitialContext();
            factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            queue = (Destination) initialContext.lookup("queue/defaultDestination");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ConnectionFactory getConnectionFactory() {
        return factory;
    }

    @Override
    public Destination getDestination() {
        return queue;
    }
}
