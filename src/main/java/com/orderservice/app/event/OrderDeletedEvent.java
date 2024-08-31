package com.orderservice.app.event;

public class OrderDeletedEvent extends BaseEvent {

	private Long stockId;
	private int quantity;

	public OrderDeletedEvent(Long stockId, int quantity) {
		super();
		this.stockId = stockId;
		this.quantity = quantity;
	}

	public OrderDeletedEvent() {
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

}
