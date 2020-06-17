package com.example.workflow_s.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-28
 * Copyright © 2019 TinhPV. All rights reserved
 **/

public class Template implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("userId")
    private String userId;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("timeCreated")
    private String timeCreated;
    @SerializedName("templateStatus")
    private String templateStatus;
    @SerializedName("organizationId")
    private Integer organizationId;
    @SerializedName("category")
    private String category;
    @SerializedName("dueTime")
    private String dueTime;
    @SerializedName("taskItemViewModels")
    private List<Task> taskItemList;


    public Template(Integer id, String userId, String name, String description, String timeCreated, String templateStatus, Integer organizationId, String category) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.timeCreated = timeCreated;
        this.templateStatus = templateStatus;
        this.organizationId = organizationId;
        this.category = category;
    }


    public Template(Integer id, String userId, String name, String description, String timeCreated, String templateStatus, Integer organizationId, String category, List<Task> taskItemViewModels) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.timeCreated = timeCreated;
        this.templateStatus = templateStatus;
        this.organizationId = organizationId;
        this.category = category;
        this.taskItemList = taskItemViewModels;
    }

    public Template(Integer id, String userId, String name, String description, String timeCreated, String templateStatus, Integer organizationId, String category, String dueTime, List<Task> taskItemList) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.timeCreated = timeCreated;
        this.templateStatus = templateStatus;
        this.organizationId = organizationId;
        this.category = category;
        this.dueTime = dueTime;
        this.taskItemList = taskItemList;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getTemplateStatus() {
        return templateStatus;
    }

    public void setTemplateStatus(String templateStatus) {
        this.templateStatus = templateStatus;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Task> getTaskItemList() {
        return taskItemList;
    }

    public void setTaskItemList(List<Task> taskItemList) {
        this.taskItemList = taskItemList;
    }
}
