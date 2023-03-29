package com.dto.product.Request;

public class ProductDetailsReq {

    private String category;
    private String brand;

    public ProductDetailsReq() {
    }

    public ProductDetailsReq(String category, String brand) {
        this.category = category;
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "ProductDetailsReq{" +
                "category=" + category +
                ", brand=" + brand +
                '}';
    }
}
