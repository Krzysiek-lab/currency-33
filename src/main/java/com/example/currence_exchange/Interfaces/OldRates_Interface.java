package com.example.currence_exchange.Interfaces;

import com.example.currence_exchange.Entities.OldRatesEntity;
import com.example.currence_exchange.Enums.CurrencyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OldRates_Interface extends JpaRepository<OldRatesEntity, Long> {

    @Query("select r from OldRatesEntity r where r.code = :code")
    List<OldRatesEntity> getByCode(@Param("code") CurrencyEnum code);
}
