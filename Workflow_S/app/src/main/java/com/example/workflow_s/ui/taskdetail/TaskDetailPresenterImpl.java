package com.example.workflow_s.ui.taskdetail;

import android.util.Log;

import com.example.workflow_s.model.ContentDetail;
import com.example.workflow_s.model.Task;

import java.util.ArrayList;

public class TaskDetailPresenterImpl implements TaskDetailContract.TaskDetailPresenter,
                TaskDetailContract.GetTaskDetailDataInteractor.OnFinishedGetTaskDetailListener{

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
    public void onFinishedGetTaskDetail(ArrayList<ContentDetail> taskDetailArrayList) {
        mTaskDetailView.setDataToView(taskDetailArrayList);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
