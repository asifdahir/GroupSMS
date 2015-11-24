package com.home.groupsms.Model;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by Administrator on 11/23/2015.
 */
public class Contact {

    public String id;
    public String title;
    public String phone1;
    public String phone1Type;

    public Contact(String id, String title, String phone1, String phone1Type) {
        this.id = id;
        this.title = title;
        this.phone1 = phone1;
        this.phone1Type = phone1Type;
    }

    public static ArrayList<Contact> getContacts(Context context) {

        ArrayList<Contact> list;
        Contact contact;

        list = new ArrayList<Contact>();

        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        String id = null, name = null, phone = null;
        String phone1 = "unknown", phone2 = "unknown", phone3 = "unknown", type1 = "unknown", type2 = "unknown", type3 = "unknown";

        while (cursor.moveToNext()) {

            id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                Cursor cursorPhone = context.getContentResolver()
                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id},
                                null);

                phone1 = " ";
                phone2 = " ";
                phone3 = " ";
                while (cursorPhone.moveToNext()) {
                    String phonetype = cursorPhone.getString(cursorPhone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                    String MainNumber = cursorPhone.getString(cursorPhone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    if (phonetype.equalsIgnoreCase("1")) {
                        phone1 = MainNumber;
                        type1 = "Home";
                    } else if (phonetype.equalsIgnoreCase("2")) {
                        phone2 = MainNumber;
                        type2 = "Mobile";
                    } else {
                        phone3 = MainNumber;
                        type3 = "Work";
                    }
                }
                cursorPhone.close();
            }

            contact = new Contact(id, name, phone1, type1);
            list.add(contact);
        }

        return list;
    }
}