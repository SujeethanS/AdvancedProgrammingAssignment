package com.dto.user.request;

public class CreateNewUserReq {

    private int userId;
    private String firstName;
    private String lastName;
    private String dob;
    private int userType;
    private String userName;
    private String secretKey;
    private String userEmail;
    private String userMobileNumber;
    private String nic;
    private int customerId;
    private double loanBalance;
    private double usedAmount;
    private int installPlan;

    public CreateNewUserReq() {
    }

    public CreateNewUserReq(int userId, String firstName, String lastName, String dob, int userType, String userName, String secretKey, String userEmail, String userMobileNumber, String nic, int customerId, double loanBalance, double usedAmount, int installPlan) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.userType = userType;
        this.userName = userName;
        this.secretKey = secretKey;
        this.userEmail = userEmail;
        this.userMobileNumber = userMobileNumber;
        this.nic = nic;
        this.customerId = customerId;
        this.loanBalance = loanBalance;
        this.usedAmount = usedAmount;
        this.installPlan = installPlan;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobileNumber() {
        return userMobileNumber;
    }

    public void setUserMobileNumber(String userMobileNumber) {
        this.userMobileNumber = userMobileNumber;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(double loanBalance) {
        this.loanBalance = loanBalance;
    }

    public double getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(double usedAmount) {
        this.usedAmount = usedAmount;
    }

    public int getInstallPlan() {
        return installPlan;
    }

    public void setInstallPlan(int installPlan) {
        this.installPlan = installPlan;
    }

    @Override
    public String toString() {
        return "CreateNewUserReq{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob='" + dob + '\'' +
                ", userType=" + userType +
                ", userName='" + userName + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userMobileNumber='" + userMobileNumber + '\'' +
                ", nic='" + nic + '\'' +
                ", customerId=" + customerId +
                ", loanBalance=" + loanBalance +
                ", usedAmount=" + usedAmount +
                ", installPlan=" + installPlan +
                '}';
    }

}
