package com.buyme.shipping;

import org.springframework.data.repository.CrudRepository;

import com.buyme.common.entity.Country;
import com.buyme.common.entity.ShippingRate;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {
	
	public ShippingRate findByCountryAndState(Country country, String state);
}
