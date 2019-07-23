package com.example.workflow_s.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TaskMember implements Serializable  {

    @SerializedName("id")
    private Integer id;

    @SerializedName("taskId")
    private Integer taskId;

    @SerializedName("userId")
    private String userId;

    public TaskMember(Integer id, Integer taskId, String userId) {
        this.id = id;
        this.taskId = taskId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
