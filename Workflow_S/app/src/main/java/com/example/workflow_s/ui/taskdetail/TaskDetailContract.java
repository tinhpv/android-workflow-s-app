package com.example.workflow_s.ui.taskdetail;

import com.example.workflow_s.model.ContentDetail;

import java.util.ArrayList;
import java.util.List;

public interface  TaskDetailContract {

    //view
    interface  TaskDetailView {
        void setDataToView(ArrayList<ContentDetail> datasource);
        void finishedSaveContent();
    }

    //presenter
    interface TaskDetailPresenter {
        void onDestroy();
        void loadDetails(int taskId);
        void updateTaskDetail(List<ContentDetail> detailList);
    }

    //model
    interface GetTaskDetailDataInteractor {
        interface OnFinishedGetTaskDetailListener {
            void onFinishedGetTaskDetail (ArrayList<ContentDetail> taskDetailArrayList);
            void onFailure(Throwable t);
        }

        interface OnFinishedSaveContentListener {
            void onFinishedSave();
            void onFailure(Throwable t);
        }

        void getContentDetail(int taskId, OnFinishedGetTaskDetailListener onFinishedListener);
        void saveDetail(List<ContentDetail> list, OnFinishedSaveContentListener saveContentListener);
    }
}
