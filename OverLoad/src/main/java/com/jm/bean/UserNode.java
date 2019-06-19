package com.jm.bean;

public class UserNode {

    private String empId;

    private String nodeCode;

    public UserNode() {
    }

    public UserNode(String empId, String nodeCode) {
        this.empId = empId;
        this.nodeCode = nodeCode;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getNodeCode() {
        return nodeCode;
    }

    public void setNodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }
}
