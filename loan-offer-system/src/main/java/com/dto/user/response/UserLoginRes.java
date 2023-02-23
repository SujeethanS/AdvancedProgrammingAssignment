package com.dto.user.response;

public class UserLoginRes {

    private int userId;
    private int userType;
    private String fullName;
    private String userEmail;
    private double usedAmount;
    private int installmentPlan;

    public UserLoginRes() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(double usedAmount) {
        this.usedAmount = usedAmount;
    }

    public int getInstallmentPlan() {
        return installmentPlan;
    }

    public void setInstallmentPlan(int installmentPlan) {
        this.installmentPlan = installmentPlan;
    }

    @Override
    public String toString() {
        return "UserLoginRes{" +
                "userId=" + userId +
                ", userType=" + userType +
                ", fullName='" + fullName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", usedAmount=" + usedAmount +
                ", installmentPlan=" + installmentPlan +
                '}';
    }
}
