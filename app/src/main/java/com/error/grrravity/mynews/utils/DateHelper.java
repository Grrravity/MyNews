package com.error.grrravity.mynews.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.error.grrravity.mynews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class DateHelper {

    // Puts the date in DD/MM/YYYY format
    @SuppressLint("SetTextI18n")
    public static String pickerFormatDate(int yearInt, int monthInt, int dayInt, TextView spinner){
        String year = Integer.toString(yearInt);
        String month = (monthInt+1 < 10) ? "0" +
                (monthInt + 1) : Integer.toString(monthInt+1);
        String day = (dayInt < 10) ? "0" + dayInt : Integer.toString(dayInt);
        spinner.setText(day+"/"+month+"/"+year);
        return year+month+day;
    }

    @SuppressLint("SetTextI18n")
    public static String pickerFormatDateTest(int yearInt, int monthInt, int dayInt){
        String year = Integer.toString(yearInt);
        String month = (monthInt+1 < 10) ? "0" +
                monthInt : Integer.toString(monthInt);
        String day = (dayInt < 10) ? "0" + dayInt : Integer.toString(dayInt);
        return year+month+day;
    }

    // Checks if begin date is before the end date in the Search form
    public static boolean datesAreValid(Context context, String beginDate, String endDate){
        if(!beginDate.isEmpty() && !endDate.isEmpty()
                && Integer.parseInt(beginDate) > Integer.parseInt(endDate)){
            Toast.makeText(context, context.getResources().getString(R.string.verification_dates),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    static Calendar setTimeNotif(Context context) {
        Preferences mPreferences = Preferences.getInstance(context);

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        String savedTime = mPreferences.getNotifTime();

        if (!savedTime.equals("")) {
            Date date = null;
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            try {
                date = sdf.parse(savedTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert date != null;
            calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
            calendar.set(Calendar.MINUTE, date.getMinutes());
            calendar.set(Calendar.SECOND, 0);
            Log.i(TAG, "Notification time set at: " + calendar.getTime());
        }
        return calendar;
    }
}
