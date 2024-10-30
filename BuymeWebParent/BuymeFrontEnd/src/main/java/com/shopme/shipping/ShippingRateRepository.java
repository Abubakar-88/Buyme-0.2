package com.Buyme.shipping;

import org.springframework.data.repository.CrudRepository;

import com.Buyme.common.entity.Country;
import com.Buyme.common.entity.ShippingRate;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {
	
	public ShippingRate findByCountryAndState(Country country, String state);
}
