package com.error.grrravity.mynews;

import com.error.grrravity.mynews.utils.Helper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HelperTest {

    //Testing date to string method
    @Test
    public void testDate(){
        String month = "04";
        String year = "2019";
        String day = "10";
        String date = year+month+day;

        int intYear = 2019;
        int intDay = 10;
        int intMonth = 4;
        assertEquals(date, Helper.pickerFormatDateTest(intYear,intMonth,intDay));
    }

}
