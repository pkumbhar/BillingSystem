package com.entity;

import java.util.List;

/**
 * Created by Admin on 24-August-24-2017.
 */

public class SalesBill {
    private String SalesBillId;
    private String firstName;
    private String MiddleName;
    private String lastName;
    private String email;
    private String totalPrice;
    private String totaltax;
    private String totalAmount;
    private String createdBy;
    private String createdOn;
    private UserTable userId;
    private String serviceCharge;
    private String isOpen;
    private String recordTime;
    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    private List<SalesBillDetail> salesBillDetailList;

    public List<SalesBillDetail> getSalesBillDetailList() {
        return salesBillDetailList;
    }

    public void setSalesBillDetailList(List<SalesBillDetail> salesBillDetailList) {
        this.salesBillDetailList = salesBillDetailList;
    }

    private List<UserTable> userTableList;


    public String getSalesBillId() {
        return SalesBillId;
    }

    public void setSalesBillId(String salesBillId) {
        SalesBillId = salesBillId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotaltax() {
        return totaltax;
    }

    public void setTotaltax(String totaltax) {
        this.totaltax = totaltax;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public UserTable getUserId() {
        return userId;
    }

    public void setUserId(UserTable userId) {
        this.userId = userId;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public List<UserTable> getUserTableList() {
        return userTableList;
    }

    public void setUserTableList(List<UserTable> userTableList) {
        this.userTableList = userTableList;
    }
}
