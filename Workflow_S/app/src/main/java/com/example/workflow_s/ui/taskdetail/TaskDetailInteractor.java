package com.example.workflow_s.ui.taskdetail;


import com.example.workflow_s.model.ContentDetail;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.ArrayList;
import java.util.List;

public class TaskDetailInteractor implements TaskDetailContract.GetTaskDetailDataInteractor{

    @Override
    public void saveDetail(List<ContentDetail> list, final OnFinishedSaveContentListener saveContentListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.saveTaskContentDetail(list);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                saveContentListener.onFinishedSave();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                saveContentListener.onFailure(t);
            }
        });
    }

    @Override
    public void getTaskById(int taskId, final OnFinishedGetTaskListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Task> call = service.getTaskById(taskId);
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                listener.onFinishedGetTask(response.body());
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void getContentDetail(int taskId, final OnFinishedGetTaskDetailListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<ContentDetail>> call = service.getContentDetail(taskId);

        call.enqueue(new Callback<List<ContentDetail>>() {
            @Override
            public void onResponse(Call<List<ContentDetail>> call, Response<List<ContentDetail>> response) {
                onFinishedListener.onFinishedGetTaskDetail((ArrayList<ContentDetail>) response.body());
            }

            @Override
            public void onFailure(Call<List<ContentDetail>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
