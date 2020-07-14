package com.google.sps.data;

/** An item in the comments section */
public class Comment {

    private long id;
    private String text;
    private long createdAt;

    public Comment(long id, String text, long createdAt) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
    }
}