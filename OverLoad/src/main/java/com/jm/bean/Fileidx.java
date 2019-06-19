package com.jm.bean;

import java.util.Date;

public class Fileidx {
    private String id;

    private String filetype;

    private String relcode;

    private String filename;

    private String physicalpath;

    private String filepath;

    private Date createtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype == null ? null : filetype.trim();
    }

    public String getRelcode() {
        return relcode;
    }

    public void setRelcode(String relcode) {
        this.relcode = relcode == null ? null : relcode.trim();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename == null ? null : filename.trim();
    }

    public String getPhysicalpath() {
        return physicalpath;
    }

    public void setPhysicalpath(String physicalpath) {
        this.physicalpath = physicalpath == null ? null : physicalpath.trim();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath == null ? null : filepath.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}