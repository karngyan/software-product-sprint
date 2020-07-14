package com.google.sps.exchanges;

import com.google.sps.data.Comment;
import java.util.List;

public class GetCommentsResponse {
    private List<Comment> comments;

    public GetCommentsResponse(List<Comment> comments) {
        this.comments = comments;
    }
}