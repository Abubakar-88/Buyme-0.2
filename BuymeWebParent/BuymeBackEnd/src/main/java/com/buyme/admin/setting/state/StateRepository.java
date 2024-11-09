package com.buyme.admin.setting.state;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.buyme.common.entity.Country;
import com.buyme.common.entity.State;

public interface StateRepository extends CrudRepository<State, Integer> {
	
	public List<State> findByCountryOrderByNameAsc(Country country);
}
