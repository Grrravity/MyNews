package com.error.grrravity.mynews.utils;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PreferencesTest {

    private Preferences mPreference;

    @Before
    public void setUp()  {
        Context context = InstrumentationRegistry.getTargetContext();
        mPreference = Preferences.getInstance(context);
    }

    @After
    public void tearDown() {
        mPreference = null;
    }


    //Store list and assert is equals
    @Test
    public void storeList(){
        List<String> testList = new ArrayList<>();
        testList.add("hello");
        testList.add("world");
        mPreference.storeTestList(testList);
        Assert.assertEquals(testList, getList());
    }


    private List<String> getList(){
        return mPreference.getTestList();
    }

}
