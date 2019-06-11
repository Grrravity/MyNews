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
    public static String pickerFormatDate(int yearInt, int monthInt, int dayInt, TextView textDate) {
        String year = Integer.toString(yearInt);
        String month = (monthInt + 1 < 10) ? "0" +
                (monthInt + 1) : Integer.toString(monthInt + 1);
        String day = (dayInt < 10) ? "0" + dayInt : Integer.toString(dayInt);
        textDate.setText(day + "/" + month + "/" + year);
        return year + month + day;
    }

    //Only for test, removed spinner to work properly.
    @SuppressLint("SetTextI18n")
    public static String pickerFormatDateTest(int yearInt, int monthInt, int dayInt) {
        String year = Integer.toString(yearInt);
        String month = (monthInt + 1 < 10) ? "0" +
                monthInt : Integer.toString(monthInt);
        String day = (dayInt < 10) ? "0" + dayInt : Integer.toString(dayInt);
        return year + month + day;
    }

    // Checks if begin date is before the end date in the Search form
    public static boolean datesAreValid(Context context, String beginDate, String endDate) {
        if (!beginDate.isEmpty() && !endDate.isEmpty()
                && Integer.parseInt(beginDate) > Integer.parseInt(endDate)) {
            Toast.makeText(context,
                    context.getResources().getString(R.string.verification_dates_correct),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (beginDate.isEmpty() || endDate.isEmpty()){
            Toast.makeText(context,
                    context.getResources().getString(R.string.verification_dates_set),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //Only for test. removed context as toast isn't needed.
    public static boolean testDatesAreValid(String beginDate, String endDate) {
        if (!beginDate.isEmpty() && !endDate.isEmpty()
                && Integer.parseInt(beginDate) > Integer.parseInt(endDate)) {
            return false;
        }
        return true;
    }

    public static Calendar setTimeNotif(String savedTime) {

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        Calendar tempCalendar = Calendar.getInstance(Locale.getDefault());

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
            tempCalendar.setTime(date);

            if (tempCalendar.get(Calendar.HOUR_OF_DAY) <= calendar.get(Calendar.HOUR_OF_DAY) ){
                if (tempCalendar.get(Calendar.MINUTE) < calendar.get(Calendar.MINUTE)){
                    calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK)+1);
                }
            }
            calendar.set(Calendar.HOUR_OF_DAY, tempCalendar.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, tempCalendar.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, 0);

            Log.i(TAG, "Notification time set at: " + calendar.getTime()
                    + "- Grrravity");
        }
        return calendar;
    }
}
