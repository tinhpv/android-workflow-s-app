package com.example.workflow_s.ui.task.dialog.time_setting;

import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.User;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-07
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TimeSettingInteractor implements TimeSettingContract.TimeSettingDataContract {

    @Override
    public void setTime(int taskId, String dateTime, final OnFinishedSetTimeListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.setDueTime(taskId, dateTime);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                onFinishedListener.onFinishedSetTime();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getTask(int checklistId, final OnFinishedGetTaskListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Task> call = service.getFirstTaskFromChecklist(checklistId);

        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                listener.onFinishedGetTask(response.body());
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
