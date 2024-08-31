package com.orderservice.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderservice.app.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
