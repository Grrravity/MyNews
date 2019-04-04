package com.error.grrravity.mynews.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class Preferences {
    private static String NOTIFCATEGORIES = "NOTIFCATEGORIES";
    private static String SEARCHCATEGORIES = "SEARCHCATEGORIES";
    private static String TABCATEGORY = "TABCATEGORY";
    private static Preferences mInstance;

    private SharedPreferences mPreferences;

    private Preferences (Context context) {

        mPreferences = context.getSharedPreferences(NOTIFCATEGORIES, Activity.MODE_PRIVATE);
        mPreferences = context.getSharedPreferences(SEARCHCATEGORIES, Activity.MODE_PRIVATE);
        mPreferences = context.getSharedPreferences(TABCATEGORY, Activity.MODE_PRIVATE);
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
     *               default is tab category.
     */
    public void storeCategory(int source, ArrayList<String> category) {
        String fromSource = TABCATEGORY;
        switch (source){
            case 0: fromSource = TABCATEGORY; break;
            case 1: fromSource = SEARCHCATEGORIES; break;
            case 2: fromSource = NOTIFCATEGORIES; break;
            default: Log.e("Test", "Source to store category is not selected"); break;
        }
        //start writing (open the file)
        SharedPreferences.Editor editor = mPreferences.edit();
        //put the data
        Gson gson = new Gson();
        String json = gson.toJson(category);
        editor.putString(fromSource, json);
        //close the file
        editor.apply();
    }

    /**
     * use gson to send back an ArrayList of categories from String.
     *
     * @return  : ArrayList of categories stored.
     * @param  source : 0 is for tab category
     *                1 is for search category
     *               2 is for notification category
     *               default is tab category.
     */
    public ArrayList<String> getCategory(int source) {
        String targetedCategory = TABCATEGORY;
        switch (source){
            case 0: targetedCategory = TABCATEGORY; break;
            case 1: targetedCategory = SEARCHCATEGORIES; break;
            case 2: targetedCategory = NOTIFCATEGORIES; break;
            default: Log.e("Test",
                    "targeted category to get string from is not selected");
            break;
        }
        Gson gson = new Gson();
        String json = mPreferences.getString(targetedCategory, "");

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

}