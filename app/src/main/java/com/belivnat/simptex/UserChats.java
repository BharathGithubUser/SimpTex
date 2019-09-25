package com.belivnat.simptex;

public class UserChats
{
    private String sender;

    private String message;

    private String sent_on;

    public UserChats() {
    }

    public String getSender ()
    {
        return sender;
    }

    public void setSender (String sender)
    {
        this.sender = sender;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getSent_on ()
    {
        return sent_on;
    }

    public void setSent_on (String sent_on)
    {
        this.sent_on = sent_on;
    }
}
