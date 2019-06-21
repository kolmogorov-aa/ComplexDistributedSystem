package ru.ulmc.school.storm.jms;

import java.io.Serializable;

public class JmsMessageId implements Comparable<JmsMessageId>, Serializable {

    private String jmsID;

    private Long sequence;

    public JmsMessageId(long sequence, String jmsID) {
        this.jmsID = jmsID;
        this.sequence = sequence;
    }

    public String getJmsID() {
        return this.jmsID;
    }

    @Override
    public int compareTo(JmsMessageId other) {
        return Long.compare(this.sequence, other.sequence);
    }

    @Override
    public int hashCode() {
        return this.sequence.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof JmsMessageId) {
            JmsMessageId id = (JmsMessageId) o;
            return this.jmsID.equals(id.jmsID);
        } else {
            return false;
        }
    }

}
