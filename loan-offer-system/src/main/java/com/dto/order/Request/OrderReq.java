package com.dto.order.Request;

public class OrderReq {

    private int userId;
    private int paymentOption;
    private int installmentStatus;
    private String createdDate;
    private String orderDetails;

    public OrderReq() {
    }

    public OrderReq(int userId, int paymentOption, int installmentStatus, String createdDate, String orderDetails) {
        this.userId = userId;
        this.paymentOption = paymentOption;
        this.installmentStatus = installmentStatus;
        this.createdDate = createdDate;
        this.orderDetails = orderDetails;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(int paymentOption) {
        this.paymentOption = paymentOption;
    }

    public int getInstallmentStatus() {
        return installmentStatus;
    }

    public void setInstallmentStatus(int installmentStatus) {
        this.installmentStatus = installmentStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "OrderReq{" +
                "userId=" + userId +
                ", paymentOption=" + paymentOption +
                ", installmentStatus=" + installmentStatus +
                ", createdDate='" + createdDate + '\'' +
                ", orderDetails='" + orderDetails + '\'' +
                '}';
    }
}
