package com.entity;

/**
 * Created by Admin on 01-November-1-2017.
 */

public class FinancialYear {
    private String financialYearId;
    private String yearName;
    private String statrtDate;
    private String endDate;
    private String isActive;
    private String recordTime;
    private String branchId;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(String financialYearId) {
        this.financialYearId = financialYearId;
    }

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }

    public String getStatrtDate() {
        return statrtDate;
    }

    public void setStatrtDate(String statrtDate) {
        this.statrtDate = statrtDate;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
