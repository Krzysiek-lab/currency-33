package com.example.currence_exchange.ViewModels;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatesViewModel {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate datesFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate datesTo;

}
