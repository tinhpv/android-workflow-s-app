package com.example.workflow_s.ui.taskdetail;

import android.util.Log;

import com.example.workflow_s.model.ContentDetail;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.ui.taskdetail.dialog.time_setting.TimeSettingContract;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;

public class TaskDetailPresenterImpl implements TaskDetailContract.TaskDetailPresenter,
            TaskDetailContract.GetTaskDetailDataInteractor.OnFinishedGetTaskDetailListener,
            TaskDetailContract.GetTaskDetailDataInteractor.OnFinishedSaveContentListener,
            TaskDetailContract.GetTaskDetailDataInteractor.OnFinishedGetTaskListener,
            TaskDetailContract.GetTaskDetailDataInteractor.OnFinishedChangeTaskStatusListener,
            TaskDetailContract.GetTaskDetailDataInteractor.OnFinishedUploadImageListener,
            TaskDetailContract.GetTaskDetailDataInteractor.OnFinishedGetTaskMemberListener {

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
    public void getTaskMember(int taskId) {
        mGetTaskDetailDataInteractor.getTaskMember(taskId, this);
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
    public void completeTask(int taskId, String taskStatus) {
        mGetTaskDetailDataInteractor.completeTask(taskId, taskStatus, this);
    }

    @Override
    public void uploadImage(int contentId, int orderContent, MultipartBody.Part photo) {
        mGetTaskDetailDataInteractor.uploadImage(contentId, orderContent, photo,this);
    }

    @Override
    public void onFinishedChangeTaskStatus() {
        mTaskDetailView.finishedCompleteTask();
    }

    @Override
    public void onFinishedUploadImage(int orderContent) {
        mTaskDetailView.finishedUploadImage(orderContent);
    }

    @Override
    public void onFinishedGetTaskMembers(List<TaskMember> taskMemberList) {
        mTaskDetailView.finishGetTaskMember(taskMemberList);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(TAG, "onFailure: " + t.getMessage());
    }
}
