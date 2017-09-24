package com.entity;

/**
 * Created by Admin on 22-September-22-2017.
 */

public class Employee {
    private String employeeId;
    private String employeeName;
    private String mobile;
    private String userName;
    private String password;
    private String applicationRoleId;
    private String branchId;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApplicationRoleId() {
        return applicationRoleId;
    }

    public void setApplicationRoleId(String applicationRoleId) {
        this.applicationRoleId = applicationRoleId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
