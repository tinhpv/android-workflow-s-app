package com.example.workflow_s.model;

import com.google.gson.annotations.SerializedName;

/**
 * Workflow_S
 * Created by TinhPV on 2019-07-10
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class ChecklistMember {
    @SerializedName("id")
    private int id;

    @SerializedName("checklistId")
    private int checklistId;

    @SerializedName("userId")
    private String userId;

    public ChecklistMember(int id, int checklistId, String userId) {
        this.id = id;
        this.checklistId = checklistId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(int checklistId) {
        this.checklistId = checklistId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
