package com.example.numad22fa_group24.models;

public class Bottle {
    private String content;
    private boolean isPublic;

    public Bottle(String content, boolean isPublic) {
        this.isPublic = isPublic;
        this.content = content;
    }

    public Bottle() {
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Bottle{" +
                "content='" + content + '\'' +
                ", isPublic=" + isPublic +
                '}';
    }
}
