package com.Buyme.admin.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.Buyme.common.entity.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, Integer> {
	
	public List<Currency> findAllByOrderByNameAsc();
}
