package com.example.currence_exchange.Controllers;

import com.example.currence_exchange.Entities.CurrencyEntity;
import com.example.currence_exchange.Interfaces.OldRates_Interface;
import com.example.currence_exchange.Interfaces.RatesHistory_Interface;
import com.example.currence_exchange.Interfaces.Rates_Interface;
import com.example.currence_exchange.ObjectJson.MonetaryAmountJson;
import com.example.currence_exchange.Service.CurrencyExchange_Logic;
import com.example.currence_exchange.ViewModels.CurrencyViewModel;
import com.example.currence_exchange.ViewModels.DatesViewModel;
import com.example.currence_exchange.ViewModels.RatesHistoryViewModel;
import com.example.currence_exchange.ViewModels.RatesViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class ViewControllers {

    private final CurrencyExchange_Logic currencyExchange_logic;
    private final Rates_Interface rates_interface;
    private final OldRates_Interface Oldrates_interface;
    private final RatesHistory_Interface ratesHistory_interface;


    @PostMapping("specificRate")
    public String getSpecificRate(Model model, @ModelAttribute("specific") @Valid RatesViewModel ratesViewModel,
                                  BindingResult bindingResult) {
        Set set = new HashSet<>();
        if (!bindingResult.hasErrors()) {
            try {
                Oldrates_interface.deleteAll();
                rates_interface.deleteAll();
                currencyExchange_logic.currencyJson("", "");
                currencyExchange_logic.currencyJsonHistory(ratesViewModel.getCode());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            set.addAll(rates_interface.getByCode(ratesViewModel.getCode()));
            set.addAll(Oldrates_interface.getByCode(ratesViewModel.getCode()));
            model.addAttribute("spec", set);
        }
        return "OneRate";
    }


    @PostMapping("specificHistoricRate")
    public String getSpecificHistoricRate(Model model, @ModelAttribute("specificHistoric")
    @Valid RatesViewModel RatesViewModel,
                                          BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try {
                ratesHistory_interface.deleteAll();
                currencyExchange_logic.currencyJsonHistory(RatesViewModel.getCode());///
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            model.addAttribute("specHist", ratesHistory_interface.findAll());
        }
        return "OneHistoricRate";
    }


    @GetMapping("all")
    public String all(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page,
                      @RequestParam(value = "size", defaultValue = "5") Integer size) {

        Page<CurrencyEntity> ratePage = currencyExchange_logic.pagination(PageRequest.of(page - 1, size));
        model.addAttribute("all", ratePage);
        int totalPages = ratePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "allPage";
    }


    @GetMapping("AllRates")
    public String allRates(Model model) {
        model.addAttribute("ratesOf", new DatesViewModel());
        rates_interface.deleteAll();
        model.addAttribute("allRates", rates_interface.findAll());
        return "allRates";
    }


    @GetMapping("Calculation")
    public String mainPaige(Model model) {
        model.addAttribute("currencyAndAmount", new CurrencyViewModel());
        return "formPage";
    }


    @GetMapping("/")
    public String Page(Model model) {
        model.addAttribute("specific", new RatesViewModel());
        return "OneRate";
    }


    @GetMapping("/historic")
    public String PageHist(Model model) {
        model.addAttribute("specificHistoric", new RatesHistoryViewModel());
        return "OneHistoricRate";
    }


    @PostMapping("RatesOfPeriod")
    public String RatesOfPeriod(@ModelAttribute("ratesOf") @Valid DatesViewModel datesViewModel,
                                BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            String start = Objects.toString(datesViewModel.getDatesFrom(), "");
            String end = Objects.toString(datesViewModel.getDatesTo(), "");
            try {
                currencyExchange_logic.getRatesOfDates(start, end);
                model.addAttribute("allRates", Oldrates_interface.findAll());
                Oldrates_interface.deleteAll();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return "allRates";
    }


    @PostMapping("Calculate")
    public String Calculate(@ModelAttribute("currencyAndAmount") @Valid CurrencyViewModel currencyViewModel,
                            BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            var entity = currencyExchange_logic.CurrencyViewModelToEntity(currencyViewModel);
            String start = Objects.toString(entity.getDateFrom(), "");
            String end = Objects.toString(entity.getDateTo(), "");
            try {
                MonetaryAmountJson currencyJson = currencyExchange_logic.currencyJson(start, end);
                var calVal = currencyExchange_logic.calculateMoney(currencyJson, entity);
                model.addAttribute("endValue", calVal);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return "end";
        } else {
            return "formPage";
        }
    }

}
