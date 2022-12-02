package com.example.numad22fa_group24.models;

import java.util.Objects;

public class Bottle {
    private String bottleID;
    private String userID;
    private String content;
    private boolean isPublic;

    public Bottle(String userID, String content, boolean isPublic) {
        this.bottleID = "";
        this.userID = userID;
        this.content = content;
        this.isPublic = isPublic;
    }

    public Bottle() {
    }

    public String getBottleID() {
        return bottleID;
    }

    public void setBottleID(String bottleID) {
        this.bottleID = bottleID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bottle bottle = (Bottle) o;
        return isPublic == bottle.isPublic && Objects.equals(bottleID, bottle.bottleID) && Objects.equals(userID, bottle.userID) && Objects.equals(content, bottle.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bottleID, userID, content, isPublic);
    }

    @Override
    public String toString() {
        return "Bottle{" +
                "bottleID='" + bottleID + '\'' +
                ", userID='" + userID + '\'' +
                ", content='" + content + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }

}
