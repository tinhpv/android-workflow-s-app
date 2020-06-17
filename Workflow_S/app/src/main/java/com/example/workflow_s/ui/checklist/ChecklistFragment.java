package com.example.workflow_s.ui.checklist;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.workflow_s.R;
import com.example.workflow_s.ui.checklist.adapter.ChecklistPagerAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

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
        getActivity().setTitle("Checklist");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
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

        //intent status
       Bundle args = getArguments();
       int statusChecklist = args.getInt("status_checklist");
       adapter.setStatus(statusChecklist);

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
}
