package com.example.currence_exchange.ObjectJson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RatesHistory {


    @JsonProperty("no")
    public String no;

    @JsonProperty("effectiveDate")
    public Date effectiveDate;

    @JsonProperty("ask")
    public String ask;

    @JsonProperty("bid")
    public String bid;
}
