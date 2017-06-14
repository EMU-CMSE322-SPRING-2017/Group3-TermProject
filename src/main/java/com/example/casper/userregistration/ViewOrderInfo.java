package com.example.casper.userregistration;

/**
 * Created by CASPER on 5/24/2017.
 */

public class ViewOrderInfo {
    String name;
    String address;
    String food;
    String price;
    String phone;
    String customerId;
    public ViewOrderInfo() {
    }

    public ViewOrderInfo(String name, String address, String food, String price, String phone, String customerId) {
        this.name = name;
        this.address = address;
        this.food = food;
        this.price = price;
        this.phone = phone;
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getFood() {
        return food;
    }

    public String getPrice() {
        return price;
    }

    public String getPhone() {
        return phone;
    }

    public String getCustomerId() {
        return customerId;
    }
}
