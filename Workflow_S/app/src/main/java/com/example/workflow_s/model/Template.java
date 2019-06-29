package com.example.workflow_s.model;

import com.google.gson.annotations.SerializedName;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-28
 * Copyright © 2019 TinhPV. All rights reserved
 **/

public class Template {

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
    private Integer templateStatus;
    @SerializedName("organizationId")
    private Integer organizationId;
    @SerializedName("category")
    private String category;

    public Template(Integer id, String userId, String name, String description, String timeCreated, Integer templateStatus, Integer organizationId, String category) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.timeCreated = timeCreated;
        this.templateStatus = templateStatus;
        this.organizationId = organizationId;
        this.category = category;
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

    public Integer getTemplateStatus() {
        return templateStatus;
    }

    public void setTemplateStatus(Integer templateStatus) {
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
}
