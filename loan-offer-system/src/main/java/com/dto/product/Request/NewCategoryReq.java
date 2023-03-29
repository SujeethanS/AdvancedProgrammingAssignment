package com.dto.product.Request;

public class NewCategoryReq {

    private String categoryName;

    public NewCategoryReq() {
    }

    public NewCategoryReq(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "NewCategory{" +
                "categoryName='" + categoryName + '\'' +
                '}';
    }
}
