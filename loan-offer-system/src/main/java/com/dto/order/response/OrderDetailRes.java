package com.dto.order.response;

public class OrderDetailRes {

    private int orderDetailId;
    private int OrderId;
    private String productName;
    private int productId;
    private int qty;
    private double subTotal;

    public OrderDetailRes() {
    }

    public OrderDetailRes(int orderDetailId, int orderId, String productName, int productId, int qty, double subTotal) {
        this.orderDetailId = orderDetailId;
        OrderId = orderId;
        this.productName = productName;
        this.productId = productId;
        this.qty = qty;
        this.subTotal = subTotal;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "OrderDetailRes{" +
                "orderDetailId=" + orderDetailId +
                ", OrderId=" + OrderId +
                ", productName='" + productName + '\'' +
                ", productId=" + productId +
                ", qty=" + qty +
                ", subTotal=" + subTotal +
                '}';
    }
}
