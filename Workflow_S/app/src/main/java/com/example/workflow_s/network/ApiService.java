package com.example.workflow_s.network;

import com.example.workflow_s.model.Checklist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-12
 * Copyright © 2019 TinhPV. All rights reserved
 **/

public interface ApiService {
    @GET("Checklists")
    Call<List<Checklist>> getAllChecklists();


}
