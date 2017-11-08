package com.entity;

/**
 * Created by Admin on 08-November-8-2017.
 */

public class CorporateCustomer {
    private String corporateCustomerId="corporateCustomerId";
    private String corporateCustomerName="corporateCustomerName";
    private String address="address";
    private String contactNumber="contactNumber";
    private String consernPerson="consernPerson";

    public String getCorporateCustomerId() {
        return corporateCustomerId;
    }

    public void setCorporateCustomerId(String corporateCustomerId) {
        this.corporateCustomerId = corporateCustomerId;
    }

    public String getCorporateCustomerName() {
        return corporateCustomerName;
    }

    public void setCorporateCustomerName(String corporateCustomerName) {
        this.corporateCustomerName = corporateCustomerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getConsernPerson() {
        return consernPerson;
    }

    public void setConsernPerson(String consernPerson) {
        this.consernPerson = consernPerson;
    }
}
