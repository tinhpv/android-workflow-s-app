package com.example.workflow_s.ui.taskdetail;


import com.example.workflow_s.model.ContentDetail;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.ArrayList;
import java.util.List;

public class TaskDetailInteractor implements TaskDetailContract.GetTaskDetailDataInteractor{
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

    //post 3 model thuc thi post something
//    @Override
//    public void postSomething(Task task, final OnFinishedPostDataListener onFinishedPostDataListener) {
//        ApiService service = ApiClient.getClient().create(ApiService.class);
//        Call<Task> call = null;
//
//        call.enqueue(new Callback<Task>() {
//            @Override
//            public void onResponse(Call<Task> call, Response<Task> response) {
//                //se responese khi thuc thi thanh cong
//                //return message
//            }
//
//            @Override
//            public void onFailure(Call<Task> call, Throwable t) {
//                //failure
//            }
//        });
//    }
}
