package com.example.workflow_s.ui.home;

import android.util.Log;

import com.example.workflow_s.model.Checklist;
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

    @Override
    public void getNoticeArrayList(final OnFinishedListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Checklist>> call = service.getAllChecklists();
        Log.d("URL Called", call.request().url() + "");

        call.enqueue(new Callback<List<Checklist>>() {
            @Override
            public void onResponse(Call<List<Checklist>> call, Response<List<Checklist>> response) {
                onFinishedListener.onFinished((ArrayList<Checklist>) response.body());
            }

            @Override
            public void onFailure(Call<List<Checklist>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
