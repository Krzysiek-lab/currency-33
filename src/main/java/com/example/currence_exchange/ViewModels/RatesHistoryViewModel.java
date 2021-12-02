package com.example.currence_exchange.ViewModels;


import com.example.currence_exchange.Enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatesHistoryViewModel {

    @NotNull(message = "field can not be empty")
    private CurrencyEnum codeHist;

    private String currency;

    private String bid;

    private String ask;

    private CurrencyEnum code;

}
