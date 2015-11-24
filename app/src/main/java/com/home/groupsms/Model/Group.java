package com.home.groupsms.Model;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by Administrator on 11/23/2015.
 */
public class Group {

    public String id;
    public String title;

    public Group(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public static ArrayList<Group> getGroups(Context context) {
        final String[] GROUP_PROJECTION = new String[]{ContactsContract.Groups._ID, ContactsContract.Groups.TITLE};
        Cursor cursor = context.getContentResolver().
                query(ContactsContract.Groups.CONTENT_URI, GROUP_PROJECTION, null, null, ContactsContract.Groups.TITLE);

        ArrayList<Group> list = new ArrayList<Group>();
        Group group = null;
        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups._ID));
            String title = (cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE)));

            group = new Group(id, title);
            list.add(group);
        }
        return list;
    }
}
