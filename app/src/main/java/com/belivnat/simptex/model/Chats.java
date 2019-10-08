package com.belivnat.simptex.model;

public class Chats {
    public String message;
    public String sender;
    public String sent_on;
    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String msg_type;
    public Chats() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSent_on() {
        return sent_on;
    }

    public void setSent_on(String sent_on) {
        this.sent_on = sent_on;
    }
}
