package com.example.workflow_s.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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

    @SerializedName("contentDetail")
    private List<ContentDetail> contentDetails;

    @SerializedName("userId")
    private List<User> memberList;

    @SerializedName("taskMember")
    private List<TaskMember> taskMemberList;

    public Task(Integer id, Integer checklistId, String name, String dueTime, Integer priority, String taskStatus) {
        this.id = id;
        this.checklistId = checklistId;
        this.name = name;
        this.dueTime = dueTime;
        this.priority = priority;
        this.taskStatus = taskStatus;
    }

    public Task(Integer id, Integer checklistId, String name, String dueTime, Integer priority, String taskStatus, List<ContentDetail> contentDetails, List<User> userId) {
        this.id = id;
        this.checklistId = checklistId;
        this.name = name;
        this.dueTime = dueTime;
        this.priority = priority;
        this.taskStatus = taskStatus;
        this.contentDetails = contentDetails;
        this.memberList = userId;
    }

    public List<TaskMember> getTaskMemberList() {
        return taskMemberList;
    }

    public void setTaskMemberList(List<TaskMember> taskMemberList) {
        this.taskMemberList = taskMemberList;
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

    public List<ContentDetail> getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(List<ContentDetail> contentDetails) {
        this.contentDetails = contentDetails;
    }

    public List<User> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<User> memberList) {
        this.memberList = memberList;
    }
}
