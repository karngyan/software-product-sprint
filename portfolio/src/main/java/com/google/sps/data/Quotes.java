package com.google.sps.data;

import java.util.List;

/** Class containing quotes */
public class Quotes {


    // title of the tv show/movie/book
    private String title;
    private List<String> quoteList;

    public Quotes(String title, List<String> quoteList) {
        this.title = title;
        this.quoteList = quoteList;
    }
}