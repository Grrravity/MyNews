package com.error.grrravity.mynews;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.error.grrravity.mynews.utils.Preferences;

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


    //Store liste and assert is equals
    @Test
    public void storeList(){
        List<String> testList = new ArrayList<>();
        testList.add("hello");
        testList.add("openClassrooms");
        mPreference.storeTestList(testList);
        Assert.assertEquals(testList, getList());
    }


    private List<String> getList(){
        return mPreference.getTestList();
    }

}
