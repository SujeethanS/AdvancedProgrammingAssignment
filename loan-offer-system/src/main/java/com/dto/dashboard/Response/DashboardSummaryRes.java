package com.dto.dashboard.Response;

public class DashboardSummaryRes {

    private int orderCount;
    private double orderTotal;
    private int productCount;
    private int customerCount;

    public DashboardSummaryRes() {
    }

    public DashboardSummaryRes(int orderCount, double orderTotal, int productCount, int customerCount) {
        this.orderCount = orderCount;
        this.orderTotal = orderTotal;
        this.productCount = productCount;
        this.customerCount = customerCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    @Override
    public String toString() {
        return "DashboardSummaryRes{" +
                "orderCount=" + orderCount +
                ", orderTotal=" + orderTotal +
                ", productCount=" + productCount +
                ", customerCount=" + customerCount +
                '}';
    }
}
