package com.example.workflow_s.ui.task;

import android.util.Log;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;

import java.util.ArrayList;

public class TaskStatusPresenterImpl implements TaskContract.TaskPresenter,
                TaskContract.GetTaskDataInteractor.OnFinishedGetTasksListener,
        TaskContract.GetTaskDataInteractor.OnFinishedChangeTaskStatusListener,
        TaskContract.GetTaskDataInteractor.OnFinishedLoadChecklistDataListener {

    private static final String TAG = "TASK_PRESENTER";
    private TaskContract.TaskView mTaskView;
    private TaskContract.TemplateView mTemplateView;
    private TaskContract.GetTaskDataInteractor mGetTaskDataInteractor;

    public TaskStatusPresenterImpl(TaskContract.TaskView mTaskView, TaskContract.GetTaskDataInteractor mGetTaskDataInteractor) {
        this.mTaskView = mTaskView;
        this.mTemplateView = null;
        this.mGetTaskDataInteractor = mGetTaskDataInteractor;
    }

    public TaskStatusPresenterImpl(TaskContract.TemplateView mTaskView, TaskContract.GetTaskDataInteractor mGetTaskDataInteractor) {
        this.mTemplateView = mTaskView;
        this.mTaskView = null;
        this.mGetTaskDataInteractor = mGetTaskDataInteractor;
    }

    @Override
    public void loadChecklistData(int orgId, int checklistId) {
        mGetTaskDataInteractor.getChecklistData(orgId, checklistId, this);
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
    public void changeTaskStatus(int taskId, String taskStatus) {
        mGetTaskDataInteractor.completeTask(taskId, taskStatus, this);
    }

    @Override
    public void onFinishedGetTasks(ArrayList<Task> taskArrayList) {
        mTemplateView.setDataToTaskRecyclerView(taskArrayList);
    }

    @Override
    public void onFinishedChangeTaskStatus() {
        mTaskView.finishedChangeTaskStatus();
    }

    @Override
    public void onFinishedGetChecklistData(Checklist checklist) {
        mTaskView.finishGetChecklist(checklist);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
