package com.error.grrravity.mynews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.error.grrravity.mynews.R;

import java.util.List;
import java.util.Objects;

//Utility class
public class Helper {

    /**
     * verify if at least a checkbox is selected and if a query is set.
     * if not, send toast to the user.
     *
     * @param context      (context) context from the activity
     * @param searchField  (EditText) query entered in search field
     * @param cbArts       (checkbox) art checkbox
     * @param cbBusiness   (checkbox) business checkbox
     * @param cbFood       (checkbox) food checkbox
     * @param cbPolitics   (checkbox) politics checkbox
     * @param cbScience    (checkbox) science checkbox
     * @param cbSport      (checkbox) sport checkbox
     * @param cbTechnology (checkbox) technology checkbox
     * @return true if all criteria are filled
     */

    public static boolean validateSearchParam(Context context, EditText searchField,
                                              CheckBox cbArts, CheckBox cbBusiness, CheckBox cbFood,
                                              CheckBox cbPolitics, CheckBox cbScience,
                                              CheckBox cbSport, CheckBox cbTechnology) {
        if (searchField.getText().toString().trim().isEmpty()) {
            Toast.makeText(context,
                    context.getResources().getString(R.string.verification_search_field),
                    Toast.LENGTH_LONG).show();
            searchField.requestFocus();
            return false;
        }
        if (!cbArts.isChecked() && !cbBusiness.isChecked() && !cbFood.isChecked()
                && !cbPolitics.isChecked() && !cbScience.isChecked()
                && !cbSport.isChecked() && !cbTechnology.isChecked()) {
            Toast.makeText(context,
                    context.getResources().getString(R.string.verification_checkbox),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * verify if a category, a query and the time for notification have been correctly set
     * if not, send toast to the user.
     *
     * @param context     (context) context from the activity
     * @param keywords    (string) query entered in search field
     * @param categories  (list string) categories
     * @param time        (string) hour for notification (hh:mm)
     * @param switchNotif (Switch) notif state
     * @return true if all criteria are filled
     */

    public static boolean validateNotifParam(Context context,
                                             String keywords, List<String> categories,
                                             String time, Switch switchNotif) {
        if (switchNotif.isChecked()) {
            if (keywords == null || keywords.isEmpty()) {
                Toast.makeText(context,
                        context.getResources().getString(R.string.verification_search_field),
                        Toast.LENGTH_LONG).show();
                return false;
            }
            if (categories.size() == 0) {
                Toast.makeText(context,
                        context.getResources().getString(R.string.verification_checkbox),
                        Toast.LENGTH_LONG).show();
                return false;
            }
            if (time.equals("") || time.isEmpty()) {
                Toast.makeText(context,
                        context.getResources().getString(R.string.verification_notif_time),
                        Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Verify if requirement is filled to send a mail from Help activity
     *
     * @param subject : (string) mail subject from the spinner. default is '- Please select a subject -'
     * @param message : (string) message written by the user.
     * @param context : (context) activity context
     * @return validation int. 0 is when missing subject, 1 is when missing message and 2 is OK.
     */

    public static int validateHelpInfo(String subject, String message, Context context) {
        int validation;
        if (subject.equals("- Please select a subject -")) {
            validation = 0;
            Toast.makeText(context,
                    context.getResources().getString(R.string.verification_help_object),
                    Toast.LENGTH_LONG).show();
        }
        if (message.isEmpty()) {
            validation = 1;
        } else {
            validation = 2;
        }
        return validation;
    }

    /**
     * send a toast when alarm is set or unset
     *
     * @param state   : (bool) true is enable, false is disable
     * @param context : (context) activity context
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

    /**
     * check if internet is available
     *
     * @param context : getActivity
     * @return (bool) activeNetworkInfo.
     */

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) Objects.requireNonNull(context)
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}