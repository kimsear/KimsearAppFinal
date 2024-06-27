package com.example.kimsearappfinal;

public class MessageModel {
    private String text;
    private String sender;

    public MessageModel(String text, String sender) {
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }
}
