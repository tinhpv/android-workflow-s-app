package com.example.workflow_s.ui.task;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.TaskMember;
import com.example.workflow_s.model.User;
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
    public void getMemberOrganization(int orgId, final OnFinishedGetMembers onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<User>> call = service.getOrganizationMember(orgId);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                onFinishedListener.onFinishedGetMembers(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }

    @Override
    public void renameTask(int taskId, String taskName, final OnFinishedRenameTaskListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.renameTask(taskId, taskName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onFinishedRenameTask();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void getUserById(String userId, final OnFinishedGetInforUserListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<User> call = service.getUserById(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                listener.onFinishedGetUser(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void getTaskMember(final int taskId, final boolean isSelected, final OnFinishedGetTaskMemberListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<TaskMember>> call = service.getAllTaskMember(taskId);
        call.enqueue(new Callback<List<TaskMember>>() {
            @Override
            public void onResponse(Call<List<TaskMember>> call, Response<List<TaskMember>> response) {
                listener.onFinishedGetTaskMember(response.body(), isSelected, taskId);
            }

            @Override
            public void onFailure(Call<List<TaskMember>> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }

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
    public void changeTaskStatus(String userId, int taskId, final String taskStatus, final OnFinishedChangeTaskStatusListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.changeTaskStatus(userId, taskId, taskStatus);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onFinishedChangeTaskStatus(taskStatus);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void completeChecklist(int checklistId, final String taskStatus, final OnFinishedChangeChecklistStatusListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.completeChecklist(checklistId, taskStatus);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onFinishedChangeChecklistStatus(taskStatus);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

}
