package com.example.workflow_s.ui.checklist;



import android.util.Log;

import com.example.workflow_s.model.Checklist;
import com.example.workflow_s.network.ApiClient;
import com.example.workflow_s.network.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-30
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistInteractor implements ChecklistContract.GetChecklistsDataInteractor {

    @Override
    public void getAllChecklist(String organizationId,final OnFinishedGetChecklistListener onFinishedGetChecklistListener) {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<List<Checklist>> call = service.getAllRunningChecklists(organizationId);

        call.enqueue(new Callback<List<Checklist>>() {
            @Override
            public void onResponse(Call<List<Checklist>> call, Response<List<Checklist>> response) {
                onFinishedGetChecklistListener.onFinishedGetChecklist((ArrayList<Checklist>) response.body());
                Log.i("Success", "success");
            }

            @Override
            public void onFailure(Call<List<Checklist>> call, Throwable t) {
                onFinishedGetChecklistListener.onFailure(t);
                Log.i("Fail", "fail");
            }
        });
    }
}
