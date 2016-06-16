package com.example.robert.zakupy.model;

/**
 * Created by Robert on 2016-06-12.
 */
public class CurrentProducts {
    public int id;
    public int id_product;
    public String amount;
    public boolean is_completed;

    public CurrentProducts(int id, int id_product, String amount, boolean is_completed) {
        this.id = id;
        this.id_product = id_product;
        this.amount = amount;
        this.is_completed = is_completed;
    }



}

