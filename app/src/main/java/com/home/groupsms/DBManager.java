package com.home.groupsms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.home.groupsms.Model.Contact;
import com.home.groupsms.Model.Message;
import com.home.groupsms.Model.Recipient;

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


    public DBManager(Context context, boolean recreateDatabase) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (recreateDatabase)
            context.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "("
                + MESSAGES_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MESSAGES_KEY_MESSAGE + " TEXT,"
                + MESSAGES_KEY_DT + " TEXT"
                + ")";
        String CREATE_RECIPIENTS_TABLE = "CREATE TABLE " + TABLE_RECIPIENTS + "("
                + RECIPIENTS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RECIPIENTS_KEY_MESSAGE_ID + " INTEGER,"
                + RECIPIENTS_KEY_NAME + " TEXT,"
                + RECIPIENTS_KEY_PHONE_NO + " TEXT,"
                + RECIPIENTS_KEY_SENT_DT + " TEXT"
                + ")";

        sqLiteDatabase.execSQL(CREATE_MESSAGES_TABLE);
        sqLiteDatabase.execSQL(CREATE_RECIPIENTS_TABLE);
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

    public Message getMessage(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MESSAGES, new String[]{
                        MESSAGES_KEY_ID,
                        MESSAGES_KEY_MESSAGE,
                        MESSAGES_KEY_DT}, MESSAGES_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor == null)
            return null;

        cursor.moveToFirst();
        return new Message(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
    }

    public List<Message> getAllMessages() {
        List<Message> list = new ArrayList<Message>();
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Message(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
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

    public int addRecipient(Recipient recipient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPIENTS_KEY_MESSAGE_ID, recipient.message_id);
        values.put(RECIPIENTS_KEY_NAME, recipient.contact.title);
        values.put(RECIPIENTS_KEY_PHONE_NO, recipient.contact.phone1);
        values.put(RECIPIENTS_KEY_SENT_DT, recipient.sent_dt);

        long id = db.insert(TABLE_RECIPIENTS, null, values);
        db.close();

        return (int) id;
    }

    public Recipient getRecipient(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECIPIENTS,
                new String[]{
                        RECIPIENTS_KEY_ID,
                        RECIPIENTS_KEY_MESSAGE_ID,
                        RECIPIENTS_KEY_SENT_DT,
                        RECIPIENTS_KEY_NAME,
                        RECIPIENTS_KEY_PHONE_NO
                }, MESSAGES_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor == null)
            return null;

        cursor.moveToFirst();
        return new Recipient(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                new Contact("", cursor.getString(3), cursor.getString(4), ""));
    }

    public List<Recipient> getAllRecipients(int messageId) {
        List<Recipient> list = new ArrayList<Recipient>();
        String selectQuery = "SELECT  * FROM " + TABLE_RECIPIENTS + " WHERE " + RECIPIENTS_KEY_MESSAGE_ID + "=" + messageId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new Recipient(cursor.getInt(0), cursor.getInt(1), cursor.getString(4),
                        new Contact("", cursor.getString(2), cursor.getString(3), "")));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public int getRecipientsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_RECIPIENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateRecipient(Recipient recipient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RECIPIENTS_KEY_MESSAGE_ID, recipient.message_id);
        values.put(RECIPIENTS_KEY_NAME, recipient.contact.title);
        values.put(RECIPIENTS_KEY_PHONE_NO, recipient.contact.phone1);
        values.put(RECIPIENTS_KEY_SENT_DT, recipient.sent_dt);

        // updating row
        return db.update(TABLE_RECIPIENTS, values, RECIPIENTS_KEY_ID + " = ?",
                new String[]{String.valueOf(recipient.id)});
    }

    public void deleteRecipient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPIENTS, RECIPIENTS_KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
