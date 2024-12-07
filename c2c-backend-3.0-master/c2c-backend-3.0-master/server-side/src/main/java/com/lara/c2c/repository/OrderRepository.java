package com.lara.c2c.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lara.c2c.model.Order;

public interface OrderRepository extends JpaRepository<Order, String>{
	
	@Modifying
	@Query("update Order o set o.orderStatus = :status where o.orderId = :orderId and o.userId = :userId")
	@Transactional
	public int updateOrderStatus(String orderId, String status, String userId);

	public Order findByOrderId(String orderId);

	public Order findByUserCurrOrderSessTimeId(String userCurrOrderSessTimeId);
	
	@Modifying
	@Query("update Order o set o.orderStatus = :orderStatus, o.orderStatusMessage = :orderStatusMessage where o.orderId = :orderId and o.userId = :userId")
	@Transactional
	public void updateOrderAbortedStatus(String orderId, String orderStatus, String orderStatusMessage, String userId);

	public Order findByOrderIdAndUserId(String orderId, String userId);

}
