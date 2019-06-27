package com.example.workflow_s.ui.taskdetail;

import com.example.workflow_s.model.ContentDetail;

import java.util.ArrayList;

public interface TaskDetailContract {

    //view
    interface  TaskDetailView {
        void setDataToView(ArrayList<ContentDetail> datasource);
    }

    //presenter
    interface TaskDetailPresenter {
        void onDestroy();
        void loadDetails(int taskId);
    }

    //model
    interface GetTaskDetailDataInteractor {
        interface OnFinishedGetTaskDetailListener {
            void onFinishedGetTaskDetail (ArrayList<ContentDetail> taskDetailArrayList);
            void onFailure(Throwable t);
        }

        void getContentDetail(int taskId, OnFinishedGetTaskDetailListener onFinishedListener);
    }
}
