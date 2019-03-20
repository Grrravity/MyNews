package com.error.grrravity.mynews.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.error.grrravity.mynews.controllers.fragments.PageFragment;

public class PagerAdapter extends FragmentPagerAdapter {


    // Default Constructor
    public PagerAdapter(FragmentManager mgr) {
        super(mgr);
    }
    @Override
    public Fragment getItem(int position){
        return PageFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    //Set the title on Tabs
    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position) {
            case 0: //Page number 1
                title = "Top Stories";
                break;
            case 1: //Page number 2
                title = "Most Popular";
                break;
            case 2: //Page number 3
                title = "SECTION NOT\r\nYET SELECTED";
                break;
        }
        return title;
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
    }
}