package com.example.casper.userregistration;

/**
 * Created by CASPER on 4/26/2017.
 */

public class UserInfo {
    String name;
    String address;
    String phone;
    String usergroup;
    public UserInfo() {
    }

    public UserInfo(String name, String address, String phone, String usergroup) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.usergroup = usergroup;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsergroup() {
        return usergroup;
    }
}

