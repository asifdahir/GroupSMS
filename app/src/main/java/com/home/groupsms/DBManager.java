package com.home.groupsms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.home.groupsms.Model.Contact;

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
                + MESSAGES_KEY_ID + " INTEGER PRIMARY KEY,"
                + MESSAGES_KEY_DT + " TEXT"
                + ")";
        String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "("
                + MESSAGES_KEY_ID + " INTEGER PRIMARY KEY,"
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
    }

    public String getGroup(int id) {
    }

    public List<String> getAllGroups() {
    }

    public int getGroupsCount() {
    }

    public void deleteGroup(String date) {
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
