package com.entity;

/**
 * Created by Admin on 24-August-24-2017.
 */

public class UserTable {
    private String userTableId;
    private String userTableNumber;
    private boolean isActive;

    public String getUserTableId() {
        return userTableId;
    }

    public void setUserTableId(String userTableId) {
        this.userTableId = userTableId;
    }

    public String getUserTableNumber() {
        return userTableNumber;
    }

    public void setUserTableNumber(String userTableNumber) {
        this.userTableNumber = userTableNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
