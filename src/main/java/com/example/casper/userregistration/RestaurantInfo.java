package com.example.casper.userregistration;

/**
 * Created by CASPER on 5/18/2017.
 */

public class RestaurantInfo {
    String address;
    String icon;
    String name;
    String phone;
    String id;
    String open;
    String close;
    String usergroup;
    public RestaurantInfo() {
    }

    public RestaurantInfo(String address, String icon, String name, String phone, String id, String open, String close, String usergroup) {
        this.address = address;
        this.icon = icon;
        this.name = name;
        this.phone = phone;
        this.id = id;
        this.open = open;
        this.close = close;
        this.usergroup = usergroup;
    }

    public String getAddress() {
        return address;
    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public String getOpen() {
        return open;
    }

    public String getClose() {
        return close;
    }

    public String getUsergroup() {
        return usergroup;
    }
}
