package com.orderservice.app.exception;

public class OrderNotFoundException extends RuntimeException {

	public OrderNotFoundException(Long orderId) {
		super(String.format("Could not find order with ID: %d", orderId));
	}

}
