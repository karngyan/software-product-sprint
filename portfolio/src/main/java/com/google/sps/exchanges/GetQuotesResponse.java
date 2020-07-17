package com.google.sps.exchanges;

import com.google.sps.data.Quotes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetQuotesResponse {
    private List<Quotes> quotes;
}