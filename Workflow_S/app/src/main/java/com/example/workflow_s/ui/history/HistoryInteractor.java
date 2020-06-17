package com.example.workflow_s.ui.history;

import com.example.workflow_s.model.Notification;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-25
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class HistoryInteractor implements HistoryContract.HistoryDataInteractor {

    @Override
    public void getAllComments(int taskId, final OnFinishedGetActitivities onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Notification>> call = service.getAllNotifs(taskId);
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                onFinishedListener.onFinishedGetActs(response.body());
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });


    }
}
