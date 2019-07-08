package com.example.workflow_s.ui.task;

import android.util.Log;

import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.Template;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;
import com.example.workflow_s.ui.template.TemplateContract;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskInteractor implements TaskContract.GetTaskDataInteractor {
    @Override
    public void getAllTasks(int checklistId, final OnFinishedGetTasksListener onFinishedLIstener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Task>> call = service.getTaskFromChecklist(checklistId);

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                onFinishedLIstener.onFinishedGetTasks((ArrayList<Task>) response.body());
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                onFinishedLIstener.onFailure(t);
            }
        });
    }

}
