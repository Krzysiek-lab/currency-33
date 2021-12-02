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
public class RatesViewModel {

    @NotNull(message = "field can not be empty")
    private CurrencyEnum code;

    private String currency;

    private String bid;

    private String ask;

}
