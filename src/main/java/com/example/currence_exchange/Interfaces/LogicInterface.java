package com.example.currence_exchange.Interfaces;

import com.example.currence_exchange.Entities.CurrencyEntity;
import com.example.currence_exchange.Entities.OldRatesEntity;
import com.example.currence_exchange.Entities.RatesEntity;
import com.example.currence_exchange.Entities.RatesHistoryEntity;
import com.example.currence_exchange.Enums.CurrencyEnum;
import com.example.currence_exchange.ObjectJson.MonetaryAmountJson;
import com.example.currence_exchange.ObjectJson.Rates;
import com.example.currence_exchange.ObjectJson.RatesHistory;
import com.example.currence_exchange.ViewModels.CurrencyViewModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LogicInterface {

    MonetaryAmountJson currencyJson(String start, String end) throws IOException;

    void currencyJsonHistory(CurrencyEnum code) throws IOException;

    CurrencyEntity CurrencyViewModelToEntity(CurrencyViewModel currencyViewModel);

    void getRates() throws IOException;

    Page<CurrencyEntity> pagination(Pageable pageable);

    void getRatesOfDates(String start, String end) throws IOException;

    List<OldRatesEntity> OldRatesJsonToRatesEntity(List<Rates> ratesList);

    public List<RatesHistoryEntity> RatesHistoryJsonToRatesHistoryEntity(List<RatesHistory> ratesList);

    List<RatesEntity> RatesJsonToRatesEntity(List<Rates> ratesList);

    CurrencyEntity calculateMoney(MonetaryAmountJson monetaryAmountJson, CurrencyEntity currencyEntity);

    CurrencyEntity jsonObToCurrencyEntity(Rates rates, Rates rates_two,
                                          BigDecimal amount, BigDecimal spare, String start, String end);

}
