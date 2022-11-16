package com.currencyconverter.controller;

import com.currencyconverter.model.Currency;
import com.currencyconverter.repository.CurrencyRepository;
import com.currencyconverter.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rates")
public class RateController {
    private RateService rateService;
    private CurrencyRepository currencyRepository;

    @Autowired
    public RateController(RateService rateService, CurrencyRepository currencyRepository) {
        this.rateService = rateService;
        this.currencyRepository = currencyRepository;
    }

    @GetMapping("/{code}")
    public Double getRate(@PathVariable String code){
        return rateService.getRate(code).get(0).getMid();
    }

    @PostMapping("/save/{code}")
    public Currency saveRate(@PathVariable String code){
       //Double price = rateService.getRate(code);
       Currency currency = new Currency();
       currency.setCode(code);
       //currency.setMid(price);
       return currencyRepository.save(currency);
    }
@GetMapping("/result/{sourceCurrencyCode}/{sourceCurrencyAmount}/{targetCurrencyCode}")
    public ResponseEntity saveCurrencyAfterConvert(@PathVariable String sourceCurrencyCode, @PathVariable Double sourceCurrencyAmount, @PathVariable String targetCurrencyCode){
       Double exchangeRateSource =  rateService.getSourceCurrency (sourceCurrencyCode);
       Double targetCurrencyAmount = rateService.getTargetCurrency(targetCurrencyCode);
       Double result = (sourceCurrencyAmount *exchangeRateSource) / targetCurrencyAmount;
       return ResponseEntity.ok(result);
    }
}
