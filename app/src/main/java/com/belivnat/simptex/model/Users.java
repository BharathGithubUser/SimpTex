package com.belivnat.simptex.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable {
    public long mobileNumber;
    public String recentChat = "";
    public String userName = "";
    public String userImageUrl = "";

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

    public Users(long mobileNumber, String userName, String recentChat, String userImageUrl) {
        this.mobileNumber = mobileNumber;
        this.userName = userName;
        this.recentChat = recentChat;
        this.userImageUrl = userImageUrl;
    }
    public Users() { }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(recentChat);
        parcel.writeLong(mobileNumber);
        parcel.writeString(userImageUrl);
    }
    private Users(Parcel in) {
        userName = in.readString();
        recentChat = in.readString();
        mobileNumber = in.readLong();
        userImageUrl = in.readString();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };
}
