package com.error.grrravity.mynews.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.error.grrravity.mynews.controllers.fragments.PageFragment;

import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private final List<PageFragment> mPageFragment;

    // Default Constructor
    public PagerAdapter(FragmentManager mgr, List<PageFragment> pageFragment) {
        super(mgr);
        mPageFragment = pageFragment;
    }

    //Switch fragment depending on position
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mPageFragment.get(position);
            case 1:
                return mPageFragment.get(position);
            case 2:
                return mPageFragment.get(position);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    //Set title on Tabs
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0: //Page number 1
                title = "Top Stories";
                break;
            case 1: //Page number 2
                title = "Most Popular";
                break;
            case 2: //Page number 3
                title = "Section not\r\nyet selected";
                break;
        }
        return title;
    }
}