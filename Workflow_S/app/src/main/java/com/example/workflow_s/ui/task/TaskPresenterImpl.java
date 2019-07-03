package com.example.workflow_s.ui.task;

import android.util.Log;

import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.Template;

import java.util.ArrayList;

public class TaskPresenterImpl implements TaskContract.TaskPresenter,
                TaskContract.GetTaskDataInteractor.OnFinishedGetTasksListener,
        TaskContract.GetTaskDataInteractor.OnFinishedGetTemplateObjectListener,
        TaskContract.GetTaskDataInteractor.OnFinishedRunChecklistListener {

    private static final String TAG = "TASK_PRESENTER";
    private TaskContract.TaskView mTaskView;
    private TaskContract.TemplateView mTemplateView;
    private TaskContract.GetTaskDataInteractor mGetTaskDataInteractor;

    public TaskPresenterImpl(TaskContract.TaskView mTaskView, TaskContract.GetTaskDataInteractor mGetTaskDataInteractor) {
        this.mTaskView = mTaskView;
        this.mTemplateView = null;
        this.mGetTaskDataInteractor = mGetTaskDataInteractor;
    }

    public TaskPresenterImpl(TaskContract.TemplateView mTaskView, TaskContract.GetTaskDataInteractor mGetTaskDataInteractor) {
        this.mTemplateView = mTaskView;
        this.mTaskView = null;
        this.mGetTaskDataInteractor = mGetTaskDataInteractor;
    }


    @Override
    public void onDestroy() {
        mTaskView = null;
    }

    @Override
    public void loadTasks(int checklistId) {
        mGetTaskDataInteractor.getAllTasks(checklistId, this);
    }

    @Override
    public void getTemplateObject(String templateId, String userId, String orgId) {
        mGetTaskDataInteractor.getTemplateObject(templateId, orgId, userId, this);
    }

    @Override
    public void runChecklist(String userId, Template template) {
        mGetTaskDataInteractor.runChecklist(userId, template, this);
    }

    @Override
    public void onFinishedGetTasks(ArrayList<Task> taskArrayList) {
        if (mTaskView != null) {
            mTaskView.setDataToTaskRecyclerView(taskArrayList);
        } else {
            mTemplateView.setDataToTaskRecyclerView(taskArrayList);
        }
    }

    @Override
    public void onFinishedGetTemplate(Template template) {
        mTemplateView.finishGetTemplateObject(template);
    }

    @Override
    public void onFinishedRunChecklist() {
        mTemplateView.finishedRunChecklist();
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
