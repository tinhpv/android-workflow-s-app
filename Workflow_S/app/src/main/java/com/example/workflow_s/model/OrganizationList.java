package com.example.workflow_s.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-20
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class OrganizationList {

    @SerializedName("organization_list")
    private ArrayList<Organization> organizationList;

    public ArrayList<Organization> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(ArrayList<Organization> organizationList) {
        this.organizationList = organizationList;
    }
}
