package com.example.workflow_s.ui.checklist;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.ui.checklist.adapter.AccomplishedChecklistAdapter;
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

    View view;
    private RecyclerView activeChecklistRecyclerView, accomplishedChecklistRecyclerView;
    private ChecklistProgressAdapter mActiveChecklistProgressAdapter;
    private AccomplishedChecklistAdapter mAccomplishedChecklistAdapter;
    private RecyclerView.LayoutManager activeChecklistLayoutManager, accomplishedChecklistLayoutManager;

    List<Checklist> mChecklists, mActiveChecklists, mAccomplisedChecklists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_checklist, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupActiveRV();
        setupAccomplishedRV();
        initData();

        // FIXME - TEST LAYOUT WITH STATIC DATASOURCE HERE
        checkoutAndCategorizeData();
    }

    private void checkoutAndCategorizeData() {
        mActiveChecklists = new ArrayList<>();
        mAccomplisedChecklists = new ArrayList<>();

        for (Checklist checklist : mChecklists) {
            if (checklist.getDoneTask() == checklist.getTotalTask()) {
                mAccomplisedChecklists.add(checklist);
            } else {
                mActiveChecklists.add(checklist);
            }
        } //end for

        mAccomplishedChecklistAdapter.setChecklists(mAccomplisedChecklists);
        mActiveChecklistProgressAdapter.setChecklists(mActiveChecklists);

    }

    private void initData() {
        mChecklists = new ArrayList<>();
        mChecklists.add(new Checklist(1, "123", "Vu Tinh 24h", "abc", "12h30'", 1, 1, 1, 5, 2));
        mChecklists.add(new Checklist(2, "124", "Tien Nguyen 14/2", "abc", "12h30'", 1, 1, 1, 5, 2));
        mChecklists.add(new Checklist(3, "125", "Phat Lam 12/3", "abc", "12h30'", 1, 1, 1, 5, 2));
        mChecklists.add(new Checklist(4, "126", "Cao Trang 5/12", "abc", "12h30'", 1, 1, 1, 5, 5));
        mChecklists.add(new Checklist(5, "128", "Vietnam 6788", "abc", "12h30'", 1, 1, 1, 5, 5));
    }

    private void setupActiveRV() {
        activeChecklistRecyclerView = view.findViewById(R.id.rv_in_progress_checklist);
        activeChecklistRecyclerView.setHasFixedSize(true);
        activeChecklistLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        activeChecklistRecyclerView.setLayoutManager(activeChecklistLayoutManager);

        mActiveChecklistProgressAdapter = new ChecklistProgressAdapter();
        activeChecklistRecyclerView.setAdapter(mActiveChecklistProgressAdapter);
    }

    private void setupAccomplishedRV() {
        accomplishedChecklistRecyclerView = view.findViewById(R.id.rv_accomplished_checklist);
        accomplishedChecklistRecyclerView.setHasFixedSize(true);
        accomplishedChecklistLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        accomplishedChecklistRecyclerView.setLayoutManager(accomplishedChecklistLayoutManager);

        mAccomplishedChecklistAdapter = new AccomplishedChecklistAdapter();
        accomplishedChecklistRecyclerView.setAdapter(mAccomplishedChecklistAdapter);
    }
}
