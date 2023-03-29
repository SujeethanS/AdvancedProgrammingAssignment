package com.dto.order.response;

public class PaymentRes {

    private int historyId;
    private int customerId;
    private int installmentPlanId;
    private String installmentPlan;
    private double amountPaid;
    private int orderId;
    private String createdDate;

    public PaymentRes() {
    }

    public PaymentRes(int historyId, int customerId, int installmentPlanId, String installmentPlan, double amountPaid, int orderId, String createdDate) {
        this.historyId = historyId;
        this.customerId = customerId;
        this.installmentPlanId = installmentPlanId;
        this.installmentPlan = installmentPlan;
        this.amountPaid = amountPaid;
        this.orderId = orderId;
        this.createdDate = createdDate;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getInstallmentPlanId() {
        return installmentPlanId;
    }

    public void setInstallmentPlanId(int installmentPlanId) {
        this.installmentPlanId = installmentPlanId;
    }

    public String getInstallmentPlan() {
        return installmentPlan;
    }

    public void setInstallmentPlan(String installmentPlan) {
        this.installmentPlan = installmentPlan;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "PaymentRes{" +
                "historyId=" + historyId +
                ", customerId=" + customerId +
                ", installmentPlanId=" + installmentPlanId +
                ", installmentPlan='" + installmentPlan + '\'' +
                ", amountPaid=" + amountPaid +
                ", orderId=" + orderId +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
