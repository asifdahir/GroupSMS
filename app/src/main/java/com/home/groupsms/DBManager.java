package com.home.groupsms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.home.groupsms.Model.Contact;
import com.home.groupsms.Model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/26/2015.
 */
public class DBManager extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = DBManager.class.getPackage().getName();
    private static final String TABLE_MESSAGES = "messages";
    private static final String TABLE_RECIPIENTS = "recipients";

    // MESSAGES Table Columns names
    private static final String MESSAGES_KEY_ID = "id";
    private static final String MESSAGES_KEY_MESSAGE = "message";
    private static final String MESSAGES_KEY_DT = "dt";

    // RECIPIENTS Table Columns names
    private static final String RECIPIENTS_KEY_ID = "id";
    private static final String RECIPIENTS_KEY_MESSAGE_ID = "message_id";
    private static final String RECIPIENTS_KEY_NAME = "name";
    private static final String RECIPIENTS_KEY_PHONE_NO = "phone_number";
    private static final String RECIPIENTS_KEY_SENT_DT = "sent_dt";


    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_GROUPS_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "("
                + RECIPIENTS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RECIPIENTS_KEY_SENT_DT + " TEXT"
                + ")";
        String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_RECIPIENTS + "("
                + RECIPIENTS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RECIPIENTS_KEY_NAME + " TEXT,"
                + RECIPIENTS_KEY_PHONE_NO + " TEXT,"
                + RECIPIENTS_KEY_SENT_DT + " TEXT"
                + ")";

        sqLiteDatabase.execSQL(CREATE_GROUPS_TABLE);
        sqLiteDatabase.execSQL(CREATE_MESSAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPIENTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);

        onCreate(sqLiteDatabase);
    }


    public int addMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MESSAGES_KEY_MESSAGE, message.message);
        values.put(MESSAGES_KEY_DT, message.dt);

        long id = db.insert(TABLE_MESSAGES, null, values);
        db.close();

        return (int) id;
    }

    public String getMessage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MESSAGES, new String[]{MESSAGES_KEY_ID,
                        MESSAGES_KEY_DT}, MESSAGES_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        String date = cursor.getString(1);

        return date;
    }

    public List<String> getAllMessages() {
        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int getMessagesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MESSAGES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public void deleteMessage(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGES, MESSAGES_KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public int addRecipient(int messageId, Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPIENTS_KEY_MESSAGE_ID, messageId);
        values.put(RECIPIENTS_KEY_NAME, contact.title);
        values.put(RECIPIENTS_KEY_PHONE_NO, contact.phone1);
        values.putNull(RECIPIENTS_KEY_SENT_DT);

        long id = db.insert(TABLE_RECIPIENTS, null, values);
        db.close();

        return (int) id;
    }

    public Contact getRecipient(int id) {
    }

    public List<Contact> getAllRecipients() {
    }

    public int getRecipientsCount() {
    }

    public int updateRecipient(Contact contact) {
    }

    public void deleteRecipient(Contact contact) {
    }
}
