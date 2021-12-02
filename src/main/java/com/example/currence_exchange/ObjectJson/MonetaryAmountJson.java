package com.example.currence_exchange.ObjectJson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Data
public class MonetaryAmountJson {

    @JsonProperty("table")
    public String table;

    @JsonProperty("no")
    public String no;

    @JsonProperty("tradingDate")
    public Date tradingDate;

    @JsonProperty("effectiveDate")
    public Date effectiveDate;

    @JsonProperty("rates")
    public List<Rates> ratesList;

}
