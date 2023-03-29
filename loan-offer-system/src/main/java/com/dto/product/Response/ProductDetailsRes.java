package com.dto.product.Response;

public class ProductDetailsRes {

    private int productId;

    private String productDetails;

    public ProductDetailsRes() {
    }

    public ProductDetailsRes(int productId, String productDetails) {
        this.productId = productId;
        this.productDetails = productDetails;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    @Override
    public String toString() {
        return "ProductDetailsRes{" +
                "productId='" + productId + '\'' +
                ", productDetails='" + productDetails + '\'' +
                '}';
    }
}
