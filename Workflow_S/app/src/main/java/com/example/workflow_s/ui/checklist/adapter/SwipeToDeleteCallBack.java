package com.example.workflow_s.ui.checklist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-16
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class SwipeToDeleteCallBack extends ItemTouchHelper.SimpleCallback {

    private CurrentChecklistAdapter mCurrentChecklistAdapter;

    public SwipeToDeleteCallBack(CurrentChecklistAdapter checklistAdapter) {
        super(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mCurrentChecklistAdapter = checklistAdapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int position = viewHolder.getAdapterPosition();
        mCurrentChecklistAdapter.deleteItem(position);
    }
}
