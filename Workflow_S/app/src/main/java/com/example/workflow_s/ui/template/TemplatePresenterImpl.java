package com.example.workflow_s.ui.template;

import android.widget.Toast;

import com.example.workflow_s.model.Template;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-27
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TemplatePresenterImpl implements TemplateContract.TemplatePresenter, TemplateContract.GetTemplateDataContract.OnFinishedGetTemplateDataListener {

    private TemplateContract.TemplateView mTemplateView;
    private TemplateContract.GetTemplateDataContract mTemplateDataContract;

    public TemplatePresenterImpl(TemplateContract.TemplateView templateView, TemplateContract.GetTemplateDataContract templateDataContract) {
        mTemplateView = templateView;
        mTemplateDataContract = templateDataContract;
    }

    @Override
    public void requestTemplateData(String orgId, String userId) {
        mTemplateDataContract.getAllTemplates(orgId, userId, this);
    }

    @Override
    public void onFinishedGetTemplates(ArrayList<Template> templateList) {
        mTemplateView.finishGetTemplates(templateList);
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
