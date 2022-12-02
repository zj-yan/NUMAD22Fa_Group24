package com.example.numad22fa_group24.models;

import java.util.Objects;

public class Bottle {
    public String userID;
    public String content;
    public boolean isPublic;

    public Bottle(String userID, String content, boolean isPublic) {
        this.userID = userID;
        this.content = content;
        this.isPublic = isPublic;
    }

    public Bottle() {
    }

    @Override
    public String toString() {
        return "Bottle{" +
                "userID='" + userID + '\'' +
                ", content='" + content + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bottle bottle = (Bottle) o;
        return isPublic == bottle.isPublic && Objects.equals(userID, bottle.userID) && Objects.equals(content, bottle.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, content, isPublic);
    }
}
