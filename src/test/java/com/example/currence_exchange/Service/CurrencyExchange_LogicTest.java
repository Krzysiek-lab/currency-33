package com.example.currence_exchange.Service;


import com.example.currence_exchange.Entities.CurrencyEntity;
import com.example.currence_exchange.Entities.OldRatesEntity;
import com.example.currence_exchange.Entities.RatesEntity;
import com.example.currence_exchange.Enums.CurrencyEnum;
import com.example.currence_exchange.Interfaces.Currency_Interface;
import com.example.currence_exchange.Interfaces.OldRates_Interface;
import com.example.currence_exchange.Interfaces.RatesHistory_Interface;
import com.example.currence_exchange.Interfaces.Rates_Interface;
import com.example.currence_exchange.ObjectJson.MonetaryAmountJson;
import com.example.currence_exchange.ObjectJson.Rates;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CurrencyExchange_LogicTest {


    @Autowired
    Rates_Interface rates_interface;

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    Currency_Interface currency_interface;

    @Autowired
    OldRates_Interface Oldrates_interface;

    @Autowired
    RatesHistory_Interface ratesHistory_interface;


    @Test
    public void it_should_save_Entity() {
        RatesEntity ratesEntity = new RatesEntity();
        ratesEntity.setCode(CurrencyEnum.USD);
        ratesEntity.setId(1L);
        ratesEntity.setCurrency("dolar amerykański");
        ratesEntity.setBid("4.1324");
        ratesEntity.setAsk("4.2158");
        RatesEntity ratesEntity1 = rates_interface.save(ratesEntity);

        Assertions.assertThat(ratesEntity1).hasFieldOrPropertyWithValue("currency", "dolar amerykański");
        Assertions.assertThat(ratesEntity1).hasFieldOrPropertyWithValue("code", CurrencyEnum.USD);
        Assertions.assertThat(ratesEntity1).hasFieldOrPropertyWithValue("bid", "4.1324");
        Assertions.assertThat(ratesEntity1).hasFieldOrPropertyWithValue("ask", "4.2158");
    }


    @Test
    public void it_should_save_OldEntity() {
        OldRatesEntity OldratesEntity = new OldRatesEntity();
        OldratesEntity.setCode(CurrencyEnum.USD);
        OldratesEntity.setId(1L);
        OldratesEntity.setCurrency("dolar amerykański");
        OldratesEntity.setBid("4.1324");
        OldratesEntity.setAsk("4.2158");
        OldRatesEntity OldratesEntity1 = Oldrates_interface.save(OldratesEntity);

        Assertions.assertThat(OldratesEntity1).hasFieldOrPropertyWithValue("currency", "dolar amerykański");
        Assertions.assertThat(OldratesEntity1).hasFieldOrPropertyWithValue("code", CurrencyEnum.USD);
        Assertions.assertThat(OldratesEntity1).hasFieldOrPropertyWithValue("bid", "4.1324");
        Assertions.assertThat(OldratesEntity1).hasFieldOrPropertyWithValue("ask", "4.2158");
    }


    @Test
    public void it_should_save_CurrencyEntity() {
        CurrencyEntity CurrencyEntity = new CurrencyEntity();
        CurrencyEntity.setAmount(new BigDecimal("100"));
        CurrencyEntity.setId(1L);
        CurrencyEntity.setRatesBuy(new BigDecimal("4.1324"));
        CurrencyEntity.setRatesSell(new BigDecimal("4.2158"));
        CurrencyEntity.setCurrencyFrom(CurrencyEnum.USD);
        CurrencyEntity.setCurrencyTo(CurrencyEnum.EUR);
        CurrencyEntity.setDateFrom(LocalDate.of(2021, 11, 30));
        CurrencyEntity.setDateTo(LocalDate.of(2021, 11, 29));
        CurrencyEntity.setSpare("0.00");
        CurrencyEntity currencyEntity = currency_interface.save(CurrencyEntity);

        Assertions.assertThat(currencyEntity).hasFieldOrPropertyWithValue("amount", new BigDecimal("100"));
        Assertions.assertThat(currencyEntity).hasFieldOrPropertyWithValue("ratesBuy", new BigDecimal("4.1324"));
        Assertions.assertThat(currencyEntity).hasFieldOrPropertyWithValue("ratesSell", new BigDecimal("4.2158"));
        Assertions.assertThat(currencyEntity).hasFieldOrPropertyWithValue("currencyFrom", CurrencyEnum.USD);
        Assertions.assertThat(currencyEntity).hasFieldOrPropertyWithValue("currencyTo", CurrencyEnum.EUR);
        Assertions.assertThat(currencyEntity).hasFieldOrPropertyWithValue("dateFrom",
                LocalDate.of(2021, 11, 30));
        Assertions.assertThat(currencyEntity).hasFieldOrPropertyWithValue("dateTo",
                LocalDate.of(2021, 11, 29));
        Assertions.assertThat(currencyEntity).hasFieldOrPropertyWithValue("spare", "0.00");
    }


    @Test
    public void should_find_all_RateEntities() {
        CurrencyEntity CurrencyEntity = new CurrencyEntity();
        CurrencyEntity.setAmount(new BigDecimal("100"));
        CurrencyEntity.setRatesBuy(new BigDecimal("4.1324"));
        CurrencyEntity.setRatesSell(new BigDecimal("4.2158"));
        CurrencyEntity.setCurrencyFrom(CurrencyEnum.USD);
        CurrencyEntity.setCurrencyTo(CurrencyEnum.EUR);
        CurrencyEntity.setDateFrom(LocalDate.of(2021, 11, 30));
        CurrencyEntity.setDateTo(LocalDate.of(2021, 11, 29));
        CurrencyEntity.setSpare("0.00");
        testEntityManager.persist(CurrencyEntity);

        Iterable<CurrencyEntity> CurrencyEntities = currency_interface.findAll();
        Assertions.assertThat(CurrencyEntities).hasSize(1).contains(CurrencyEntity);
    }


    @Test
    public void should_find_all_OldRateEntities() {
        OldRatesEntity OldratesEntity = new OldRatesEntity();
        OldratesEntity.setCode(CurrencyEnum.USD);
        OldratesEntity.setCurrency("dolar amerykański");
        OldratesEntity.setBid("4.1324");
        OldratesEntity.setAsk("4.2158");
        testEntityManager.persist(OldratesEntity);

        Iterable<OldRatesEntity> OldratesEntities = Oldrates_interface.findAll();
        Assertions.assertThat(OldratesEntities).hasSize(1).contains(OldratesEntity);
    }


    @Test
    public void should_find_RateEntity_By_code() {
        RatesEntity ratesEntity = new RatesEntity();
        ratesEntity.setCode(CurrencyEnum.USD);
        ratesEntity.setCurrency("dolar amerykański");
        ratesEntity.setBid("4.1324");
        ratesEntity.setAsk("4.2158");
        testEntityManager.persist(ratesEntity);
        Iterable<RatesEntity> ratesEntities = rates_interface.getByCode(ratesEntity.getCode());
        Assertions.assertThat(ratesEntities).hasSize(1).contains(ratesEntity);
    }


    @Test
    public void should_find_OldRateEntity_By_code() {
        OldRatesEntity OldratesEntity = new OldRatesEntity();
        OldratesEntity.setCode(CurrencyEnum.USD);
        OldratesEntity.setCurrency("dolar amerykański");
        OldratesEntity.setBid("4.1324");
        OldratesEntity.setAsk("4.2158");
        testEntityManager.persist(OldratesEntity);
        Iterable<OldRatesEntity> OldratesEntities = Oldrates_interface.getByCode(OldratesEntity.getCode());
        Assertions.assertThat(OldratesEntities).hasSize(1).contains(OldratesEntity);
    }


    @ParameterizedTest
    @CsvSource({"/,/"})
    public void StringTest(String input_1, String input_2) throws IOException {
        CurrencyExchange_Logic currencyExchange_logic =
                new CurrencyExchange_Logic(currency_interface, rates_interface, Oldrates_interface,ratesHistory_interface);

        currencyExchange_logic.currencyJson(input_1, input_2);
        String dates = "/" + input_1 + "/" + input_2;
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
        MonetaryAmountJson val = objectMapper.readValue(String.valueOf(jsonObject.deleteCharAt(0)
                .deleteCharAt(jsonObject.length() - 1)), MonetaryAmountJson.class);
        assertNotNull(val);
    }

    ///////////////
    @Test
    public void should_calculate_money() {
        //given
        CurrencyExchange_Logic currencyExchange_logic =
                new CurrencyExchange_Logic(currency_interface, rates_interface, Oldrates_interface,ratesHistory_interface);

        Rates l1 = Rates.builder()
                .currency("dolar amerykański")
                .code(CurrencyEnum.USD)
                .bid("4.1324")
                .ask("4.2158")
                .build();

        Rates l2 = Rates.builder()
                .currency("euro")
                .code(CurrencyEnum.EUR)
                .bid("4.6671")
                .ask("4.7613")
                .build();

        var rate = CurrencyEntity.builder()
                .amount(new BigDecimal("100"))
                .currencyFrom(CurrencyEnum.USD)
                .currencyTo(CurrencyEnum.EUR)
                .ratesBuy(new BigDecimal("4.1324"))
                .ratesSell(new BigDecimal("4.2158"))
                .build();

        MonetaryAmountJson monetaryAmountJson = new MonetaryAmountJson();
        monetaryAmountJson.setRatesList(List.of(l1, l2));
        //when
        CurrencyEntity value = currencyExchange_logic.calculateMoney(monetaryAmountJson, rate);
        var rate_2 = CurrencyEntity.builder()
                .amount(value.getAmount())
                .currencyFrom(value.getCurrencyFrom())
                .currencyTo(value.getCurrencyTo())
                .ratesBuy(value.getRatesBuy())
                .ratesSell(value.getRatesSell())
                .spare(value.getSpare())
                .build();
        //then
        assertEquals(rate_2.getAmount(), new BigDecimal("86.79"));
        assertEquals(rate_2.getSpare(), "0.02USD");
    }
    /////////////////
}













