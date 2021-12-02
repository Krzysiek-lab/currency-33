package com.example.currence_exchange.Interfaces;

import com.example.currence_exchange.Entities.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Currency_Interface extends JpaRepository<CurrencyEntity, Long> {

}
