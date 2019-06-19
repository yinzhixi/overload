package com.jm.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Permission implements Serializable{

    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private final String domain = "admin";
    private boolean isAssign = false;//
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDomain() {
        return domain;
    }
    
    @JsonProperty("LAY_CHECKED")
    public boolean isAssign() {
        return isAssign;
    }
    public void setAssign(boolean isAssign) {
        this.isAssign = isAssign;
    }
    
}
