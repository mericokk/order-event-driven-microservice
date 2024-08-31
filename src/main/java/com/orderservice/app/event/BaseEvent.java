package com.orderservice.app.event;

import java.time.Instant;
import java.util.UUID;

public abstract class BaseEvent {

	private final UUID id;
	private Instant timestamp;

	public BaseEvent() {
		this.id = UUID.randomUUID();
		this.timestamp = Instant.now();
	}

	public UUID getId() {
		return id;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

}