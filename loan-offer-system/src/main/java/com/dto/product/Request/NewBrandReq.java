package com.dto.product.Request;

public class NewBrandReq {

    private String brandName;

    public NewBrandReq() {
    }

    public NewBrandReq(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "NewBrandReq{" +
                "brandName='" + brandName + '\'' +
                '}';
    }
}
