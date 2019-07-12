package com.example.workflow_s.model;

import com.google.gson.annotations.SerializedName;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-10
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistMember {

    @SerializedName("id")
    private Integer id;

    @SerializedName("checklistId")
    private Integer checklistId;

    @SerializedName("userId")
    private String userId;

    public ChecklistMember(Integer id, Integer checklistId, String userId) {
        this.id = id;
        this.checklistId = checklistId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
