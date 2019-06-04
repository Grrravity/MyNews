package com.error.grrravity.mynews.utils;

import com.error.grrravity.mynews.R;

public class HelpHelper {

    private static boolean mSubjectFocus;
    private static boolean mMessageFocus;

    /**
     * Verify if requirement is filled to send a mail from Help activity
     *
     * @param subject : (string) mail subject from the spinner. default is '- Please select a subject -'
     * @param message : (string) message written by the user.
     * @return validation int. 0 is when missing subject, 1 is when missing message and 2 is OK.
     */

    public static boolean validateHelpInfo(String subject, String message,
                                           ErrorListener errorListener) {
        if (subject.equals("- Please select a subject -")) {
            if (errorListener != null) {
                errorListener.onShowErrorFromResources(R.string.verification_help_object);
            }
            mSubjectFocus = true;
            return false;
        }
        if (message.isEmpty()){
            mMessageFocus = true;
            return false;
        }
        return true;
    }

    //get focus value. required to make validateSearchParam() working on unit testing.
    public static boolean getSubjectFocus(){
        return mSubjectFocus;
    }

    //get focus value. required to make validateSearchParam() working on unit testing.
    public static boolean getMessageFocus(){
        return mMessageFocus;
    }
}
