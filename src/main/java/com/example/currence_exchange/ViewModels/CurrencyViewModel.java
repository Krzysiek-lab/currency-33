package com.example.currence_exchange.ViewModels;


import com.example.currence_exchange.Enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyViewModel {

    @NotNull(message = "field can not be empty")
    private BigDecimal amount;

    private BigDecimal ratesBuy;

    private BigDecimal ratesSell;

    private CurrencyEnum currencyEnumFrom;

    private CurrencyEnum currencyEnumTo;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateTo;

}
