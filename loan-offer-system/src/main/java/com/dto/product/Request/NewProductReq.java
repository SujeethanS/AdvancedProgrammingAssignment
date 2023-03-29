package com.dto.product.Request;

public class NewProductReq {

    private String productName;
    private int categoryId;
    private int brandId;
    private double salePrice;

    public NewProductReq() {
    }

    public NewProductReq(String productName, int categoryId, int brandId, double salePrice) {
        this.productName = productName;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.salePrice = salePrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "NewProductReq{" +
                "productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", salePrice=" + salePrice +
                '}';
    }
}
