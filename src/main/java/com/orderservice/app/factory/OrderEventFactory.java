package com.orderservice.app.factory;

import org.springframework.stereotype.Component;

import com.orderservice.app.event.OrderCreatedEvent;
import com.orderservice.app.event.OrderDeletedEvent;
import com.orderservice.app.event.OrderUpdatedEvent;
import com.orderservice.app.model.Order;

@Component
public class OrderEventFactory {

	public OrderCreatedEvent createOrderCreatedEvent(Order order) {
		return new OrderCreatedEvent(order.getStockId(), order.getQuantity(), order.getCreatedAt(), order.getStatus());
	}

	public OrderUpdatedEvent createOrderUpdatedEvent(Order order) {
		return new OrderUpdatedEvent(order.getStockId(), order.getQuantity(), order.getCreatedAt(), order.getStatus());
	}

	public OrderDeletedEvent createOrderDeletedEvent(Order order) {
		return new OrderDeletedEvent(order.getStockId(), order.getQuantity());
	}

}
