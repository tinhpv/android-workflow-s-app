package com.example.workflow_s.ui.organization;

import com.example.workflow_s.model.Organization;
import com.example.workflow_s.model.Task;
import com.example.workflow_s.model.User;
import com.example.workflow_s.model.UserOrganization;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-27
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class OrganizationInteractor implements OrganizationContract.GetOrganizationDataContract {

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
    public void getOrganization(String userId, final OnFinishedGetOrganizatonListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<Organization> call = service.getCurrentOrganization(userId);

        call.enqueue(new Callback<Organization>() {
            @Override
            public void onResponse(Call<Organization> call, Response<Organization> response) {
                onFinishedListener.onFinishedGetOrg((Organization) response.body());
            }

            @Override
            public void onFailure(Call<Organization> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });


    }

    @Override
    public void getListUserOrganization(String userId, final OnFinishedGetListUserOrganizationListener onFinishedListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<UserOrganization>> call = service.getListUserOrganization(userId);

        call.enqueue(new Callback<List<UserOrganization>>() {
            @Override
            public void onResponse(Call<List<UserOrganization>> call, Response<List<UserOrganization>> response) {
                onFinishedListener.onFinishedGetListUserOrg((ArrayList<UserOrganization>) response.body());
            }

            @Override
            public void onFailure(Call<List<UserOrganization>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
