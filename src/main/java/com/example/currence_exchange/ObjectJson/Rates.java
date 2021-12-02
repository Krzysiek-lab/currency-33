package com.example.currence_exchange.ObjectJson;

import com.example.currence_exchange.Enums.CurrencyEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Rates {


    @JsonProperty("currency")
    public String currency;

    @JsonProperty("code")
    public CurrencyEnum code;

    @JsonProperty("bid")
    public String bid;

    @JsonProperty("ask")
    public String ask;
}
