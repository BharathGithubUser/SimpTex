package com.belivnat.simptex.model;

public class UserProfile {
    public String name;
    public String profileImage;
    public UserProfile() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
