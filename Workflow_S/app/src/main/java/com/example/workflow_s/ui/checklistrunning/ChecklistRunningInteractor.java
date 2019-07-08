package com.example.workflow_s.ui.checklistrunning;

import com.example.workflow_s.model.Template;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-05
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistRunningInteractor implements ChecklistRunningContract.ChecklistRunningData {

    @Override
    public void getTemplateObject(String templateId, String orgId, String userId, final OnFinishedGetTemplateObjectListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Template> call = service.getTemplateObject(orgId, templateId, userId);

        call.enqueue(new Callback<Template>() {
            @Override
            public void onResponse(Call<Template> call, Response<Template> response) {
                onFinishedListener.onFinishedGetTemplate(response.body());
            }

            @Override
            public void onFailure(Call<Template> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void runChecklist(String userId, Template template, final OnFinishedRunChecklistListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.runChecklist(userId, template);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onFinishedRunChecklist();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
