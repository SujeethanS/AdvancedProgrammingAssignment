package com.dto.user.request;

public class GetCustomerDetailReq {

    private int customerId;

    public GetCustomerDetailReq() {
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "GetCustomerDetailReq{" +
                "customerId=" + customerId +
                '}';
    }

}
