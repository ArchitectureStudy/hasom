package com.hasom.mvc.CreateIssue.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leejunho on 2017. 1. 23..
 */

public class LabelDTO {

    private int id;
    private String url;
    private String name;
    private String color;

    @SerializedName("default")
    private boolean checkDefault;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isCheckDefault() {
        return checkDefault;
    }

    public void setCheckDefault(boolean checkDefault) {
        this.checkDefault = checkDefault;
    }
}
