package com.error.grrravity.mynews.utils;

import android.content.Context;
import android.widget.Toast;

import com.error.grrravity.mynews.R;

//Utility class
class AlarmToastHelper {

    /**
     * send a toast when alarm is set or unset
     *
     * @param state   : (bool) true is enable, false is disable
     * @param context : (Context) activity context
     */

    static void alarmToast(boolean state, Context context) {
        if (state) {
            Toast.makeText(context,
                    context.getResources().getString(R.string.alarm_on),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,
                    context.getResources().getString(R.string.alamr_off),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
