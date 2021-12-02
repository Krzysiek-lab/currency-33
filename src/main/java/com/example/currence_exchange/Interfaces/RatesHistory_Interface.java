package com.example.currence_exchange.Interfaces;

import com.example.currence_exchange.Entities.RatesHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatesHistory_Interface extends JpaRepository<RatesHistoryEntity, Long> {


}
