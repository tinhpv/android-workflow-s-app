package com.example.workflow_s.ui.checklist;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.ui.checklist.adapter.AccomplishedChecklistAdapter;
import com.example.workflow_s.ui.checklist.adapter.ChecklistPagerAdapter;
import com.example.workflow_s.ui.checklist.adapter.ChecklistProgressAdapter;
import com.example.workflow_s.ui.organization.adapter.OrganizationMemberAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistFragment extends Fragment {

    private final static int NUMBER_OF_TABS = 2;
    private final static String TAG = "CHECKLIST";

    View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabItem mCurrentTabItem, mAllTabItem;
    private ChecklistPagerAdapter adapter;

    private ChecklistContract.ChecklistPresenter mPresenter;

    public ChecklistFragment(){}

    public static ChecklistFragment newInstance() {
        final ChecklistFragment checklistFragment = new ChecklistFragment();
        return checklistFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_checklist, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        setupActiveRV();
//        setupAccomplishedRV();
//        initData();
//
//        // FIXME - TEST LAYOUT WITH STATIC DATASOURCE HERE
//        checkoutAndCategorizeData();
        mTabLayout = view.findViewById(R.id.tab_checklist_layout);

        mCurrentTabItem = view.findViewById(R.id.tab_current);
        mAllTabItem = view.findViewById(R.id.tab_all);

        mViewPager = view.findViewById(R.id.view_checklist_pager);
        adapter = new ChecklistPagerAdapter(getChildFragmentManager(), NUMBER_OF_TABS);

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }


}
