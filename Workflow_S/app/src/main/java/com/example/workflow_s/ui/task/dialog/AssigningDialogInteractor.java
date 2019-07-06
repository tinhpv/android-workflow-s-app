package com.example.workflow_s.ui.task.dialog;

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

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-06
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class AssigningDialogInteractor implements AssigningDialogContract.GetDataAssignInteractor {
    @Override
    public void getAllMember(int orgId, final OnFinishedGetMembersListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<User>> call = service.getOrganizationMember(orgId);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                onFinishedListener.onFinishedGetMembers((ArrayList<User>) response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }

    @Override
    public void assignTaskMember(TaskMember member, final OnFinishedAssignMemberListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.assignMember(member);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onFinishedAssigning();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void unassignTaskMember(int memberId, final OnFinishedUnassignMemberListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = service.unassignMember(memberId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onFinishedUnassigning();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    @Override
    public void getAllTaskMembers(int taskId, final OnFinishedGetTaskMembersListener listener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<TaskMember>> call = service.getAllTaskMember(taskId);
        call.enqueue(new Callback<List<TaskMember>>() {
            @Override
            public void onResponse(Call<List<TaskMember>> call, Response<List<TaskMember>> response) {
                listener.onFinishedGetTaskMembers((ArrayList<TaskMember>) response.body());
            }

            @Override
            public void onFailure(Call<List<TaskMember>> call, Throwable t) {
                listener.onFailure(t);
            }
        });

    }
}
