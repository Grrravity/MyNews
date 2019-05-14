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
    private static String MYPREF = "MYPREF";
    private static Preferences mInstance;

    private static final String NOTIFCATEGORIES = "NOTIFCATEGORIES";

    private SharedPreferences mPreferences;

    private Preferences (Context context) {

        mPreferences = context.getSharedPreferences(MYPREF, Activity.MODE_PRIVATE);
    }

    public static Preferences getInstance(Context context) {
        if (mInstance == null)
            mInstance = new Preferences(context);
        return mInstance;
    }

    //TODO Clean preferences.

    /**
     * uses gson to store categories as an Arraylist of strings.
     *
     * @param category : list of categories.
     * @param source : 0 is for tab category
     *               1 is for search category
     *               2 is for notification category
     *               default is tab category.
     */
    public void storeCategory(int source, ArrayList<String> category) {
        String prefSource = "TABCATEGORY";
        switch (source){
            case 0: prefSource = "TABCATEGORY"; break;
            case 1: prefSource = "SEARCHCATEGORIES"; break;
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
     *               default is tab category.
     */
    public ArrayList<String> getCategory(int source) {
        String targetedPref = "TABCATEGORY";
        switch (source){
            case 0: targetedPref = "TABCATEGORY"; break;
            case 1: targetedPref = "SEARCHCATEGORIES"; break;
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

    //storeCategories change ArrayList into json strings and save it
    public void storeNotifCategories(List<String> categories) {
        //start writing (open the file)
        SharedPreferences.Editor editor = mPreferences.edit();
        //put the data
        Gson gson = new Gson();
        String json = gson.toJson(categories);
        editor.putString(NOTIFCATEGORIES, json);
        //close the file
        editor.apply();
    }

    //getCategories recovers json strings and return there in ArrayList
    public ArrayList<String> getNotifCategories() {
        Gson gson = new Gson();
        String json = mPreferences.getString(NOTIFCATEGORIES, "");

        ArrayList<String> categories;

        if (json.length() < 1) {
            categories = new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<String>>() {
            }.getType();
            categories = gson.fromJson(json, type);
        }

        //return the value that was stored under the key

        return categories;
    }

    public void storeNotifQuery (String notifQuery){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove("NOTIFQUERY");
        editor.putString("NOTIFQUERY", notifQuery);
        editor.apply();
    }

    public String getNotifQuery () {
        return mPreferences.getString("NOTIFQUERY", "");
    }

    public void storeNotifBoolean (Boolean notifBool){
        SharedPreferences.Editor editor=mPreferences.edit();
        editor.remove("NOTIFENABLE");
        editor.putBoolean("NOTIFENABLE", notifBool);
        editor.apply();
    }

    public Boolean getNotifBoolean(){
        return mPreferences.getBoolean("NOTIFENABLE", false);
    }

    public void storeNotifTime(String time) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.remove("NOTIFTIME");
        editor.putString("NOTIFTIME", time);
        editor.apply();
    }

    public String getNotifTime () {return mPreferences.getString("NOTIFTIME", "");}

    public void storeTestList ( List<String> testList) {
        String prefSource = "TEST";

        //start writing (open the file)
        SharedPreferences.Editor editor = mPreferences.edit();
        //put the data
        Gson gson = new Gson();
        String json = gson.toJson(testList);
        editor.putString(prefSource, json);
        //close the file
        editor.apply();
    }

    //get test
    public ArrayList<String> getTestList() {
        Gson gson = new Gson();
        String json = mPreferences.getString("TEST", "");
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