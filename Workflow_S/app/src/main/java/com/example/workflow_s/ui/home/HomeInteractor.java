package com.example.workflow_s.ui.home;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-15
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class HomeInteractor implements HomeContract.GetHomeDataInteractor {


    // FIXME - HARDCODE FOR TESTING
    @Override
    public void getAllRunningChecklists(String userId, String orgId, final OnFinishedGetRunningChecklistsListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
//        Call<List<Checklist>> call = service.getAllRunningChecklists(orgId, userId);
        Call<List<Checklist>> call = service.getAllRunningChecklists("1", "2372592022969346");

        call.enqueue(new Callback<List<Checklist>>() {
            @Override
            public void onResponse(Call<List<Checklist>> call, Response<List<Checklist>> response) {
                onFinishedListener.onFinishedGetChecklists((ArrayList<Checklist>) response.body());
            }

            @Override
            public void onFailure(Call<List<Checklist>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getAllDueTasks(String userId, final OnFinishedGetDueTasksListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Task>> call = service.getAllDueTasks(userId);

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                onFinishedListener.onFinishedGetTasks((ArrayList<Task>) response.body());
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
