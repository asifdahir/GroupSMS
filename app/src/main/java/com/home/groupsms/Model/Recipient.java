package com.home.groupsms.Model;

/**
 * Created by Administrator on 11/27/2015.
 */
public class Recipient {
    public int id;
    public int message_id;
    public String name;
    public String phone_number;
    public String sent_dt;

    public Recipient(int id, int message_id, String name, String phone_number, String sent_dt) {
        this.id = id;
        this.message_id = message_id;
        this.name = name;
        this.phone_number = phone_number;
        this.sent_dt = sent_dt;
    }
}
