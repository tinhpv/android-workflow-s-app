package com.example.workflow_s.ui.checklist;

import android.util.Log;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.Template;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistPresenterImpl implements ChecklistContract.ChecklistPresenter,
            ChecklistContract.GetChecklistsDataInteractor.OnFinishedGetChecklistListener,
            ChecklistContract.GetChecklistsDataInteractor.OnFinishedGetFirstTaskFormChecklistListener,
            ChecklistContract.GetChecklistsDataInteractor.OnFinishedGetTemplateDataListener,
ChecklistContract.GetChecklistsDataInteractor.OnFinishedDeleteChecklistListener,
        ChecklistContract.GetChecklistsDataInteractor.OnFinishedSetNameOfChecklist{

    private static final String TAG = "CHECKLISTS_PRESENTER";
    private ChecklistContract.ChecklistView mChecklistView;
    private ChecklistContract.AllChecklistView mAllChecklistView;
    private ChecklistContract.GetChecklistsDataInteractor mChecklistDataInteractor;

    public ChecklistPresenterImpl(ChecklistContract.ChecklistView mChecklistView, ChecklistContract.GetChecklistsDataInteractor mChecklistDataInteractor) {
        this.mChecklistView = mChecklistView;
        this.mAllChecklistView = null;
        this.mChecklistDataInteractor = mChecklistDataInteractor;
    }

    public ChecklistPresenterImpl(ChecklistContract.AllChecklistView allChecklistView, ChecklistContract.GetChecklistsDataInteractor checklistDataInteractor) {
        mAllChecklistView = allChecklistView;
        mChecklistView = null;
        mChecklistDataInteractor = checklistDataInteractor;
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
    public void loadFirstTaskFromChecklist(int checklistId, Checklist parentChecklistOfThisTask) {
        mChecklistDataInteractor.getFirstTask(checklistId, parentChecklistOfThisTask,this);
    }

    @Override
    public void requestTemplateData(String orgId) {
        mChecklistDataInteractor.getAllTemplates(orgId, this);
    }

    @Override
    public void deleteChecklist(int checklistId, String userId) {
        mChecklistDataInteractor.deleteChecklist(checklistId, userId, this);
    }

    @Override
    public void setNameOfChecklist(int checklistId, String name) {
        mChecklistDataInteractor.setNameOfChecklist(checklistId, name, this);
    }

    @Override
    public void onFinishedGetChecklist(ArrayList<Checklist> checklistArrayList) {
        if (mChecklistView != null) {
            mChecklistView.setDataToChecklistRecyclerView(checklistArrayList);
        } else {
            mAllChecklistView.setDataToChecklistRecyclerView(checklistArrayList);
        } // end if
    }

    @Override
    public void onFinishedGetFirstTask(Task task, Checklist parentChecklistOfThisTask) {
        mChecklistView.finishFirstTaskFromChecklist(task, parentChecklistOfThisTask);
    }

    @Override
    public void onFinishedGetTemplates(ArrayList<Template> templateList) {
        if (mChecklistView != null) {
            mChecklistView.finishGetTemplates(templateList);
        } else {
            mAllChecklistView.finishGetTemplates(templateList);
        }
    }

    @Override
    public void onFinishedDeleteChecklist() {
        if (mChecklistView != null) {
            mChecklistView.finishDeleteChecklist();
        } else {
            mAllChecklistView.finishDeleteChecklist();
        }
    }

    @Override
    public void onFinishedSetNameChecklist() {

    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
