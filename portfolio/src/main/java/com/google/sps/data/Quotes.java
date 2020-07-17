package com.google.sps.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/** Class containing quotes */
@Data
@AllArgsConstructor
public class Quotes {

    // title of the tv show/movie/book
    private String title;
    private List<String> quoteList;

}