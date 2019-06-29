package com.example.workflow_s.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workflow_s.R;
import com.example.workflow_s.ui.activity.adapters.ViewPagerAdapter;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-26
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ActivityFragment extends Fragment {

    private final static int NUMBER_OF_TABS = 2;
    private final static String TAG = "ACTIVITIES";

    View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabItem mTodayTabItem, mUpcomingTabItem;
    private ViewPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mTabLayout = view.findViewById(R.id.tab_layout);
        // TAB ITEM
        mTodayTabItem = view.findViewById(R.id.tab_today);
        mUpcomingTabItem = view.findViewById(R.id.tab_upcoming);
        mViewPager = view.findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), NUMBER_OF_TABS);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }


}
