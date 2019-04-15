package com.error.grrravity.mynews.views;

import android.support.test.rule.ActivityTestRule;

import com.error.grrravity.mynews.controllers.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

//Test ViewPager
public class TestPagerAdapter {

    private MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mActivity = mainActivityActivityTestRule.getActivity();
    }

    //Get item at position 2
    @Test
    public void runTestPager() throws Throwable {
        getItem(2);
    }

    //Number of pages (should be 3)
    @Test
    public void countReturn() {
        assertEquals(3, Objects.requireNonNull(mActivity.mViewPager.getAdapter())
                .getCount());
    }

    //Test if title are correct
    @Test
    public void titleCorrect() {
        assertEquals("Top Stories",
                Objects.requireNonNull(mActivity.mViewPager.getAdapter())
                        .getPageTitle(0));
        assertEquals("Most Popular",
                Objects.requireNonNull(mActivity.mViewPager.getAdapter())
                        .getPageTitle(1));
        assertEquals("Section not\nyet selected",
                Objects.requireNonNull(mActivity.mViewPager.getAdapter())
                        .getPageTitle(2));
    }

    //Return the good item
    private void getItem(final int i) throws Throwable {
        mainActivityActivityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivity.mViewPager.setCurrentItem(i);
            }
        });

        Thread.sleep(200);
        assertEquals(Objects.requireNonNull(mActivity.mViewPager.getAdapter()).getPageTitle(i),
                mActivity.mViewPager.getAdapter()
                        .getPageTitle(mActivity.mViewPager.getCurrentItem()));
    }
}