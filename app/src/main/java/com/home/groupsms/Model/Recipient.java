package com.home.groupsms.Model;

/**
 * Created by Administrator on 11/27/2015.
 */
public class Recipient {
    public int id;
    public int message_id;
    public String sent_dt;
    public Contact contact;

    public Recipient(int id, int message_id, String sent_dt, Contact contact) {
        this.id = id;
        this.message_id = message_id;
        this.sent_dt = sent_dt;
        this.contact = new Contact(contact.id, contact.title, contact.phone1, contact.phone1Type);
    }
}
