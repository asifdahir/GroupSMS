package com.home.groupsms.Model;

/**
 * Created by Administrator on 11/27/2015.
 */
public class Message {
    public int id;
    public String message;
    public String dt;

    public Message(int id, String message, String dt) {
        this.id = id;
        this.message = message;
        this.dt = dt;
    }
}
