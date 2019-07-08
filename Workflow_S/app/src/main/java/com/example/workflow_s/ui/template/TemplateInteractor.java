package com.example.workflow_s.ui.template;

import com.example.workflow_s.model.Template;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-27
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class TemplateInteractor implements TemplateContract.GetTemplateDataContract {
    @Override
    public void getAllTemplates(String orgId, String userId, final OnFinishedGetTemplateDataListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Template>> call = service.getAllCreatedTemplates(orgId, userId);
        call.enqueue(new Callback<List<Template>>() {
            @Override
            public void onResponse(Call<List<Template>> call, Response<List<Template>> response) {
                onFinishedListener.onFinishedGetTemplates((ArrayList<Template>) response.body());
            }

            @Override
            public void onFailure(Call<List<Template>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
