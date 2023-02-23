package com.dto.user.response;

public class InstallmentPlanRes {

    private int installmentPlanId;
    private String planName;

    public InstallmentPlanRes() {
    }

    public InstallmentPlanRes(int installmentPlanId, String planName) {
        this.installmentPlanId = installmentPlanId;
        this.planName = planName;
    }

    public int getInstallmentPlanId() {
        return installmentPlanId;
    }

    public void setInstallmentPlanId(int installmentPlanId) {
        this.installmentPlanId = installmentPlanId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Override
    public String toString() {
        return "InstallmentPlanRes{" +
                "installmentPlanId=" + installmentPlanId +
                ", planName='" + planName + '\'' +
                '}';
    }
}
