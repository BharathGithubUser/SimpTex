package com.belivnat.simptex;

public class Users {
    public long mobileNumber;
    public String recentChat;
    public String userName;
    public String userImageUrl;

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }
    public String getRecentChat() {
        return recentChat;
    }

    public void setRecentChat(String recentChat) {
        this.recentChat = recentChat;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    Users(long mobileNumber, String userName, String recentChat, String userImageUrl) {
        this.mobileNumber = mobileNumber;
        this.userName = userName;
        this.recentChat = recentChat;
        this.userImageUrl = userImageUrl;
    }
    Users() { }
}
