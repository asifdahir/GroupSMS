package com.home.groupsms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.home.groupsms.Model.Recipient;

/**
 * Created by Asif on 28/11/2015.
 */
public class SMSService {

    private Context mContext;
    private BroadcastReceiver mBroadcastReceiverSMSSent;
    private BroadcastReceiver mBroadcastReceiverSMSDelivered;
    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";

    public SMSService(Context context) {
        mContext = context;

        mBroadcastReceiverSMSSent = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int messageId = intent.getIntExtra("MESSAGE_ID", -1);
                int recipientId = intent.getIntExtra("RECIPIENT_ID", -1);
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        DBManager dbManager = new DBManager(mContext, false);
                        Recipient recipient = dbManager.getRecipient(recipientId);
                        recipient.sent_dt = Common.getCurrentDateTime();
                        dbManager.updateRecipient(recipient);
                        SMSStatusActivity activity = (SMSStatusActivity) mContext;
                        activity.updateRecyclerView(messageId);

                        //Toast.makeText(mContext, "SMS sent to " + recipient.contact.phone1, Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(mContext, "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(mContext, "No service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(mContext, "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(mContext, "Radio off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        mBroadcastReceiverSMSDelivered = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //String phoneNumber = intent.getStringExtra("ID");
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        //Toast.makeText(mContext, "SMS delivered to " + phoneNumber, Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        //Toast.makeText(mContext, "SMS not delivered", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        //---when the SMS has been sent---
        mContext.registerReceiver(mBroadcastReceiverSMSSent, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        mContext.registerReceiver(mBroadcastReceiverSMSDelivered, new IntentFilter(DELIVERED));

    }

    public void sendSMS(int messageId, int recipientId, String phoneNumber, String message) {
        Intent intentSent;
        Intent intentDelivered;
        PendingIntent pendingIntentSent;
        PendingIntent pendingIntentDelivered;

        intentSent = new Intent(SENT);
        intentSent.putExtra("MESSAGE_ID", messageId);
        intentSent.putExtra("RECIPIENT_ID", recipientId);
        intentDelivered = new Intent(DELIVERED);
        //intentDelivered.putExtra("RECIPIENT_ID", recipientId);

        pendingIntentSent = PendingIntent.getBroadcast(mContext, 0, intentSent, 0);
        pendingIntentDelivered = PendingIntent.getBroadcast(mContext, 0, intentDelivered, 0);

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pendingIntentSent, pendingIntentDelivered);
    }

    public void unregisterReceivers() {
        try {
            mContext.unregisterReceiver(mBroadcastReceiverSMSSent);
        } catch (Exception ex) {
        }

        try {
            mContext.unregisterReceiver(mBroadcastReceiverSMSDelivered);
        } catch (Exception ex) {
        }
    }
}
