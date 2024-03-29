package com.example.zoomsoft.eventInfo;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.zoomsoft.ui.main.PlaceholderFragment;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of either the habit viewing tab or the habit event tab.
 */
public class SectionsPagerAdapterEvent extends FragmentPagerAdapter {

//    @StringRes
//    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;



    public SectionsPagerAdapterEvent(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // getItem is called to instantiate the fragment for the given page.
        if (position == 0){
            return new HabitInfoDisplay();
        }
        if (position == 1){
            return new HabitEventDisplay();
        }
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "INFO";
            case 1:
                return "EVENT";
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}