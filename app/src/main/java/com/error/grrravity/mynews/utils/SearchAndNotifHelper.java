package com.error.grrravity.mynews.utils;

import com.error.grrravity.mynews.R;

import java.util.List;

//Utility class
public class SearchAndNotifHelper {

    private static boolean mFocus;

    /**
     * verify if at least a checkbox is selected and if a query is set.
     * if not, send toast to the user.
     *
     * @param searchField  (String) query entered in search field
     * @param cbArts       (bool) art checkbox
     * @param cbBusiness   (bool) business checkbox
     * @param cbFood       (bool) food checkbox
     * @param cbPolitics   (bool) politics checkbox
     * @param cbScience    (bool) science checkbox
     * @param cbSport      (bool) sport checkbox
     * @param cbTechnology (bool) technology checkbox
     * @param errorListener (errorListener) error listener to make Toast.
     * @return true if all criteria are filled
     */

    public static boolean validateSearchParam(String searchField,
                                              Boolean cbArts, Boolean cbBusiness, Boolean cbFood,
                                              Boolean cbPolitics, Boolean cbScience,
                                              Boolean cbSport, Boolean cbTechnology,
                                              ErrorListener errorListener) {
        if (searchField == null || searchField.equals("")) {
            if (errorListener != null) {
                errorListener.onShowErrorFromResources(R.string.verification_search_field);
            }
            mFocus = true;
            return false;
        }
        if (!cbArts && !cbBusiness && !cbFood && !cbPolitics && !cbScience && !cbSport &&
                !cbTechnology) {
            if (errorListener != null) {
                errorListener.onShowErrorFromResources(R.string.verification_checkbox);
            }
            mFocus = false;
            return false;
        }
        mFocus = false;
        return true;
    }

    //get focus value. required to make validateSearchParam() working on unit testing.
    public static boolean getFocus(){
        return mFocus;
    }

    /**
     * verify if a category, a query and the time for notification have been correctly set
     * if not, send toast to the user.
     *
     * @param keywords    (string) query entered in search field
     * @param categories  (list string) categories
     * @param time        (string) hour for notification (hh:mm)
     * @param switchNotif (bool) notif state
     * @return true if all criteria are filled
     */

    public static boolean validateNotifParam(String keywords, List<String> categories,
                                             String time, boolean switchNotif,
                                             ErrorListener errorListener) {
        if (switchNotif) {
            if (keywords == null || keywords.isEmpty()) {
                if (errorListener != null) {
                    errorListener.onShowErrorFromResources(R.string.verification_search_field);
                }
                return false;
            }
            if (categories.size() == 0) {
                if (errorListener != null) {
                    errorListener.onShowErrorFromResources(R.string.verification_checkbox);
                }
                return false;
            }
            if (time.equals("") || time.isEmpty()) {
                if (errorListener != null) {
                    errorListener.onShowErrorFromResources(R.string.verification_notif_time);
                }
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
}