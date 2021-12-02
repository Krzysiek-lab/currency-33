package com.example.currence_exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.currence_exchange.Entities")
@EnableJpaRepositories("com.example.currence_exchange.Interfaces")
public class CurrenceExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrenceExchangeApplication.class, args);
    }


}
