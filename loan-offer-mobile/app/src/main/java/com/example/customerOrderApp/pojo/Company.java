package com.example.customerOrderApp.pojo;

import java.sql.Blob;

/**
 * Created by dev5 on 8/9/2016.
 */
public class Company {

    private String companyName = "company name";
    private String companyDesc = "company name";
    private String companyAddress = "address";
    private String companyStoreAddress = "store address";
    private Blob companyLogo = null;
    private String companyIndustry = "1";
    private String companyContactName = "contact name";
    private String  companyContactNumber = "07777777777";
    private byte[] companylogo = null;
    private String companyCardCompanyName = "n/l";
    private String companyCardCompanyAddress = "n/l";
    private String companyCardCompanyPhoneNo = "0";
    private String companyCardRegDate = "n/l";
    private String companyCardCompanyOfficerName = "name";
    private String cloudComId;
    private String clientId;
    private Integer isRegistered = 1;
    private Integer orderId = 1;
    private Integer locationId = 1;
    private String companyMessage = "n/l";
    private String companyMailId = "n/l";

    public Company(){

    }

    public Company(String companyName, String companyDesc, String  companyAddress, String companyStoreAddress, String companyIndustry, String companyContactName, String  companyContactNumber, byte[] companylogo){
        this.companyName = companyName;
        this.companyDesc = companyDesc;
        this.companyAddress = companyAddress;
        this.companyStoreAddress = companyStoreAddress;
        this.companyIndustry = companyIndustry;
        this.companyContactName = companyContactName;
        this.companyContactNumber = companyContactNumber;
        this.companylogo=companylogo;
    }

    public Company(String companyName, String companyDesc, String companyAddress, String companyStoreAddress, Blob companyLogo, String companyIndustry, String companyContactName, String companyContactNumber, byte[] companylogo, String companyCardCompanyName, String companyCardCompanyAddress, String companyCardCompanyPhoneNo, String companyCardRegDate, String companyCardCompanyOfficerName, String cloudComId, String clientId, Integer isRegistered, Integer orderId, Integer locationId, String companyMessage, String companyMailId) {
        this.companyName = companyName;
        this.companyDesc = companyDesc;
        this.companyAddress = companyAddress;
        this.companyStoreAddress = companyStoreAddress;
        this.companyLogo = companyLogo;
        this.companyIndustry = companyIndustry;
        this.companyContactName = companyContactName;
        this.companyContactNumber = companyContactNumber;
        this.companylogo = companylogo;
        this.companyCardCompanyName = companyCardCompanyName;
        this.companyCardCompanyAddress = companyCardCompanyAddress;
        this.companyCardCompanyPhoneNo = companyCardCompanyPhoneNo;
        this.companyCardRegDate = companyCardRegDate;
        this.companyCardCompanyOfficerName = companyCardCompanyOfficerName;
        this.cloudComId = cloudComId;
        this.clientId = clientId;
        this.isRegistered = isRegistered;
        this.orderId = orderId;
        this.locationId = locationId;
        this.companyMessage = companyMessage;
        this.companyMailId = companyMailId;
    }

    public byte[] getCompanylogo() {
        return companylogo;
    }

    public void setCompanylogo(byte[] companylogo) {
        this.companylogo = companylogo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyStoreAddress() {
        return companyStoreAddress;
    }

    public void setCompanyStoreAddress(String companyStoreAddress) {
        this.companyStoreAddress = companyStoreAddress;
    }

    public String getCompanyIndustry() {
        return companyIndustry;
    }

    public void setCompanyIndustry(String companyIndustry) {
        this.companyIndustry = companyIndustry;
    }

    public String getCompanyContactName() {
        return companyContactName;
    }

    public void setCompanyContactName(String companyContactName) {
        this.companyContactName = companyContactName;
    }

    public String getCompanyContactNumber() {
        return companyContactNumber;
    }

    public void setCompanyContactNumber(String companyContactNumber) {
        this.companyContactNumber = companyContactNumber;
    }

    public String getCompanyCardCompanyName() {
        return companyCardCompanyName;
    }

    public void setCompanyCardCompanyName(String companyCardCompanyName) {
        this.companyCardCompanyName = companyCardCompanyName;
    }

    public String getCompanyCardCompanyAddress() {
        return companyCardCompanyAddress;
    }

    public void setCompanyCardCompanyAddress(String companyCardCompanyAddress) {
        this.companyCardCompanyAddress = companyCardCompanyAddress;
    }

    public String getCompanyCardCompanyPhoneNo() {
        return companyCardCompanyPhoneNo;
    }

    public void setCompanyCardCompanyPhoneNo(String companyCardCompanyPhoneNo) {
        this.companyCardCompanyPhoneNo = companyCardCompanyPhoneNo;
    }

    public String getCompanyCardRegDate() {
        return companyCardRegDate;
    }

    public void setCompanyCardRegDate(String companyCardRegDate) {
        this.companyCardRegDate = companyCardRegDate;
    }

    public String getCompanyCardCompanyOfficerName() {
        return companyCardCompanyOfficerName;
    }

    public void setCompanyCardCompanyOfficerName(String companyCardCompanyOfficerName) {
        this.companyCardCompanyOfficerName = companyCardCompanyOfficerName;
    }

    public String getCloudComId() {
        return cloudComId;
    }

    public void setCloudComId(String cloudComId) {
        this.cloudComId = cloudComId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int isRegistered() {
        return isRegistered;
    }

    public void setRegistered(int registered) {
        isRegistered = registered;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public Blob getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(Blob companyLogo) {
        this.companyLogo = companyLogo;
    }

    public int getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(int isRegistered) {
        this.isRegistered = isRegistered;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setIsRegistered(Integer isRegistered) {
        this.isRegistered = isRegistered;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getCompanyMessage() {
        return companyMessage;
    }

    public void setCompanyMessage(String companyMessage) {
        this.companyMessage = companyMessage;
    }

    public String getCompanyMailId() {
        return companyMailId;
    }

    public void setCompanyMailId(String companyMailId) {
        this.companyMailId = companyMailId;
    }
}
