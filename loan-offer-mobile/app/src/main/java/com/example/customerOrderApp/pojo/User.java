package com.example.customerOrderApp.pojo;

/**
 * Created by keerthanan on 25/07/2016.
 */
public class User {

    private String userID = "1";
    private String userName = "admin";
    private String userPassward = "1234";
    private String userAddress = "address";
    private String userProfilePicture;
    private String userDesignation = "one";
    private String userPNO = "0";
    private String userUniqueId = "n/l";
    private String userAuthId = "n/l";
    private Integer userState = 0;
    private String userEmail = "n@g.com";

    public String getUserUniqueId() {
        return userUniqueId;
    }

    public void setUserUniqueId(String userUniqueId) {
        this.userUniqueId = userUniqueId;
    }

    public String getUserAuthId() {
        return userAuthId;
    }

    public void setUserAuthId(String userAuthId) {
        this.userAuthId = userAuthId;
    }

    public User(String userID, String userName, String userPassward , String userAddress, String userDesignation, String userPNO) {
        this.userID = userID;
        this.userName = userName;
        this.userPassward = userPassward;
        this.userAddress = userAddress;
        //this.userProfilePicture = userProfilePicture;
        this.userDesignation = userDesignation;
        this.userPNO = userPNO;

    }

    public User(String userID, String userName, String userAddress, String userDesignation, String userPNO) {
        this.userID = userID;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userDesignation = userDesignation;
        this.userPNO = userPNO;
    }

    public User(){

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassward() {
        return userPassward;
    }

    public void setUserPassward(String userPassward) {
        this.userPassward = userPassward;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

   // public String getUserProfilePicture() { return userProfilePicture;}

   // public void setUserProfilePicture(String userProfilePicture) {this.userProfilePicture = userProfilePicture;}

    public String getUserDesignation() {return userDesignation;}

    public void setUserDesignation(String userDesignation) {this.userDesignation = userDesignation;}

    public String getUserPNO() {
        return userPNO;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public void setUserPNO(String userPNO) {
        this.userPNO = userPNO;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}

