package com.example.workflow_s.ui.home;

import com.example.workflow_s.model.Checklist;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-15
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public interface HomeContract {
    interface HomePresenter {
        void onDestroy();
        void loadDataFromServer();
    }

    interface HomeView {
        void setDataToRecyclerView(ArrayList<Checklist> datasource);
    }

    interface GetHomeDataInteractor {

        interface OnFinishedListener {
            void onFinished(ArrayList<Checklist> checklistArrayList);
            void onFailure(Throwable t);
        }

        void getNoticeArrayList(OnFinishedListener onFinishedListener);
    }
}
