package com.example.workflow_s.ui.activity;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;

import java.util.ArrayList;

public interface ActivityContract {

    //presenter
    interface ActivityPresenter {
        void onDestroy();
        void loadAllChecklist(String organizationId);
        void loadFirstTaskFromChecklist(int checklistId, Checklist checklist);
    }

    //view
    interface ActivityView {
        void setDataToChecklistRecyclerView(ArrayList<Checklist> datasource);
        void finishedGetFirstTask(Task task, Checklist checklist);
    }

    //model
    interface GetActivitiesDataInteractor {
        interface OnFinishedGetChecklistListener {
            void onFinishedGetChecklist(ArrayList<Checklist> checklistArrayList);
            void onFailure(Throwable t);
        }

        interface OnFinishedGetFirstTaskListener {
            void onFinishedGetFirstTask(Task task, Checklist checklist);
            void onFailure(Throwable t);
        }

        void getFirstTask(int checklistId, Checklist checklist, OnFinishedGetFirstTaskListener onFinishedListener);
        void getAllChecklist(String organizationId, OnFinishedGetChecklistListener onFinishedGetChecklistListener);
    }
}
