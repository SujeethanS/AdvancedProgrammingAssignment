package com.dto.order.Request;

public class OrderDetailsReq {

    private int orderId;

    public OrderDetailsReq() {
    }

    public OrderDetailsReq(int orderId) {
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
        return "OrderDetailsReq{" +
                "orderId=" + orderId +
                '}';
    }
}
