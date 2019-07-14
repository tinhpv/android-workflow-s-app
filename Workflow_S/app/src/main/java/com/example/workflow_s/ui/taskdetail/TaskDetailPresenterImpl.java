package com.example.workflow_s.ui.taskdetail;

import android.util.Log;

import com.example.workflow_s.model.ContentDetail;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.ui.taskdetail.dialog.time_setting.TimeSettingContract;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailPresenterImpl implements TaskDetailContract.TaskDetailPresenter,
            TaskDetailContract.GetTaskDetailDataInteractor.OnFinishedGetTaskDetailListener,
            TaskDetailContract.GetTaskDetailDataInteractor.OnFinishedSaveContentListener,
            TaskDetailContract.GetTaskDetailDataInteractor.OnFinishedGetTaskListener {

    private static final String TAG = "TASK_DETAIL_PRESENTER";
    private TaskDetailContract.TaskDetailView mTaskDetailView;
    private TaskDetailContract.GetTaskDetailDataInteractor mGetTaskDetailDataInteractor;

    public TaskDetailPresenterImpl(TaskDetailContract.TaskDetailView mTaskDetailView, TaskDetailContract.GetTaskDetailDataInteractor mGetTaskDetailDataInteractor) {
        this.mTaskDetailView = mTaskDetailView;
        this.mGetTaskDetailDataInteractor = mGetTaskDetailDataInteractor;
    }

    @Override
    public void onDestroy() {
        mTaskDetailView = null;
    }

    @Override
    public void loadDetails(int taskId) {
        mGetTaskDetailDataInteractor.getContentDetail(taskId, this);
    }

    @Override
    public void getTask(int taskId) {
        mGetTaskDetailDataInteractor.getTaskById(taskId, this);
    }

    @Override
    public void updateTaskDetail(List<ContentDetail> detailList) {
        mGetTaskDetailDataInteractor.saveDetail(detailList, this);
    }

    @Override
    public void onFinishedGetTaskDetail(ArrayList<ContentDetail> taskDetailArrayList) {
        mTaskDetailView.setDataToView(taskDetailArrayList);
    }

    @Override
    public void onFinishedSave() {
        mTaskDetailView.finishedSaveContent();
    }

    @Override
    public void onFinishedGetTask(Task task) {
        mTaskDetailView.finishedGetTaskDetail(task);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
