package com.home.groupsms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.home.groupsms.Model.Contact;

import java.util.Enumeration;

/**
 * Created by Administrator on 11/26/2015.
 */
public class ComposeSMS extends AppCompatActivity {

    private Toolbar mToolbar;
    private Menu mMenu;
    private BroadcastReceiver mBroadcastReceiverSMSSent;
    private BroadcastReceiver mBroadcastReceiverSMSDelivered;

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_home);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Compose SMS");
    }

    private void sendSMS() {
        DBManager dbManager = null;
        EditText editText;
        Contact contact;
        String message;
        String key;
        Enumeration<String> enumKey;
        int groupId;

        editText = (EditText) findViewById(R.id.edit);
        editText.setEnabled(false);
        message = editText.getText().toString();
        mMenu.getItem(0).setVisible(false);

        dbManager = new DBManager(this);
        groupId = dbManager.addMessage(message);

        enumKey = MainActivity.HashtableSelectedContacts.keys();
        while (enumKey.hasMoreElements()) {
            key = enumKey.nextElement();
            contact = MainActivity.HashtableSelectedContacts.get(key);

            dbManager.addRecipient(groupId, contact);
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        Intent intentSent;
        Intent intentDelivered;
        PendingIntent pendingIntentSent;
        PendingIntent pendingIntentDelivered;

        intentSent = new Intent(SENT);
        intentSent.putExtra("ID", phoneNumber);
        intentDelivered = new Intent(DELIVERED);
        intentDelivered.putExtra("ID", phoneNumber);

        pendingIntentSent = PendingIntent.getBroadcast(this, 0, intentSent, 0);
        pendingIntentDelivered = PendingIntent.getBroadcast(this, 0, intentDelivered, 0);

        mBroadcastReceiverSMSSent = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String phoneNumber = intent.getStringExtra("ID");
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent to " + phoneNumber, Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        mBroadcastReceiverSMSDelivered = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String phoneNumber = intent.getStringExtra("ID");
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered to " + phoneNumber, Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        //---when the SMS has been sent---
        registerReceiver(mBroadcastReceiverSMSSent, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        registerReceiver(mBroadcastReceiverSMSDelivered, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pendingIntentSent, pendingIntentDelivered);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_compose_sms);

        setupToolbar();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiverSMSSent);
        unregisterReceiver(mBroadcastReceiverSMSDelivered);

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose_sms, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send_sms:
                sendSMS();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
