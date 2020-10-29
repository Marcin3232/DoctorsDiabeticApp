package com.example.doctorsdiabeticapp.Model;

import java.util.Date;

public class ChatMessage {

    private String message;
    private String sender;
    private String receiver;
    private long time_send;

    public ChatMessage(String messageText, String messageSender, String messageReceiver) {
        this.message = messageText;
        this.sender = messageSender;
        this.receiver = messageReceiver;
        this.time_send = new Date().getTime();
    }

    public ChatMessage() {
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public long getTime_send() {
        return time_send;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setTime_send(long time_send) {
        this.time_send = time_send;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

}
