package com.example.casestudy.model;

public class Item {
    private Product product;
    private int quantity;
    public Item() {}

    public Item(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
