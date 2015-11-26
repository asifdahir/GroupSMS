package com.home.groupsms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.home.groupsms.Model.Contact;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 11/26/2015.
 */
public class DBManager extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = DBManager.class.getPackage().getName();
    private static final String TABLE_GROUPS = "groups";
    private static final String TABLE_MESSAGES = "messages";

    // Groups Table Columns names
    private static final String GROUPS_KEY_ID = "id";
    private static final String GROUPS_KEY_DT = "dt";

    // Messages Table Columns names
    private static final String MESSAGES_KEY_ID = "id";
    private static final String MESSAGES_KEY_NAME = "name";
    private static final String MESSAGES_KEY_PHONE_NO = "phone_number";
    private static final String MESSAGES_KEY_DT = "dt";


    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_GROUPS_TABLE = "CREATE TABLE " + TABLE_GROUPS + "("
                + MESSAGES_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MESSAGES_KEY_DT + " TEXT"
                + ")";
        String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "("
                + MESSAGES_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MESSAGES_KEY_NAME + " TEXT,"
                + MESSAGES_KEY_PHONE_NO + " TEXT,"
                + MESSAGES_KEY_DT + " TEXT"
                + ")";

        sqLiteDatabase.execSQL(CREATE_GROUPS_TABLE);
        sqLiteDatabase.execSQL(CREATE_MESSAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);

        onCreate(sqLiteDatabase);
    }


    public int addGroup(String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GROUPS_KEY_DT, date);

        long id = db.insert(TABLE_GROUPS, null, values);
        db.close();

        return (int) id;
    }

    public String getGroup(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GROUPS, new String[]{GROUPS_KEY_ID,
                        GROUPS_KEY_DT}, GROUPS_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String date = cursor.getString(1);

        return date;
    }

    public List<String> getAllGroups() {
        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_GROUPS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int getGroupsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_GROUPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public void deleteGroup(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROUPS, GROUPS_KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void addMessage(Contact contact) {
    }

    public Contact getMessage(int id) {
    }

    public List<Contact> getAllMessages() {
    }

    public int getMessagesCount() {
    }

    public int updateMessage(Contact contact) {
    }

    public void deleteMessage(Contact contact) {
    }
}
