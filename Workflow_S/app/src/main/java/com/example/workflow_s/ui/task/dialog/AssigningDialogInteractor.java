package com.example.workflow_s.ui.task.dialog;

import com.example.workflow_s.model.User;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.ArrayList;
import java.util.List;

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
}
