package com.example.workflow_s.ui.task;

import com.example.workflow_s.model.Task;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.ArrayList;
import java.util.List;

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
