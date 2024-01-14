package com.example.codersforum;

// Message.java
public class Message {

    private String user;
    private String content;
    private long timestamp;

    // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    public Message() {
    }

    public Message(String user, String content, long timestamp) {
        this.user = user;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
