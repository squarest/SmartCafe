package com.paper.smartcafe.entities;

/**
 * Created by Chudofom on 24.08.17.
 */

public class TopProduct {
    public Product product;
    public double quantity;

    public double getQuantity() {
        return quantity;
    }

    public TopProduct(Product product, double quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
