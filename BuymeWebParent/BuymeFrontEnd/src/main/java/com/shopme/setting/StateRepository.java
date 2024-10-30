package com.Buyme.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.Buyme.common.entity.Country;
import com.Buyme.common.entity.State;

public interface StateRepository extends CrudRepository<State, Integer> {
	
	public List<State> findByCountryOrderByNameAsc(Country country);
}
