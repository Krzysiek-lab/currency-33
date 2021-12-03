package com.example.currence_exchange.Service;

import com.example.currence_exchange.Entities.CurrencyEntity;
import com.example.currence_exchange.Entities.OldRatesEntity;
import com.example.currence_exchange.Entities.RatesEntity;
import com.example.currence_exchange.Entities.RatesHistoryEntity;
import com.example.currence_exchange.Enums.CurrencyEnum;
import com.example.currence_exchange.Interfaces.*;
import com.example.currence_exchange.ObjectJson.MonetaryAmountJson;
import com.example.currence_exchange.ObjectJson.MonetaryAmountJsonHistory;
import com.example.currence_exchange.ObjectJson.Rates;
import com.example.currence_exchange.ObjectJson.RatesHistory;
import com.example.currence_exchange.ViewModels.CurrencyViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyExchange_Logic implements LogicInterface {


    private final Currency_Interface currency_interface;
    private final Rates_Interface rates_interface;
    private final OldRates_Interface Oldrates_interface;
    private final RatesHistory_Interface ratesHistory_interface;
    String start, end;


    @Override
    public MonetaryAmountJson currencyJson(String start, String end) throws IOException {
        this.start = start;
        this.end = end;
        String dates = "/" + start + "/" + end;
        URL url = new URL("http://api.nbp.pl/api/exchangerates/tables/c" + dates);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder jsonObject = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonObject.append(line);
        }
        bufferedReader.close();
        ObjectMapper objectMapper = new ObjectMapper();
        getRates();
        return objectMapper.readValue(String.valueOf(jsonObject.deleteCharAt(0)
                .deleteCharAt(jsonObject.length() - 1)), MonetaryAmountJson.class);
    }

    @Override
    public void currencyJsonHistory(CurrencyEnum code) throws IOException {
        URL url = new URL("http://api.nbp.pl/api/exchangerates/rates/c/" + code + "/last/7/?format=json");
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder jsonObject = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonObject.append(line);
        }
        bufferedReader.close();
        ObjectMapper objectMapper = new ObjectMapper();
        var end = objectMapper.readValue(String.valueOf(jsonObject),
                MonetaryAmountJsonHistory.class);
        RatesHistoryJsonToRatesHistoryEntity(end.getRatesHistoriesList()).forEach(ratesHistory_interface::save);
    }

    @Override
    public CurrencyEntity CurrencyViewModelToEntity(CurrencyViewModel currencyViewModel) {//1
        CurrencyEntity currencyEntity;
        if (currencyViewModel.getDateFrom() != null || currencyViewModel.getDateTo() != null) {
            currencyEntity = CurrencyEntity.builder()
                    .currencyFrom(currencyViewModel.getCurrencyEnumFrom())
                    .currencyTo(currencyViewModel.getCurrencyEnumTo())
                    .amount(currencyViewModel.getAmount())
                    .dateFrom(currencyViewModel.getDateFrom())
                    .dateTo(currencyViewModel.getDateTo())
                    .build();
        } else {
            currencyEntity = CurrencyEntity.builder()
                    .currencyFrom(currencyViewModel.getCurrencyEnumFrom())
                    .currencyTo(currencyViewModel.getCurrencyEnumTo())
                    .amount(currencyViewModel.getAmount())
                    .build();
        }
        return currencyEntity;
    }

    @Override
    public void getRates() throws IOException {
        String dates = "/" + this.start + "/" + this.end;
        URL url = new URL("http://api.nbp.pl/api/exchangerates/tables/c" + dates);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder jsonObject = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonObject.append(line);
        }
        bufferedReader.close();
        ObjectMapper objectMapper = new ObjectMapper();
        MonetaryAmountJson monetaryAmountJson = objectMapper.readValue(String.valueOf(jsonObject.deleteCharAt(0)
                .deleteCharAt(jsonObject.length() - 1)), MonetaryAmountJson.class);
        RatesJsonToRatesEntity(monetaryAmountJson.ratesList).forEach(rates_interface::save);
    }

    @Override
    public Page<CurrencyEntity> pagination(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<CurrencyEntity> list;
        if (currency_interface.count() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = (int) Math.min(startItem + pageSize, currency_interface.count());
            list = currency_interface.findAll().subList(startItem, toIndex);
        }
        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), currency_interface.count());
    }

    @Override
    public void getRatesOfDates(String starts, String ends) throws IOException {
        Oldrates_interface.deleteAll();
        String dates = "/" + starts + "/" + ends;
        URL url = new URL("http://api.nbp.pl/api/exchangerates/tables/c" + dates);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuilder jsonObject = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonObject.append(line);
        }
        bufferedReader.close();
        ObjectMapper objectMapper = new ObjectMapper();
        MonetaryAmountJson monetaryAmountJson = objectMapper.readValue(String.valueOf(jsonObject.deleteCharAt(0)
                .deleteCharAt(jsonObject.length() - 1)), MonetaryAmountJson.class);
        OldRatesJsonToRatesEntity(monetaryAmountJson.ratesList).forEach(Oldrates_interface::save);
    }

    @Override
    public List<RatesEntity> RatesJsonToRatesEntity(List<Rates> ratesList) {
        List<RatesEntity> list = new ArrayList<>();
        for (Rates rate : ratesList) {
            var r = RatesEntity.builder()
                    .ask(rate.ask)
                    .bid(rate.bid)
                    .code(rate.code)
                    .currency(rate.currency)
                    .build();
            list.add(r);
        }
        return list;
    }

    @Override
    public List<OldRatesEntity> OldRatesJsonToRatesEntity(List<Rates> ratesList) {
        List<OldRatesEntity> list = new ArrayList<>();
        for (Rates rate : ratesList) {
            var r = OldRatesEntity.builder()
                    .ask(rate.ask)
                    .bid(rate.bid)
                    .code(rate.code)
                    .currency(rate.currency)
                    .build();
            list.add(r);
        }
        return list;
    }

    @Override
    public CurrencyEntity calculateMoney(MonetaryAmountJson monetaryAmountJson, CurrencyEntity currencyEntity) {
        List<Rates> listFirst = monetaryAmountJson.getRatesList().stream().filter(e -> e.code.equals(currencyEntity
                .getCurrencyFrom())).collect(Collectors.toList());
        List<Rates> listSecond = monetaryAmountJson.getRatesList().stream().filter(e -> e.code.equals(currencyEntity
                .getCurrencyTo())).collect(Collectors.toList());

        Rates one, two;

        if (listFirst.size() != 0) {
            one = listFirst.get(0);
        } else {
            one = Rates.builder()
                    .currency("Polski Złoty")
                    .ask("1")
                    .bid("1")
                    .code(CurrencyEnum.PLN)
                    .build();
        }
        if (listSecond.size() != 0) {
            two = listSecond.get(0);
        } else {
            two = Rates.builder()
                    .currency("Polski Złoty")
                    .ask("1")
                    .bid("1")
                    .code(CurrencyEnum.PLN)
                    .build();

        }

        BigDecimal am, val_1, val_2, val_3, val_4, val_5, val_6;

        if (two.code.toString().equals("PLN") && !one.code.toString().equals("PLN")) {
            am = new BigDecimal(String.valueOf(currencyEntity.getAmount()));
            val_4 = new BigDecimal(one.bid).multiply(am);// w pln
            val_3 = new BigDecimal(val_4.toString()).setScale(2, RoundingMode.DOWN);// calosc
            val_6 = val_4.subtract(val_3).setScale(2, RoundingMode.DOWN);
        } else if (two.code.toString().equals("PLN") && one.code.toString().equals("PLN")) {
            am = new BigDecimal(String.valueOf(currencyEntity.getAmount()));
            val_6 = new BigDecimal("0.00");// spare
            val_3 = new BigDecimal(String.valueOf(am));// amount
        } else if (one.code.toString().equals("PLN") && !two.code.toString().equals("PLN")) {/////////
            am = new BigDecimal(String.valueOf(currencyEntity.getAmount()));
            val_6 = new BigDecimal("0.00");// spare
            val_4 = new BigDecimal(String.valueOf(am)).divide(new BigDecimal(two.bid), 2, RoundingMode.DOWN);
            val_3 = new BigDecimal(val_4.toString()).setScale(2, RoundingMode.DOWN);
        } else if (one.code.toString().equals(two.code.toString())) {
            val_3 = new BigDecimal(String.valueOf(currencyEntity.getAmount()));
            val_6 = new BigDecimal(0);
        } else {
            am = new BigDecimal(String.valueOf(currencyEntity.getAmount()));
            val_1 = new BigDecimal(one.bid).multiply(am);
            val_2 = val_1.divide(new BigDecimal(two.ask), 30, RoundingMode.HALF_EVEN);
            val_3 = new BigDecimal(val_2.toString()).setScale(2, RoundingMode.DOWN);
            val_4 = val_2.subtract(val_3);
            val_5 = val_4.multiply(new BigDecimal(two.bid));
            val_6 = val_5.multiply(new BigDecimal(one.ask)).setScale(2, RoundingMode.DOWN); // rezta w pierwszej walucie
        }
        if (start == null && end == null) {
            return jsonObToCurrencyEntity(one, two, val_3, val_6, "", "");
        } else {
            return jsonObToCurrencyEntity(one, two, val_3, val_6, start, end);
        }
    }

    @Override
    public CurrencyEntity jsonObToCurrencyEntity(Rates ratesOne, Rates ratesTwo, BigDecimal amount,
                                                 BigDecimal spare, String start, String end) {
        CurrencyEntity currencyEntity;
        if (!start.equals("") || !end.equals("")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(start, formatter);
            LocalDate localDate_2 = LocalDate.parse(end, formatter);
            currencyEntity = CurrencyEntity.builder()
                    .currencyFrom(ratesOne.code)
                    .currencyTo(ratesTwo.code)
                    .amount(amount)
                    .ratesBuy(new BigDecimal(ratesOne.bid))
                    .ratesSell(new BigDecimal(ratesOne.ask))
                    .spare(String.valueOf(spare) + ratesOne.code)
                    .dateFrom(localDate)
                    .dateTo(localDate_2)
                    .build();
        } else {
            currencyEntity = CurrencyEntity.builder()
                    .currencyFrom(ratesOne.code)
                    .currencyTo(ratesTwo.code)
                    .amount(amount)
                    .ratesBuy(new BigDecimal(ratesOne.bid))
                    .ratesSell(new BigDecimal(ratesOne.ask))
                    .spare(String.valueOf(spare) + ratesOne.code)
                    .build();
            currency_interface.save(currencyEntity);
        }
        return currencyEntity;
    }

    @Override
    public List<RatesHistoryEntity> RatesHistoryJsonToRatesHistoryEntity(List<RatesHistory> ratesList) {
        List<RatesHistoryEntity> list = new ArrayList<>();
        for (RatesHistory rate : ratesList) {
            var r = RatesHistoryEntity.builder()
                    .ask(rate.ask)
                    .bid(rate.bid)
                    .effectiveDate(rate.effectiveDate)
                    .build();
            list.add(r);
        }
        return list;
    }

}
