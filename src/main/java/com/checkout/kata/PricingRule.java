package com.checkout.kata;

public class PricingRule {
    private String itemName;
    private int numberOfItems;
    private double price;

    public PricingRule(String itemName, int numberOfItems, double price) {
        this.itemName = itemName;
        this.numberOfItems = numberOfItems;
        this.price = price;
    }

    public int getNumberOfItems() {
        return this.numberOfItems;
    }

    public double getPrice() {
        return this.price;
    }

    public String getItemName() {
        return this.itemName;
    }
}
