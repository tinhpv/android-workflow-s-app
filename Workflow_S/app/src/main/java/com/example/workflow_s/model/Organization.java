package com.example.workflow_s.model;

import com.google.gson.annotations.SerializedName;

/**
 * Workflow_S
 * Created by TinhPV on 2019-06-20
 * Copyright © 2019 TinhPV. All rights reserved
 **/


public class Organization {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public Organization(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
