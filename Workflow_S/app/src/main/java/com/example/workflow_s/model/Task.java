package com.example.workflow_s.model;

import com.google.gson.annotations.SerializedName;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-22
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class Task {

    @SerializedName("id")
    private Integer id;

    @SerializedName("checklistId")
    private Integer checklistId;

    @SerializedName("name")
    private String name;

    @SerializedName("dueTime")
    private String dueTime;

    @SerializedName("priority")
    private Integer priority;

    @SerializedName("taskStatus")
    private String taskStatus;

    public Task(Integer id, Integer checklistId, String name, String dueTime, Integer priority, String taskStatus) {
        this.id = id;
        this.checklistId = checklistId;
        this.name = name;
        this.dueTime = dueTime;
        this.priority = priority;
        this.taskStatus = taskStatus;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
