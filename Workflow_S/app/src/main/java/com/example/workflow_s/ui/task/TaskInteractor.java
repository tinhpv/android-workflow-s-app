package com.example.workflow_s.ui.task;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskInteractor implements TaskContract.GetTaskDataInteractor {

    @Override
    public void getChecklistData(int orgId, int checklistId, final OnFinishedLoadChecklistDataListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Checklist> call = service.getChecklistById(orgId, checklistId);
        call.enqueue(new Callback<Checklist>() {
            @Override
            public void onResponse(Call<Checklist> call, Response<Checklist> response) {
                listener.onFinishedGetChecklistData(response.body());
            }

            @Override
            public void onFailure(Call<Checklist> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

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

    @Override
    public void completeTask(int taskId, String taskStatus, final OnFinishedChangeTaskStatusListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.completeTask(taskId, taskStatus);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onFinishedChangeTaskStatus();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void completeChecklist(int checklistId, String taskStatus, final OnFinishedChangeChecklistStatusListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.completeTask(checklistId, taskStatus);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onFinishedChangeChecklistStatus();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

}
