package com.google.sps.exchanges;

import com.google.sps.data.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentsResponse {
    private List<Comment> comments;
}