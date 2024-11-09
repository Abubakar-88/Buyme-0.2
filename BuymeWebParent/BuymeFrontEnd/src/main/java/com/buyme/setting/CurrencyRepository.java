package com.buyme.setting;

import org.springframework.data.repository.CrudRepository;

import com.buyme.common.entity.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, Integer> {

}
