package com.orderservice.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.app.model.Order;
import com.orderservice.app.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {
		logger.info("Received request to get all orders");
		List<Order> orders = orderService.getAllOrders();
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
		logger.info("Received request to get order with ID: {}", orderId);
		Order order = orderService.getOrderById(orderId);
		return ResponseEntity.ok(order);
	}

	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody Order newOrder) {
		logger.info("Received request to create a new order: {}", newOrder);
		Order createdOrder = orderService.createOrder(newOrder);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
	}

	@PutMapping("/{orderId}")
	public ResponseEntity<Order> updateOrder(@RequestBody Order updatedOrder, @PathVariable Long orderId) {
		logger.info("Received request to update order with ID: {}. New data: {}", orderId, updatedOrder);
		Order order = orderService.updateOrder(orderId, updatedOrder);
		return ResponseEntity.ok(order);
	}

	@DeleteMapping("/{orderId}")
	public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
		logger.info("Received request to delete order with ID: {}", orderId);
		orderService.deleteOrder(orderId);
		return ResponseEntity.noContent().build();
	}
}