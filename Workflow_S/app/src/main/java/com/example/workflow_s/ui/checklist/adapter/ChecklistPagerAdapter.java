package com.example.workflow_s.ui.checklist.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.workflow_s.ui.checklist.fragments.AllChecklistFragment;
import com.example.workflow_s.ui.checklist.fragments.CurrentChecklistFragment;

public class ChecklistPagerAdapter extends FragmentPagerAdapter {

    private int numberOfTabs;

    public ChecklistPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new CurrentChecklistFragment();
            case 1:
                return new AllChecklistFragment();
            default:
                    return null;
        } //end switch
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Current";
            case 1:
                return "All";
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}