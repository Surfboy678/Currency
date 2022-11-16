package com.currencyconverter.repository;

import com.currencyconverter.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    Currency findCurrencyByCode(String code);

}
