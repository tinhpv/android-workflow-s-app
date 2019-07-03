package com.example.workflow_s.ui.checklist;

import android.util.Log;

import com.example.workflow_s.model.Checklist;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistPresenterImpl implements ChecklistContract.ChecklistPresenter,
            ChecklistContract.GetChecklistsDataInteractor.OnFinishedGetChecklistListener{

    private static final String TAG = "CHECKLISTS_PRESENTER";
    private ChecklistContract.ChecklistView mChecklistView;
    private ChecklistContract.GetChecklistsDataInteractor mChecklistDataInteractor;

    public ChecklistPresenterImpl(ChecklistContract.ChecklistView mChecklistView, ChecklistContract.GetChecklistsDataInteractor mChecklistDataInteractor) {
        this.mChecklistView = mChecklistView;
        this.mChecklistDataInteractor = mChecklistDataInteractor;
    }

    @Override
    public void onDestroy() {
        mChecklistView = null;
    }

    @Override
    public void loadAllChecklist(String organizationId) {
        mChecklistDataInteractor.getAllChecklist(organizationId, this);
    }

    @Override
    public void onFinishedGetChecklist(ArrayList<Checklist> checklistArrayList) {
        mChecklistView.setDataToChecklistRecyclerView(checklistArrayList);
        Log.i("ChecklistView", checklistArrayList.isEmpty() + "");
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
