package com.example.currence_exchange.Entities;

import com.example.currence_exchange.Enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
@Entity
@Table
@NoArgsConstructor
public class CurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    Long id;

    @Column
    private BigDecimal amount;
    @Column
    private BigDecimal ratesBuy;
    @Column
    private BigDecimal ratesSell;
    @Column
    private CurrencyEnum currencyFrom;
    @Column
    private CurrencyEnum currencyTo;
    @Column
    private LocalDate dateFrom;
    @Column
    private LocalDate dateTo;
    @Column
    private String spare;

}
