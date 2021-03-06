package com.cart4j.model.product;

public enum ProductSizeUnit {

    INCH("inch"),
    MILLIMETER("mm"),
    CENTIMETER("cm");

    private String value;

    ProductSizeUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
