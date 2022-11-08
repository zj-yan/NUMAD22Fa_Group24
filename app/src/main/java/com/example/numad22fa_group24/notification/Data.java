package com.example.numad22fa_group24.notification;

public class Data {
    public String sender;
    public String receiver;
    public String title;
    public String body;
    public String img;

    public Data() {

    }

    public Data(String sender, String receiver, String title, String body, String img) {
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.body = body;
        this.img = img;
    }
}
