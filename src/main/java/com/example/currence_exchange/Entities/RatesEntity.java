package com.example.currence_exchange.Entities;

import com.example.currence_exchange.Enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table
public class RatesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private String currency;

    @Column
    private CurrencyEnum code;

    @Column
    private String bid;

    @Column
    private String ask;
}
