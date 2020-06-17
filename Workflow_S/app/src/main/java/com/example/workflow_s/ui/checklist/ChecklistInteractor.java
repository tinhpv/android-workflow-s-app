package com.example.workflow_s.ui.checklist;



import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.Template;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistInteractor implements ChecklistContract.GetChecklistsDataInteractor {

    @Override
    public void getAllTemplates(String orgId, final OnFinishedGetTemplateDataListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Template>> call = service.getAllCreatedTemplates(orgId);
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

    @Override
    public void deleteChecklist(int checklistId, String userId, final OnFinishedDeleteChecklistListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.deleteChecklist(checklistId, userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onFinishedDeleteChecklist();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }

    @Override
    public void getAllChecklist(String organizationId,final OnFinishedGetChecklistListener onFinishedGetChecklistListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Checklist>> call = service.getAllRunningChecklists(organizationId);

        call.enqueue(new Callback<List<Checklist>>() {
            @Override
            public void onResponse(Call<List<Checklist>> call, Response<List<Checklist>> response) {
                onFinishedGetChecklistListener.onFinishedGetChecklist((ArrayList<Checklist>) response.body());
            }

            @Override
            public void onFailure(Call<List<Checklist>> call, Throwable t) {
                onFinishedGetChecklistListener.onFailure(t);
            }
        });
    }

    @Override
    public void getFirstTask(int checklistId, final Checklist parentChecklistOfThisTask, final OnFinishedGetFirstTaskFormChecklistListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Task> call = service.getFirstTaskFromChecklist(checklistId);

        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                onFinishedListener.onFinishedGetFirstTask(response.body(), parentChecklistOfThisTask);
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void setNameOfChecklist(int checklistId, String name, final OnFinishedSetNameOfChecklist onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.setNameOfChecklist(checklistId, name);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                onFinishedListener.onFinishedSetNameChecklist();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }


}
