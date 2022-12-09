package com.example.numad22fa_group24.models;

public class Message {
    private String senderID;
    private String receiverID;
    private String content;
    private long timeStamp;

    public Message(String senderID, String receiverID, String content, long timeStamp) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.content = content;
        this.timeStamp = timeStamp;
    }

    public Message() {
    }

    public String getSenderID() {
        return senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public String getContent() {
        return content;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

}
