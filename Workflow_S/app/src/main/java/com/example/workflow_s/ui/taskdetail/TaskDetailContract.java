package com.example.workflow_s.ui.taskdetail;

import com.example.workflow_s.model.ContentDetail;
import com.example.workflow_s.model.Task;

import java.util.ArrayList;

public interface TaskDetailContract {

    //view
    interface  TaskDetailView {
        void setDataToView(ArrayList<ContentDetail> datasource);
        //post
        //se tra data tu model ve
       // void baonguoidung(String abc);
    }

    //presenter
    interface TaskDetailPresenter {
        void onDestroy();
        void loadDetails(int taskId);
        //them ham post
        //se lay data tu view qua
       // void postSomething(Task t);
    }

    //model
    interface GetTaskDetailDataInteractor {
        interface OnFinishedGetTaskDetailListener {
            void onFinishedGetTaskDetail (ArrayList<ContentDetail> taskDetailArrayList);
            void onFailure(Throwable t);
        }

      //  interface OnFinishedPostDataListener {
        //    void onFinishedPostTaskDetail ();
         //   void onFailure(Throwable t);
       // }

        void getContentDetail(int taskId, OnFinishedGetTaskDetailListener onFinishedListener);
        //post
       // void postSomething(Task task, OnFinishedPostDataListener onFinishedPostDataListener);
    }
}
