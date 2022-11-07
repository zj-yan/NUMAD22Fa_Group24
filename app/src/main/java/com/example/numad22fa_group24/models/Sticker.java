package com.example.numad22fa_group24.models;

public class Sticker {

    private String imageName;
    private String senderId;
    private long timeStamp;

    public Sticker() {

    }

    public Sticker(String imageName, String senderId, long timeStamp) {
        this.imageName = imageName;
        this.senderId = senderId;
        this.timeStamp = timeStamp;
    }

    public String getImageName() {
        return imageName;
    }

    public String getSenderId() {
        return senderId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
