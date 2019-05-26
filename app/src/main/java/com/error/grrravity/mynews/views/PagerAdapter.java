package com.error.grrravity.mynews.views;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.error.grrravity.mynews.controllers.fragments.PageFragment;
import com.error.grrravity.mynews.utils.Preferences;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {

    private final List<PageFragment> mPageFragment;
    private static Preferences mPreferences;

    // Default Constructor
    public PagerAdapter(FragmentManager mgr, List<PageFragment> pageFragment, Context context) {
        super(mgr);
        mPageFragment = pageFragment;
        mPreferences = Preferences.getInstance(context);
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
        switch (position) {
            case 0: //Page number 1
                return "Top Stories";
            case 1: //Page number 2
                return "Most Popular";
            case 2: //Page number 3
                ArrayList<String> category = mPreferences.getCategory(0);
                if (category != null && category.size() > 0) {
                    return category.get(0);
                } else {
                    return "Section not\r\nyet selected";
                }
            default:
                return null;
        }
    }
}