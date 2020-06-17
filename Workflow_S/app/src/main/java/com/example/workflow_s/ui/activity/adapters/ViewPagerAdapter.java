package com.example.workflow_s.ui.activity.adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.workflow_s.ui.activity.fragments.TodayActivitiesFragment;
import com.example.workflow_s.ui.activity.fragments.UpcomingActivitiesFragment;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-25
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ViewPagerAdapter extends FragmentPagerAdapter  {

    private int numberOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new TodayActivitiesFragment();
            case 1:
                return new UpcomingActivitiesFragment();
            default:
                return null;
        } // end switch
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Today";
            case 1:
                return "Upcoming";
            default:
                return null;
        } // end switch
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
