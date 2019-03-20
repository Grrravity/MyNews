package com.error.grrravity.mynews.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.error.grrravity.mynews.R;

//Utility class
public class Helper {

    // Puts the date in DD/MM/YYYY format in a spinner
    public static String spinnerFormatDate(int yearInt, int monthInt, int dayInt, TextView spinner){
        String year = Integer.toString(yearInt);
        String month = (monthInt+1 < 10) ? "0" +
                Integer.toString(monthInt+1) : Integer.toString(monthInt+1);
        String day = (dayInt < 10) ? "0" + Integer.toString(dayInt) : Integer.toString(dayInt);
        spinner.setText(day+"/"+month+"/"+year);
        return year+month+day;
    }

    // Puts the date from YYYY-MM-DD to DD/MM/YY format
    public static String formatDate(String date){
        return date.substring(8, 10)+"/"+date.substring(5, 7)+"/"+date.substring(2,4);
    }

    // Checks the internet connection
    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = null;
        if (cm != null) {
            network = cm.getActiveNetworkInfo();
        }
        return network != null && network.isConnectedOrConnecting();
    }

    // Checks the validity of the form
    public static boolean parametersAreValid(Context context, EditText searchField,
                                             CheckBox cbArts, CheckBox cbBusiness,
                                             CheckBox cbPolitics, CheckBox cbTechnology){
        if(searchField.getText().toString().trim().isEmpty()){
            Toast.makeText(context,
                    context.getResources().getString(R.string.verification_search_field),
                    Toast.LENGTH_LONG).show();
            searchField.requestFocus();
            return false;
        }
        if(!cbArts.isChecked() && !cbBusiness.isChecked() &&
                !cbPolitics.isChecked() && !cbTechnology.isChecked()){
            Toast.makeText(context,
                    context.getResources().getString(R.string.verification_checkbox),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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
}