package com.example.workflow_s.ui.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
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

    private ActivityContract.ActivityPresenter mPresenter;

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
        adapter = new ViewPagerAdapter(getChildFragmentManager(), NUMBER_OF_TABS);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().setTitle("Home");
    }

}
