package com.example.casper.userregistration;

/**
 * Created by CASPER on 5/25/2017.
 */

public class RestaurantItems {
    String name;
    String price;
    String restaurant_id;
    String category;
    String img;
    public RestaurantItems() {
    }

    public RestaurantItems(String name, String price, String restaurant_id, String category, String img) {
        this.name = name;
        this.price = price;
        this.restaurant_id = restaurant_id;
        this.category = category;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getRestaurantId() {
        return restaurant_id;
    }

    public String getCategory() {
        return category;
    }

    public String getImg() {
        return img;
    }
}
