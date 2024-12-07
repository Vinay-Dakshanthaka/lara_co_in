package com.lara.c2c.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lara.c2c.model.WatchedVideoNotification;

public interface WatchedVideoNotifRepository extends JpaRepository<WatchedVideoNotification, Integer>{

	public List<WatchedVideoNotification> findByUserId(String userId);
	
}
