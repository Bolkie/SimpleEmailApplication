package com.company.model;

import java.time.LocalDateTime;

public class Message {
    private final int id;
    private static int nextId = 0;
    private final MessageType type;
    private final String sender;
    private final String receiver;
    private final String topic;
    private final String message;
    private final LocalDateTime sentDateTime;
    private boolean viewed;

    public Message(MessageType messageType, String sender, String receiver, String topic, String message) {
        this.id = nextId;
        this.type = messageType;
        this.sender = sender;
        this.receiver = receiver;
        this.topic = topic;
        this.message = message;
        this.sentDateTime = LocalDateTime.now();
        this.viewed = false;
        nextId++;
    }

    public int getId() {
        return id;
    }

    public MessageType getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getTopic() {
        return topic;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSentDateTime() {
        return sentDateTime;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }
}