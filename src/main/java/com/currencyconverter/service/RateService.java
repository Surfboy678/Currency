package com.currencyconverter.service;

import com.currencyconverter.model.Currency;
import com.currencyconverter.model.CurrencyConverter;
import com.currencyconverter.model.Rate;
import com.currencyconverter.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RateService {
    private CurrencyRepository currencyRepository;

    @Autowired
    public RateService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Rate> getRate(String code){
        RestTemplate restTemplate = new RestTemplate();
        CurrencyConverter forObject = restTemplate.getForObject("http://api.nbp.pl/api/exchangerates/rates/A/"+ code, CurrencyConverter.class);
        return forObject.getRates();
    }

    //public Double getSourceCurrency(String sourceCurrencyCode){
      //return Optional.ofNullable(currencyRepository.findCurrencyByCode(sourceCurrencyCode)).map(Currency::getMid).orElse(getRate(sourceCurrencyCode));
    //}

    //public Double getTargetCurrency(String targetCurrencyCode){
      //  return Optional.ofNullable(currencyRepository.findCurrencyByCode(targetCurrencyCode)).map(Currency::getMid).orElse(getRate(targetCurrencyCode));
    //}

    public Double getSourceCurrency(String sourceCurrency) {
        Double val = 0.0;
        Currency currency = currencyRepository.findCurrencyByCode(sourceCurrency);
        if (currency == null) {
            Double rate = getRate(sourceCurrency).get(0).getMid();
            Currency currency1 = new Currency();
            currency1.setMid(rate);
            currency1.setCode(sourceCurrency);
        }
        if(getRate(sourceCurrency) == null){
            throw new IllegalArgumentException("Nie ma waluty");
        } else{
            Currency currency1 = new Currency();
             currency1.setMid(getRate(sourceCurrency).get(0).getMid());
             currency1 .setCode(sourceCurrency);
             currencyRepository.save(currency1);
             val = currency1.getMid();
    }
        return val;
}
    public Double getTargetCurrency(String targetCurrencyCode) {
        Double val1 = 0.0;
        Currency currency = currencyRepository.findCurrencyByCode(targetCurrencyCode);
        if (currency == null) {
            Double rate = getRate(targetCurrencyCode).get(0).getMid();
            Currency currency1 = new Currency();
            currency1.setMid(rate);
            currency1.setCode(targetCurrencyCode);
        }
        if(getRate(targetCurrencyCode) == null){
            throw new IllegalArgumentException("Nie ma waluty");
        } else{
            Currency currency1 = new Currency();
            currency1.setMid(getRate(targetCurrencyCode).get(0).getMid());
            currency1 .setCode(targetCurrencyCode);
            currencyRepository.save(currency1);
            val1 = currency1.getMid();
        }
        return val1;
    }

}
