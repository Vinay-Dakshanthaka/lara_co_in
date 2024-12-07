package com.lara.c2c.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lara.c2c.model.Cart;
import com.lara.c2c.model.Learner;

public interface CartRepository extends JpaRepository<Cart, Integer>{

	public Optional<Cart> findByUser(Learner user);
}
