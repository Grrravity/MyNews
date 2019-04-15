package com.error.grrravity.mynews.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Preferences {
    private static String NOTIFCATEGORIES = "NOTIFCATEGORIES";
    private static String SEARCHCATEGORIES = "SEARCHCATEGORIES";
    private static String TABCATEGORY = "TABCATEGORY";
    private static String NOTIFTIME = "NOTIFTIME";
    private static Preferences mInstance;

    private SharedPreferences mPreferences;

    private Preferences (Context context) {

        mPreferences = context.getSharedPreferences(NOTIFCATEGORIES, Activity.MODE_PRIVATE);
        mPreferences = context.getSharedPreferences(SEARCHCATEGORIES, Activity.MODE_PRIVATE);
        mPreferences = context.getSharedPreferences(TABCATEGORY, Activity.MODE_PRIVATE);
        mPreferences = context.getSharedPreferences(NOTIFTIME, Activity.MODE_PRIVATE);
    }

    public static Preferences getInstance(Context context) {
        if (mInstance == null)
            mInstance = new Preferences(context);
        return mInstance;
    }

    /**
     * uses gson to store categories as an Arraylist of strings.
     *
     * @param category : list of categories.
     * @param source : 0 is for tab category
     *               1 is for search category
     *               2 is for notification category
     *               3 is for notification time
     *               default is tab category.
     */
    public void storePref(int source, ArrayList<String> category) {
        String prefSource = TABCATEGORY;
        switch (source){
            case 0: prefSource = TABCATEGORY; break;
            case 1: prefSource = SEARCHCATEGORIES; break;
            case 2: prefSource = NOTIFCATEGORIES; break;
            case 3: prefSource = NOTIFTIME; break;
            default: break;
        }
        //start writing (open the file)
        SharedPreferences.Editor editor = mPreferences.edit();
        //put the data
        Gson gson = new Gson();
        String json = gson.toJson(category);
        editor.putString(prefSource, json);
        //close the file
        editor.apply();
    }

    /**
     * use gson to send back an ArrayList of categories from String.
     *
     * @return  : ArrayList of categories stored.
     * @param  source : 0 is for tab category
     *                1 is for search category
     *                2 is for notification category
     *                3 is for notification time
     *               default is tab category.
     */
    public ArrayList<String> getPref(int source) {
        String targetedPref = TABCATEGORY;
        switch (source){
            case 0: targetedPref = TABCATEGORY; break;
            case 1: targetedPref = SEARCHCATEGORIES; break;
            case 2: targetedPref = NOTIFCATEGORIES; break;
            case 3: targetedPref = NOTIFTIME; break;
            default: break;
        }
        Gson gson = new Gson();
        String json = mPreferences.getString(targetedPref, "");

        ArrayList<String> category;

        if (json.length() < 1) {
            category = new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            category = gson.fromJson(json, type);
        }
        return category;
    }

    // FOR TEST PURPOSE

    // store test
    public void storeTestList(List<String> testList) {

        SharedPreferences.Editor editor = mPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(testList);
        editor.putString("test", json);
        editor.apply();
    }

    //get test
    public ArrayList<String> getTestList() {
        Gson gson = new Gson();
        String json = mPreferences.getString("test", "");
        ArrayList<String> testList;
        if (json.length() < 1) {
            testList = new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            testList = gson.fromJson(json, type);
        }
        return testList;
    }
}