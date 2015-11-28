package com.home.groupsms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 11/26/2015.
 */
public class Common {

    public static String getCurrentDateTime() {
        Date date = new Date();
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
    }
}
