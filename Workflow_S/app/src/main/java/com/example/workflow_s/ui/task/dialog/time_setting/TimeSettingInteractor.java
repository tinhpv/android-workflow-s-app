package com.example.workflow_s.ui.task.dialog.time_setting;

import com.example.workflow_s.model.Checklist;
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
    public void getChecklistInfo(int checklistId, final OnFinishedGetChecklistInfoListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Checklist> call = service.getChecklistById(checklistId);
        call.enqueue(new Callback<Checklist>() {
            @Override
            public void onResponse(Call<Checklist> call, Response<Checklist> response) {
                listener.onFinishedGetChecklistInfo(response.body());
            }

            @Override
            public void onFailure(Call<Checklist> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void setTime(int checklistId, String dateTime, final OnFinishedSetTimeListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.setChecklistDueTime(checklistId, dateTime);
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
}
