package com.dto.dashboard.Request;

public class DashboardSummaryReq {

    private int userId;

    public DashboardSummaryReq() {
    }

    public DashboardSummaryReq(int userId) {
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
        return "OrderSummaryReq{" +
                "userId=" + userId +
                '}';
    }
}
