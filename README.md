# Order Service

## Project Overview

Order Service is a microservice designed to handle customer orders within an event-driven architecture. Developed using Java and Spring Boot, it communicates asynchronously with other microservices via Apache Kafka. It provides RESTful APIs for order management and integrates with PostgreSQL for persistent data storage.

## Features

- **Event-Driven Architecture**: The service uses an event-driven architecture to achieve asynchronous communication between microservices. This approach leverages Apache Kafka to publish and consume events. When an order is created or updated, an event is published to Kafka, allowing other services (e.g., Stock Service) to react to these changes without direct coupling. This decoupling of services enhances system flexibility, scalability, and resilience. For example, if the Stock Service needs to update inventory levels based on new orders, it can listen to the relevant Kafka topics and process events as they arrive, ensuring that stock data remains synchronized without synchronous API calls.
- **RESTful APIs**: Exposes endpoints for creating, retrieving, updating, and deleting orders.
- **PostgreSQL Database**: Utilizes PostgreSQL for robust and reliable data storage.
- **Kafka Integration**: Publishes and consumes Kafka events for order creation, updates, and other order-related activities.

## Project Structure

The project is organized into the following main packages:

- **`config`**: Contains configuration classes for Kafka producers and consumers, and application settings.
- **`controller`**: Handles REST API requests and responses for order management.
- **`event`**: Defines event classes used for Kafka messaging.
- **`exception`**: Includes custom exception classes for error handling.
- **`factory`**: Provides factory classes for creating event instances.
- **`model`**: Defines domain models representing orders.
- **`repository`**: Contains JPA repositories for database interactions.
- **`service`**: Implements business logic related to orders.

## API Endpoints

- **GET /orders**: Retrieve all orders.
- **GET /orders/{id}**: Retrieve an order by ID.
- **POST /orders**: Create a new order.
- **PUT /orders/{id}**: Update an existing order.
- **DELETE /orders/{id}**: Delete an order by ID.
