package com.error.grrravity.mynews;

import android.widget.EditText;

import com.error.grrravity.mynews.utils.Helper;

import org.junit.Test;

import butterknife.BindView;

import static org.junit.Assert.assertEquals;

public class HelperTest {
    @BindView(R.id.editBeginDateTV) EditText mSpinner;

    //Testing date to string method
    @Test
    public void testDate(){
        String month = "04";
        String year = "2019";
        String day = "10";
        String date = day+month+year;

        int intDay = 10;
        int intYear = 2019;
        int intMonth = 4;
        assertEquals(date, Helper.pickerFormatDate(intYear,intMonth,intDay, mSpinner));
    }

}
