package com.example.workflow_s.ui.checklist;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.Template;

import java.util.ArrayList;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public interface ChecklistContract {

    //presenter
    interface ChecklistPresenter {
        void onDestroy();
        void loadAllChecklist(String organizationId);
        void loadFirstTaskFromChecklist(int checklistId, Checklist parentChecklistOfThisTask);
        void requestTemplateData(String orgId);
    }

    //view
    interface ChecklistView {
        void setDataToChecklistRecyclerView(ArrayList<Checklist> datasource);
        void finishFirstTaskFromChecklist(Task task, Checklist parentChecklistOfThisTask);
        void finishGetTemplates(List<Template> templateList);
    }

    //view
    interface AllChecklistView {
        void setDataToChecklistRecyclerView(ArrayList<Checklist> datasource);
        void finishGetTemplates(List<Template> templateList);

    }

    //model
    interface GetChecklistsDataInteractor {
        interface OnFinishedGetChecklistListener {
            void onFinishedGetChecklist(ArrayList<Checklist> checklistArrayList);
            void onFailure(Throwable t);
        }

        interface OnFinishedGetFirstTaskFormChecklistListener {
            void onFinishedGetFirstTask(Task task, Checklist parentChecklistOfThisTask);
            void onFailure(Throwable t);
        }

        interface OnFinishedGetTemplateDataListener {
            void onFinishedGetTemplates(ArrayList<Template> templateList);
            void onFailure(Throwable t);
        }

        void getAllTemplates(String orgId, OnFinishedGetTemplateDataListener onFinishedListener);

        void getAllChecklist(String organizationId, OnFinishedGetChecklistListener onFinishedGetChecklistListener);
        void getFirstTask(int checklistId, Checklist parentChecklistOfThisTask, OnFinishedGetFirstTaskFormChecklistListener onFinishedListener);
    }
}
