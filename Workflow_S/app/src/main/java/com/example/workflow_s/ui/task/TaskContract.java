package com.example.workflow_s.ui.task;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.checklist.ChecklistContract;

import java.util.ArrayList;

public interface TaskContract {

    // view comm with presenter
    interface TaskPresenter {
        void onDestroy();
        void loadChecklistData(int orgId, int checklistId);
        void loadTasks(int checklistId);
        void changeTaskStatus(int taskId, String taskStatus);
        void changeChecklistStatus(int checklistId, String status);
    }

    interface TaskView {
        void finishGetChecklist(Checklist checklist);
        void finishedChangeTaskStatus();
        void finishChangeChecklistStatus();
    }

    interface TemplateView {
        void setDataToTaskRecyclerView(ArrayList<Task> datasource);
    }

    //presenter comm with model
    interface GetTaskDataInteractor {

        interface OnFinishedGetTasksListener {
            void onFinishedGetTasks(ArrayList<Task> taskArrayList);
            void onFailure(Throwable t);
        }

        interface OnFinishedChangeTaskStatusListener {
            void onFinishedChangeTaskStatus();
            void onFailure(Throwable t);
        }

        interface OnFinishedChangeChecklistStatusListener {
            void onFinishedChangeChecklistStatus();
            void onFailure(Throwable t);
        }

        interface OnFinishedLoadChecklistDataListener {
            void onFinishedGetChecklistData(Checklist checklist);
            void onFailure(Throwable t);
        }

        void getAllTasks(int checklistId, OnFinishedGetTasksListener onFinishedLIstener);
        void completeTask(int taskId, String taskStatus, OnFinishedChangeTaskStatusListener listener);
        void completeChecklist(int checklistId, String taskStatus, OnFinishedChangeChecklistStatusListener listener);
        void getChecklistData(int orgId, int checklistId, OnFinishedLoadChecklistDataListener listener);

    }
}
