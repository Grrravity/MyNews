package com.error.grrravity.mynews;

import com.error.grrravity.mynews.utils.DateHelper;
import com.error.grrravity.mynews.utils.ErrorListener;
import com.error.grrravity.mynews.utils.FocusListener;
import com.error.grrravity.mynews.utils.HelpHelper;
import com.error.grrravity.mynews.utils.SearchAndNotifHelper;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HelpersTest {

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

    @Test
    public void testSearchParams(){
        String searchField = "";
        boolean cbArts = false;
        boolean cbBusiness = false;
        boolean cbFood = false;
        boolean cbPolitics = false;
        boolean cbScience = false;
        boolean cbSport = false;
        boolean cbTechnology = false;

        boolean conditions;

        if (!searchField.isEmpty() && !searchField.equals("")){
            if (cbArts || cbBusiness|| cbFood || cbPolitics|| cbScience|| cbSport|| cbTechnology){
                conditions = true;
            } else {
                conditions = false;
            }
        } else {
            conditions = false;
        }

        assertEquals(conditions, SearchAndNotifHelper.validateSearchParam(searchField, cbArts,
                cbBusiness, cbFood, cbPolitics, cbScience, cbSport, cbTechnology,
                errorListener, focusListener));
    }

    @Test
    public void testNotifParams(){
        String keyword = "trump";
        List<String> categories = new ArrayList<>();
        String time = "20:10";
        boolean switchNotif = true;

        categories.add("food");

        boolean conditions;

        if (switchNotif){
            if (!time.equals("")){
                if (!keyword.equals("")){
                    if (categories.size() != 0){
                        conditions = true;
                    } else {
                        conditions = false;
                    }
                } else {
                    conditions = false;
                }
            } else {
                conditions = false;
            }
        } else {
            conditions = false;
        }

        assertEquals(conditions, SearchAndNotifHelper.validateNotifParam(keyword, categories, time,
                switchNotif, errorListener));
    }

    @Test
    public void testHelpActivityInfo(){
        String subject = "- Please select a subject -"; //return false
        //String subject = "Bug"; //return true
        String message = "test";

        boolean conditions;

        if (!subject.equals("- Please select a subject -")){
            conditions = !message.isEmpty();
        } else {
            conditions = false;
        }

        assertEquals(conditions, HelpHelper.validateHelpInfo(subject, message, errorListener));

    }

    private FocusListener focusListener = new FocusListener() {
        @Override
        public void onGetRequestFocus(boolean bool) {

        }
    };

    //just to have listener in unit test.
    private ErrorListener errorListener = new ErrorListener() {
              @Override
              public void onShowErrorString(String error) {

              }

              @Override
              public void onShowErrorFromResources(int error) {

              }
          };
}
