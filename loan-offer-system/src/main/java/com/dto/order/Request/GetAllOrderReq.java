package com.dto.order.Request;

public class GetAllOrderReq {

    private int userId;

    public GetAllOrderReq() {
    }

    public GetAllOrderReq(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "GetAllOrderReq{" +
                "userId=" + userId +
                '}';
    }
}
