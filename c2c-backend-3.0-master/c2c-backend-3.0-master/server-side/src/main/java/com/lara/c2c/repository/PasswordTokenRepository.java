package com.lara.c2c.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lara.c2c.model.PasswordResetToken;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Integer>{

	PasswordResetToken findByUserId(String userId);
	
	@Transactional
	@Modifying
	@Query("delete from PasswordResetToken prt where prt.userId = :userId")
	void deleteTokenByUserId(String userId);

}
