package com.example.workflow_s.ui.activity;



import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityInteractor implements ActivityContract.GetActivitiesDataInteractor {


    @Override
    public void getFirstTask(int checklistId, final Checklist checklist, final OnFinishedGetFirstTaskListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Task> call = service.getFirstTaskFromChecklist(checklistId);

        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                onFinishedListener.onFinishedGetFirstTask(response.body(), checklist);
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getAllChecklist(String organizationId, final OnFinishedGetChecklistListener onFinishedGetChecklistListener) {
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
}
