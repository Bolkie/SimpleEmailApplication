package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Inbox {
    private final List<Message> sentMessages;
    private final List<Message> receivedMessages;

    public Inbox() {
        sentMessages = new ArrayList<>();
        receivedMessages = new ArrayList<>();
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public void addSentMessage(Message sentMessage) {
        this.sentMessages.add(sentMessage);
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void addReceivedMessage(Message receivedMessage) {
        this.receivedMessages.add(receivedMessage);
    }
}