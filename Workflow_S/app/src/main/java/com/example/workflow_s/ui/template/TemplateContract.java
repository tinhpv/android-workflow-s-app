package com.example.workflow_s.ui.template;

import com.example.workflow_s.model.Template;
import com.example.workflow_s.model.User;
import com.example.workflow_s.ui.organization.OrganizationContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-27
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public interface TemplateContract {

    interface TemplatePresenter {
        void requestTemplateData(String orgId);
    }

    interface TemplateView {
        void finishGetTemplates(List<Template> templateList);
    }

    interface GetTemplateDataContract {
        interface OnFinishedGetTemplateDataListener {
            void onFinishedGetTemplates(ArrayList<Template> templateList);
            void onFailure(Throwable t);
        }

        void getAllTemplates(String orgId, OnFinishedGetTemplateDataListener onFinishedListener);
    }
}
