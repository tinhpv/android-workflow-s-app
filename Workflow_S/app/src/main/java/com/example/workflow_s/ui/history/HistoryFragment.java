package com.example.workflow_s.ui.history;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workflow_s.R;
import com.example.workflow_s.model.Notification;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-25
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class HistoryFragment extends Fragment implements HistoryContract.HistoryView {

    View view;

    private RecyclerView historyRv;
    private HistoryAdapter mHistoryAdapter;
    private RecyclerView.LayoutManager historyLayoutManager;

    private LinearLayout historyDataNotFound;

    private ShimmerFrameLayout mShimmerFrameLayout;
    private HistoryContract.HistoryPresenter mHistoryPresenter;


    int taskId;

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

    @Override
    public void onResume() {
        super.onResume();
        mShimmerFrameLayout.setVisibility(View.VISIBLE);
        mShimmerFrameLayout.startShimmerAnimation();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        getActivity().setTitle("Task Activities");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mShimmerFrameLayout = view.findViewById(R.id.history_shimmer_view_container);
        historyDataNotFound = view.findViewById(R.id.history_data_notfound_message);
        setupRV();
        requestData();
    }

    private void setupRV() {
        historyRv = view.findViewById(R.id.rv_history);
        historyLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        historyRv.setLayoutManager(historyLayoutManager);

        mHistoryAdapter = new HistoryAdapter(getContext());
        historyRv.setAdapter(mHistoryAdapter);
    }

    private void requestData() {
        taskId = getArguments().getInt("taskId");
        mHistoryPresenter = new HistoryPresenterImpl(new HistoryInteractor(), this);
        mHistoryPresenter.getActivities(taskId);
    }

    @Override
    public void finishedGetTaskActivities(List<Notification> notificationList) {
        mShimmerFrameLayout.stopShimmerAnimation();
        mShimmerFrameLayout.setVisibility(View.GONE);

        if (null != notificationList) {
            if (notificationList.size() == 0) {
                historyDataNotFound.setVisibility(View.VISIBLE);
            } else {
                mHistoryAdapter.setActivityList(notificationList);
            }
        }
    }
}
