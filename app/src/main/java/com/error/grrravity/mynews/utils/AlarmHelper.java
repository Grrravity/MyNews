package com.error.grrravity.mynews.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AlarmHelper {

    private static Preferences mPreferences ;

    public void configureAlarmNotif (Context context){

        AlarmManager mAlarmManager;
        PendingIntent mPendingIntent;

        mPreferences = Preferences.getInstance(context);

        Calendar current = Calendar.getInstance(Locale.FRANCE);
        Calendar calendar = Calendar.getInstance(Locale.FRANCE);

        String savedTime = mPreferences.getNotifTime();

        Date date = null;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        try {
            date = sdf.parse(savedTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.setTime(date);

        if (calendar.before(current)){
            calendar.add(Calendar.DATE, 1);
        }

        boolean switchNotif = mPreferences.getNotifBoolean();

        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (switchNotif){
            Intent intent = new Intent(context, AlarmReceiver.class);
            mPendingIntent = PendingIntent.getBroadcast(context, 0 , intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            if (mAlarmManager != null){
                mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, mPendingIntent);
            }
        }

        if (mAlarmManager != null && switchNotif){
            Intent intent = new Intent(context, AlarmReceiver.class);
            mPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, mPendingIntent);
        }

    }
}
