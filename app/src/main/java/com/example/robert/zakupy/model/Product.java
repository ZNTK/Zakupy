package com.example.robert.zakupy.model;

/**
 * Created by Robert on 2016-06-12.
 */
public class Product {
    public int id;
    public String name;
    public int id_category;
    public String unit;

    public Product(int id, String name, int id_category, String unit) {
        this.id = id;
        this.name = name;
        this.id_category = id_category;
        this.unit = unit;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
