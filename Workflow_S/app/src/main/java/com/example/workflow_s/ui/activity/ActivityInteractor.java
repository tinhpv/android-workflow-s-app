package com.example.workflow_s.ui.activity;



import com.example.workflow_s.model.Task;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityInteractor implements ActivityContract.GetActivitiesDataInteractor {

    @Override
    public void getAllDueTasks(String organizationId, String userId, final OnFinishedGetDueTasksListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Task>> call = service.getAllDueTasks(organizationId, userId);

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                onFinishedListener.onFinishedGetDueTasks(response.body());
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getUpcomingTasks(String organizationId, String userId, final OnFinishedGetUpcomingTasksListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Task>> call = service.getUpcomingTasks(organizationId, userId);

        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                onFinishedListener.onFinishedGetUpcomingTasks(response.body());
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
