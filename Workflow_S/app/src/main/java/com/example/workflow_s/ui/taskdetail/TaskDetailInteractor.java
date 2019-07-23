package com.example.workflow_s.ui.taskdetail;


import com.example.workflow_s.model.Comment;
import com.example.workflow_s.model.ContentDetail;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import java.util.ArrayList;
import java.util.List;

public class TaskDetailInteractor implements TaskDetailContract.GetTaskDetailDataInteractor{


    @Override
    public void getAllComments(int taskId, final OnFinishedLoadCommentListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Comment>> call = service.getAllComments(taskId);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                listener.onFinishedGetComments(response.body());
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void getTaskMember(int taskId, final OnFinishedGetTaskMemberListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<TaskMember>> call = service.getAllTaskMember(taskId);
        call.enqueue(new Callback<List<TaskMember>>() {
            @Override
            public void onResponse(Call<List<TaskMember>> call, Response<List<TaskMember>> response) {
                listener.onFinishedGetTaskMembers(response.body());
            }

            @Override
            public void onFailure(Call<List<TaskMember>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void uploadImage(int contentId, final int orderContent, MultipartBody.Part photo, final OnFinishedUploadImageListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.uploadImage(contentId, orderContent, photo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onFinishedUploadImage(orderContent);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
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
