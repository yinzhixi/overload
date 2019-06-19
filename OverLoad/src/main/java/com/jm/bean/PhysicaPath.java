package com.jm.bean;

import java.util.Date;

public class PhysicaPath {
    private Integer id;

    private String pathname;

    private String pathCode;

    private String path;

    private String status;

    private String createtime;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public PhysicaPath() {
    }

    public PhysicaPath(Integer id, String pathname, String pathCode, String path, String status, String createtime) {
        this.id = id;
        this.pathname = pathname;
        this.pathCode = pathCode;
        this.path = path;
        this.status = status;
        this.createtime = createtime;
    }

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPathname() {
        return pathname;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname == null ? null : pathname.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}