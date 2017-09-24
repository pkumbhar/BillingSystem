package com.entity;

/**
 * Created by Admin on 24-August-24-2017.
 */

public class UserTable {
    private String userTableId;
    private String userTableNumber;
    private boolean isActive;
    private boolean isAc;
    private boolean isNonAc;
    private boolean isGarden;
    private String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean isAc() {
        return isAc;
    }

    public void setAc(boolean ac) {
        isAc = ac;
    }

    public boolean isNonAc() {
        return isNonAc;
    }

    public void setNonAc(boolean nonAc) {
        isNonAc = nonAc;
    }

    public boolean isGarden() {
        return isGarden;
    }

    public void setGarden(boolean garden) {
        isGarden = garden;
    }

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
