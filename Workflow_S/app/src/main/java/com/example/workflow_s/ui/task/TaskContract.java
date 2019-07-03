package com.example.workflow_s.ui.task;

import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.Template;
import com.example.workflow_s.ui.template.TemplateContract;

import java.util.ArrayList;

public interface TaskContract {

    //view comm with presenter
    interface TaskPresenter {
        void onDestroy();
        void loadTasks(int checklistId);
        void getTemplateObject(String templateId, String userId, String orgId);
        void runChecklist(String userId, Template template);
    }

    interface TaskView {
        void setDataToTaskRecyclerView(ArrayList<Task> datasource);
    }

    interface TemplateView {
        void setDataToTaskRecyclerView(ArrayList<Task> datasource);
        void finishGetTemplateObject(Template template);
        void finishedRunChecklist();
    }

    //presenter comm with model
    interface GetTaskDataInteractor {

        interface OnFinishedGetTasksListener {
            void onFinishedGetTasks(ArrayList<Task> taskArrayList);
            void onFailure(Throwable t);
        }

        interface OnFinishedGetTemplateObjectListener {
            void onFinishedGetTemplate(Template template);
            void onFailure(Throwable t);
        }

        interface OnFinishedRunChecklistListener {
            void onFinishedRunChecklist();
            void onFailure(Throwable t);
        }


        void getAllTasks(int checklistId, OnFinishedGetTasksListener onFinishedLIstener);
        void getTemplateObject(String templateId, String orgId, String userId, OnFinishedGetTemplateObjectListener onFinishedListener);
        void runChecklist(String userId, Template template, OnFinishedRunChecklistListener listener);
    }
}
