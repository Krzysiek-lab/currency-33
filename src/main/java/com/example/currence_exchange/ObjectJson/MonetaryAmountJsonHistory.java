package com.example.currence_exchange.ObjectJson;

import com.example.currence_exchange.Enums.CurrencyEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Data
public class MonetaryAmountJsonHistory {

    @JsonProperty("table")
    public String table;

    @JsonProperty("currency")
    public String currency;

    @JsonProperty("code")
    public CurrencyEnum code;

    @JsonProperty("rates")
    public List<RatesHistory> ratesHistoriesList;

}
