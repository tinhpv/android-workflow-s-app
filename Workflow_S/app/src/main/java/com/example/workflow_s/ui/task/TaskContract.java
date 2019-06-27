package com.example.workflow_s.ui.task;

import com.example.workflow_s.model.Task;

import java.util.ArrayList;

public interface TaskContract {

    //view comm with presenter
    interface TaskPresenter {
        void onDestroy();
        void loadTasks(int checklistId);
    }

    interface TaskView {
        void setDataToTaskRecyclerView(ArrayList<Task> datasource);
    }

    //presenter comm with model
    interface GetTaskDataInteractor {
        interface OnFinishedGetTasksListener {
            void onFinishedGetTasks(ArrayList<Task> taskArrayList);
            void onFailure(Throwable t);
        }

        void getAllTasks(int checklistId, OnFinishedGetTasksListener onFinishedLIstener);
    }
}
