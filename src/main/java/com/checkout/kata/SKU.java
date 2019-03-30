package com.checkout.kata;

import java.util.Objects;

public class SKU {
    private String name;
    private int price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SKU sku = (SKU) o;
        return price == sku.price &&
                name.equals(sku.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    public SKU(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }
}
