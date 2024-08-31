package com.orderservice.app.event;

import java.util.Date;

import com.orderservice.app.model.OrderStatus;

public class OrderCreatedEvent extends BaseEvent {

	private Long stockId;
	private int quantity;
	private Date createdAt;
	private OrderStatus status;

	public OrderCreatedEvent(Long stockId, int quantity, Date createdAt, OrderStatus status) {
		super();
		this.stockId = stockId;
		this.quantity = quantity;
		this.createdAt = createdAt;
		this.status = status;
	}

	public OrderCreatedEvent() {
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

}
