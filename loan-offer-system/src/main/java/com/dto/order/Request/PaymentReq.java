package com.dto.order.Request;

public class PaymentReq {

    private int orderId;

    public PaymentReq() {
    }

    public PaymentReq(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "PaymentReq{" +
                "orderId=" + orderId +
                '}';
    }
}
