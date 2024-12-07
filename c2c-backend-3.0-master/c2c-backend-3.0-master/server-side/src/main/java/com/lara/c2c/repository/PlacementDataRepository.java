package com.lara.c2c.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lara.c2c.model.PlacementData;


public interface PlacementDataRepository extends CrudRepository<PlacementData, Integer>{

	@Query(value = "select*from placement_data where selected = true", nativeQuery = true)
	public  Iterable<PlacementData> getselectedPlacement();


}
