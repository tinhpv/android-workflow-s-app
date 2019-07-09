package com.example.workflow_s.ui.checklistrunning;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Template;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-05
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public interface ChecklistRunningContract {

    interface ChecklistRunningPresenter {
        void getTemplateObject(String templateId, String userId, String orgId);
        void runChecklist(String userId, Template template);
    }

    interface ChecklistRunningView {
        void finishGetTemplateObject(Template template);
        void finishedRunChecklist(Checklist checklist);
    }

    interface ChecklistRunningData {
        interface OnFinishedGetTemplateObjectListener {
            void onFinishedGetTemplate(Template template);
            void onFailure(Throwable t);
        }

        interface OnFinishedRunChecklistListener {
            void onFinishedRunChecklist(Checklist checklist);
            void onFailure(Throwable t);
        }

        void getTemplateObject(String templateId, String orgId, String userId, OnFinishedGetTemplateObjectListener onFinishedListener);
        void runChecklist(String userId, Template template, OnFinishedRunChecklistListener listener);
    }
}
