package com.error.grrravity.mynews;

import com.error.grrravity.mynews.utils.DateHelper;

import org.junit.Test;

import java.util.Calendar;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DateHelperTest {

    //Testing date to string method
    @Test
    public void testPickerDate(){
        String year = "2019";
        String month = "04";
        String day = "10";
        String date = year+month+day;

        int intYear = 2019;
        int intMonth = 4;
        int intDay = 10;
        assertEquals(date, DateHelper.pickerFormatDateTest(intYear,intMonth,intDay));
    }

    // test if date validation for search is correct : day begin must be before day end
    @Test
    public void testSearchDateValidation(){
        String dateBegin = "20190410";
        String dateEnd = "20190510";
        assert(DateHelper.testDatesAreValid(dateBegin, dateEnd));
    }

    // test if setTimeNotif set alarm at the good time and if it adds a day if the hour is before
    // actual
    @Test
    public void testTimeNotif(){
        String time = "20:10";
        if (time.contains(":0")){
            time = time.replace(":0", ":");
        }

        Calendar calendarTest = DateHelper.setTimeNotif(time);

        String savedTime = calendarTest.get(Calendar.HOUR_OF_DAY)+ ":"
                + calendarTest.get(Calendar.MINUTE);
        assertEquals(time,savedTime);

        Calendar calendarTestNow = Calendar.getInstance(Locale.getDefault());

        if (calendarTest.get(Calendar.HOUR_OF_DAY) <= calendarTestNow.get(Calendar.HOUR_OF_DAY)){
            if (calendarTest.get(Calendar.MINUTE) <= calendarTestNow.get(Calendar.MINUTE)) {
                assertNotEquals(calendarTest.get(Calendar.DAY_OF_WEEK),
                        calendarTestNow.get(Calendar.DAY_OF_WEEK));
            }
        } else {
            assertEquals(calendarTest.get(Calendar.DAY_OF_WEEK),
                    calendarTestNow.get(Calendar.DAY_OF_WEEK));
        }
    }
}
