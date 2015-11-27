package com.home.groupsms;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.home.groupsms.Adapter.SMSStatusAdapter;
import com.home.groupsms.Model.Recipient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 11/27/2015.
 */
public class SMSStatusActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private SMSStatusAdapter mAdapter;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sms_status);

        setupToolbar();

        DBManager dbManager = new DBManager(this);
        ArrayList<Recipient> list = new ArrayList<>(dbManager.getAllRecipients());
        mAdapter = new SMSStatusAdapter(list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSMSStatus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
