package com.example.workflow_s.model;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-07
 * Copyright © 2019 TinhPV. All rights reserved
 **/

import com.google.gson.annotations.SerializedName;

public class Checklist {

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
    @SerializedName("templateId")
    private Integer templateId;

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public Integer getTemplateStatus() {
        return templateStatus;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public Integer getTemplateId() {
        return templateId;
    }
}
