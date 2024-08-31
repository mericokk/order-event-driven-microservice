package com.orderservice.app.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderservice.app.event.OrderCreatedEvent;
import com.orderservice.app.event.OrderDeletedEvent;
import com.orderservice.app.event.OrderUpdatedEvent;
import com.orderservice.app.exception.OrderNotFoundException;
import com.orderservice.app.factory.OrderEventFactory;
import com.orderservice.app.model.Order;
import com.orderservice.app.repository.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final KafkaTemplate<String, Object> kafkaTemplate;
	private final OrderEventFactory orderEventFactory;
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	public OrderService(OrderRepository orderRepository, KafkaTemplate<String, Object> kafkaTemplate,
			OrderEventFactory orderEventFactory) {
		this.orderRepository = orderRepository;
		this.kafkaTemplate = kafkaTemplate;
		this.orderEventFactory = orderEventFactory;
	}

	@Transactional(readOnly = true)
	public List<Order> getAllOrders() {
		logger.debug("Fetching all orders");
		return orderRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Order getOrderById(Long orderId) {
		logger.debug("Fetching order with ID: {}", orderId);
		Order order = orderRepository.findById(orderId).orElseThrow(() -> {
			logger.error("Order with ID: {} not found", orderId);
			return new OrderNotFoundException(orderId);
		});
		logger.debug("Fetched order: {}", order);
		return order;
	}

	@Transactional
	public Order createOrder(Order newOrder) {
		logger.info("Creating new order: {}", newOrder);
		newOrder.setCreatedAt(new Date());

		Order savedOrder = orderRepository.save(newOrder);
		logger.info("Order saved with ID: {}", savedOrder.getId());

		OrderCreatedEvent event = orderEventFactory.createOrderCreatedEvent(savedOrder);

		try {
			kafkaTemplate.send("order-created", event);
			logger.info("OrderCreatedEvent sent for order ID: {}", savedOrder.getId());
		} catch (Exception e) {
			logger.error("Failed to send OrderCreatedEvent for order ID: {}", savedOrder.getId(), e);
		}

		return savedOrder;
	}

	@Transactional
	public Order updateOrder(Long orderId, Order updatedOrder) {
		logger.info("Updating order with ID: {}", orderId);

		Order existingOrder = orderRepository.findById(orderId).orElseThrow(() -> {
			logger.error("Order with ID: {} not found for update", orderId);
			return new OrderNotFoundException(orderId);
		});

		existingOrder.setStockId(updatedOrder.getStockId());
		existingOrder.setQuantity(updatedOrder.getQuantity());
		existingOrder.setCreatedAt(updatedOrder.getCreatedAt());
		existingOrder.setStatus(updatedOrder.getStatus());

		Order savedOrder = orderRepository.save(existingOrder);
		logger.info("Order updated with ID: {}", savedOrder.getId());

		OrderUpdatedEvent event = orderEventFactory.createOrderUpdatedEvent(savedOrder);

		try {
			kafkaTemplate.send("order-updated", event);
			logger.info("OrderUpdatedEvent sent for order ID: {}", savedOrder.getId());
		} catch (Exception e) {
			logger.error("Failed to send OrderUpdatedEvent for order ID: {}", savedOrder.getId(), e);
		}

		return savedOrder;
	}

	@Transactional
	public void deleteOrder(Long orderId) {
		logger.info("Deleting order with ID: {}", orderId);

		Order existingOrder = orderRepository.findById(orderId).orElseThrow(() -> {
			logger.error("Order with ID: {} not found for delete", orderId);
			return new OrderNotFoundException(orderId);
		});

		orderRepository.delete(existingOrder);
		logger.info("Order deleted with ID: {}", orderId);

		OrderDeletedEvent event = orderEventFactory.createOrderDeletedEvent(existingOrder);

		try {
			kafkaTemplate.send("order-deleted", event);
			logger.info("OrderDeletedEvent sent for order ID: {}", orderId);
		} catch (Exception e) {
			logger.error("Failed to send OrderDeletedEvent for order ID: {}", orderId, e);
		}
	}
}