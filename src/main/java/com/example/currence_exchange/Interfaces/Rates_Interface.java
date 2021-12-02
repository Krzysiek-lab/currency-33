package com.example.currence_exchange.Interfaces;

import com.example.currence_exchange.Entities.RatesEntity;
import com.example.currence_exchange.Enums.CurrencyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Rates_Interface extends JpaRepository<RatesEntity, Long> {

    @Query("select r from RatesEntity r where r.code = :code")
    List<RatesEntity> getByCode(@Param("code") CurrencyEnum code);


}
