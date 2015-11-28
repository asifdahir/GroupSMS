package com.home.groupsms;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.home.groupsms.Adapter.SMSStatusAdapter;
import com.home.groupsms.Model.Message;
import com.home.groupsms.Model.Recipient;

import java.util.ArrayList;

/**
 * Created by Administrator on 11/27/2015.
 */
public class SMSStatusActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private SMSStatusAdapter mAdapter;
    private Message mMessage;
    private ArrayList<Recipient> mListRecipients;

    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_home);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.sms_status);
    }

    public void updateRecyclerView(int messageId) {
        DBManager dbManager = new DBManager(this, false);

        mListRecipients = new ArrayList<>(dbManager.getAllRecipients(messageId));
        mAdapter = new SMSStatusAdapter(mListRecipients);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSMSStatus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sms_status);

        setupToolbar();

        Intent intent = getIntent();
        int messageId = intent.getIntExtra("MESSAGE_ID", -1);

        DBManager dbManager = new DBManager(this, false);
        mMessage = dbManager.getMessage(messageId);

        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(mMessage.message);

        updateRecyclerView(messageId);

        new SMSServiceOperation(this).execute();

    }

    private class SMSServiceOperation extends AsyncTask<Void, Void, String> {
        private Context mContext;

        public SMSServiceOperation(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(Void... voids) {
            SMSService smsService = new SMSService(mContext);
            for (Recipient recipient : mListRecipients) {
                smsService.sendSMS(mMessage.id, recipient.id, recipient.contact.phone1, mMessage.message);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
