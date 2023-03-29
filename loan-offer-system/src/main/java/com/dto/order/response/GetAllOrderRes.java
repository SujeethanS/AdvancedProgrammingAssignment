package com.dto.order.response;

public class GetAllOrderRes {

    private int orderId;
    private double grandTotal;
    private int userId;
    private String createdDate;
    private int installmentStatus;

    public GetAllOrderRes() {
    }

    public GetAllOrderRes(int orderId, double grandTotal, int userId, String createdDate, int installmentStatus) {
        this.orderId = orderId;
        this.grandTotal = grandTotal;
        this.userId = userId;
        this.createdDate = createdDate;
        this.installmentStatus = installmentStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public int getInstallmentStatus() {
        return installmentStatus;
    }

    public void setInstallmentStatus(int installmentStatus) {
        this.installmentStatus = installmentStatus;
    }

    @Override
    public String toString() {
        return "GetAllOrderRes{" +
                "orderId=" + orderId +
                ", grandTotal=" + grandTotal +
                ", userId=" + userId +
                ", createdDate='" + createdDate + '\'' +
                ", installmentStatus=" + installmentStatus +
                '}';
    }
}
