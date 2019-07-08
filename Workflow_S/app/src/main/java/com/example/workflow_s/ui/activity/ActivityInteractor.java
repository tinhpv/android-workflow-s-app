package com.example.workflow_s.ui.activity;


import android.util.Log;

import com.example.workflow_s.model.Task;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityInteractor implements ActivityContract.GetActivitiesDataInteractor {

    // FIXME Waiting for data
    @Override
    public void getAllTodayTasks(String userId, OnFinishedGetTodayTaskListener onFinishedGetTodayTaskListener) {

    }

    @Override
    public void getAllUpcomingTasks(String orgranizationId, String userId, final OnFinishedGetUpcomingTaskListener onFinishedGetUpcomingTaskListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Task>> call = service.getUpcomingTasks(orgranizationId,userId);

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                onFinishedGetUpcomingTaskListener.onFinishedGetUpcomingTasks((ArrayList<Task>) response.body());
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                onFinishedGetUpcomingTaskListener.onFailure(t);
            }
        });
    }
}
