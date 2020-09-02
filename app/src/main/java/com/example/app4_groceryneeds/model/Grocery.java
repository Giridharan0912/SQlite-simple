package com.example.app4_groceryneeds.model;

public class Grocery {
    private int item_id;
    private String item_name;
    private int item_quantity;
    private String item_brand;
    private int item_size;
    private String item_date_added;

    public Grocery() {
    }

    public Grocery(int item_id, String item_name, int item_quantity, String item_brand, int item_size, String item_date_added) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_quantity = item_quantity;
        this.item_brand = item_brand;
        this.item_size = item_size;
        this.item_date_added = item_date_added;
    }

    public Grocery(String item_name, int item_quantity, String item_brand, int item_size, String item_date_added) {
        this.item_name = item_name;
        this.item_quantity = item_quantity;
        this.item_brand = item_brand;
        this.item_size = item_size;
        this.item_date_added = item_date_added;
    }

    public String getItem_date_added() {
        return item_date_added;
    }

    public void setItem_date_added(String item_date_added) {
        this.item_date_added = item_date_added;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(int item_quantity) {
        this.item_quantity = item_quantity;
    }

    public String getItem_brand() {
        return item_brand;
    }

    public void setItem_brand(String item_brand) {
        this.item_brand = item_brand;
    }

    public int getItem_size() {
        return item_size;
    }

    public void setItem_size(int item_size) {
        this.item_size = item_size;
    }
}
