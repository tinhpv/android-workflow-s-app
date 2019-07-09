package com.example.workflow_s.ui.checklistrunning;

import android.util.Log;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Template;
import com.example.workflow_s.ui.template.TemplateContract;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-05
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistRunningPresenterImpl implements ChecklistRunningContract.ChecklistRunningPresenter,
        ChecklistRunningContract.ChecklistRunningData.OnFinishedGetTemplateObjectListener,
        ChecklistRunningContract.ChecklistRunningData.OnFinishedRunChecklistListener {

    private ChecklistRunningContract.ChecklistRunningView mRunningView;
    private ChecklistRunningContract.ChecklistRunningData mRunningData;

    public ChecklistRunningPresenterImpl(ChecklistRunningContract.ChecklistRunningView runningView, ChecklistRunningContract.ChecklistRunningData runningData) {
        mRunningView = runningView;
        mRunningData = runningData;
    }

    @Override
    public void getTemplateObject(String templateId, String userId, String orgId) {
        mRunningData.getTemplateObject(templateId, orgId, userId, this);
    }

    @Override
    public void runChecklist(String userId, Template template) {
        mRunningData.runChecklist(userId, template, this);
    }

    @Override
    public void onFinishedRunChecklist(Checklist checklist) {
        mRunningView.finishedRunChecklist(checklist);
    }

    @Override
    public void onFinishedGetTemplate(Template template) {
        mRunningView.finishGetTemplateObject(template);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d("TEST", "onFailure: FAIL TO DO ANYTHING");
    }
}
