package com.google.sps.exchanges;

import com.google.sps.data.Quotes;
import java.util.List;

public class GetQuotesResponse {
    private List<Quotes> quotes;

    public GetQuotesResponse(List<Quotes> quotes) {
        this.quotes = quotes;
    }
}