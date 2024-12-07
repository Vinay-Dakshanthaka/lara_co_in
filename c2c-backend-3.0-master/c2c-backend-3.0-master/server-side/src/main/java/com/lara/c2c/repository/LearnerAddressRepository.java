package com.lara.c2c.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.model.LearnerAddress;

public interface LearnerAddressRepository extends JpaRepository<LearnerAddress, Integer>{
	
	//@Query("select * from LearnerAddress la where la.userId = :userId()")
	public List<LearnerAddress> findByUserId(String userId);
	
	@Modifying
	@Query("update LearnerAddress la set la.houseNo = :#{#la.houseNo} "+
	" where la.userId= :#{#la.userId} and la.addressType = :#{#la.addressType}")
	@Transactional
	public int updateLearnerAddressDetail(LearnerAddress la);
	
}
