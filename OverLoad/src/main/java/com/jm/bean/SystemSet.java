package com.jm.bean;

import java.io.Serializable;

public class SystemSet implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String key;
    private String val;
    private String comment;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getVal() {
        return val;
    }
    public void setVal(String val) {
        this.val = val;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    
}
