package com.lara.c2c.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lara.c2c.model.PackageData;

public interface PackageDataRepository extends CrudRepository<PackageData, Integer> {

	@Query(value = "select*from package_data where selected = true", nativeQuery = true)
	public Iterable<PackageData> getselected();
}
