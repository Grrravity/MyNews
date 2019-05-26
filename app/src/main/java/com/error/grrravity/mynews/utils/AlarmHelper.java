package com.error.grrravity.mynews.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;


public class AlarmHelper {

    public void configureAlarmNotif(Context context) {

        AlarmManager mAlarmManager;
        PendingIntent mPendingIntent;

        Preferences preferences = Preferences.getInstance(context);

        //TODO adapter en test unitaire (helpertest)
        Calendar notifTime = DateHelper.setTimeNotif(preferences.getNotifTime());

        boolean switchNotif = preferences.getNotifBoolean();

        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (switchNotif) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            mPendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            if (mAlarmManager != null) {
                mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, notifTime.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, mPendingIntent);
                Helper.alarmToast(true, context);
            }
        }

        if (mAlarmManager != null && switchNotif) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            mPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, notifTime.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, mPendingIntent);
            Helper.alarmToast(true, context);
        }
        if (!switchNotif && mAlarmManager != null) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            mPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            mAlarmManager.cancel(mPendingIntent);
            Helper.alarmToast(false, context);
        }

    }
}
