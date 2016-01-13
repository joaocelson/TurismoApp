package com.jctecnologia.turismoapp.model;

import android.os.Message;

import java.util.LinkedList;

/**
 * Created by JOAOCELSON on 11/01/2016.
 */
public class WrapObjToNetwork {
    private Pessoa user;
    private Pessoa userFrom;
    private Message message;
    private LinkedList<Message> messages;
    private String method;


    public WrapObjToNetwork( LinkedList<Message> messages, String method) {
        this.messages = messages;
        this.method = method;
    }
    public WrapObjToNetwork(Pessoa user, Pessoa userFrom, String method) {
        this.user = user;
        this.userFrom = userFrom;
        this.method = method;
    }
    public WrapObjToNetwork(Pessoa user, String method) {
        this.user = user;
        this.method = method;
    }
    public WrapObjToNetwork(Message message, String method) {
        this.message = message;
        this.method = method;
    }

    public Pessoa getUser() {
        return user;
    }

    public void setUser(Pessoa user) {
        this.user = user;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Pessoa getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(Pessoa userFrom) {
        this.userFrom = userFrom;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

    public void setMessages(LinkedList<Message> messages) {
        this.messages = messages;
    }
}

