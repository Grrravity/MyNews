package com.error.grrravity.mynews.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;


public class AlarmHelper {

    public void configureAlarmNotif (Context context){

        AlarmManager mAlarmManager;
        PendingIntent mPendingIntent;

        Preferences preferences = Preferences.getInstance(context);

        Calendar notifTime = DateHelper.setTimeNotif(context);

        boolean switchNotif = preferences.getNotifBoolean();

        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (switchNotif){
            Intent intent = new Intent(context, AlarmReceiver.class);
            mPendingIntent = PendingIntent.getBroadcast(context, 0 , intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            if (mAlarmManager != null){
                mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, notifTime.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, mPendingIntent);
            }
        }

        if (mAlarmManager != null && switchNotif){
            Intent intent = new Intent(context, AlarmReceiver.class);
            mPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, notifTime.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, mPendingIntent);
        }

    }
}
