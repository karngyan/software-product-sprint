package com.google.sps.data;

import lombok.AllArgsConstructor;
import lombok.Data;

/** An item in the comments section */
@Data
@AllArgsConstructor
public class Comment {

    private long id;
    private String text;
    private long createdAt;
    private String email;

}