package com.error.grrravity.mynews.utils;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.error.grrravity.mynews.R;

import java.util.List;

//Utility class
public class Helper {

    // Checks the validity of the form
    public static boolean validateParameters(Context context, EditText searchField,
                                             CheckBox cbArts, CheckBox cbBusiness, CheckBox cbFood,
                                             CheckBox cbPolitics, CheckBox cbScience,
                                             CheckBox cbSport, CheckBox cbTechnology){
        if(searchField.getText().toString().trim().isEmpty()){
            Toast.makeText(context,
                    context.getResources().getString(R.string.verification_search_field),
                    Toast.LENGTH_LONG).show();
            searchField.requestFocus();
            return false;
        }
        if(!cbArts.isChecked() && !cbBusiness.isChecked() && !cbFood.isChecked()
                && !cbPolitics.isChecked() && !cbScience.isChecked()
                && !cbSport.isChecked() && !cbTechnology.isChecked()){
            Toast.makeText(context,
                    context.getResources().getString(R.string.verification_checkbox),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static boolean validateNotifParams(Context context,
                                              String keywords, List<String> categories,
                                              String time, Switch switchNotif) {
        boolean mSwitch;
        if (switchNotif.isChecked()) {
            mSwitch= true;
            if (keywords != null && keywords.isEmpty()) {
                Toast.makeText(context,
                        "Please enter something to search",
                        Toast.LENGTH_LONG).show();
                mSwitch = false;
            }
            if (categories.size() == 0) {
                Toast.makeText(context,
                        "Please select at least one category",
                        Toast.LENGTH_LONG).show();
                mSwitch = false;
            }
            if (time.equals("") || time.isEmpty()) {
                Toast.makeText(context,
                        "Please select the time when you want to get notify",
                        Toast.LENGTH_LONG).show();
                mSwitch = false;
            }
        } else {
            mSwitch= false;
        }
        return  mSwitch;
    }

}