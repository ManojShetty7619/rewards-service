# Customer Reward Points API

## Overview

This Spring Boot application calculates customer reward points based on
purchase transactions within a given date range.

The system provides a single REST API to calculate rewards for a
customer.

------------------------------------------------------------------------

## Reward Rules

-   2 points for every dollar spent above \$100\
-   1 point for every dollar spent between \$50 and \$100\
-   0 points for purchases below \$50

------------------------------------------------------------------------

## Tech Stack

-   Java 17\
-   Spring Boot\
-   Spring Data JPA\
-   H2 In-Memory Database\
-   Lombok\
-   JUnit 5\
-   Mockito\
-   SLF4J + Logback

------------------------------------------------------------------------

## Data Setup

Schema and test data are automatically loaded from:

src/main/resources/schema.sql\
src/main/resources/data.sql

------------------------------------------------------------------------

## How to Run

mvn clean install\
mvn spring-boot:run

Application runs at:

http://localhost:8080

------------------------------------------------------------------------

## H2 Database Console

http://localhost:8080/h2-console

JDBC URL:\
jdbc:h2:mem:rewardsdb

Username:\
manoj

Password:\
manoj

------------------------------------------------------------------------

# API Endpoint

GET /api/rewards/{customerId}

Example Request:

http://localhost:8080/api/rewards/1?startDate=2025-01-01&endDate=2025-03-31

------------------------------------------------------------------------

## Query Parameters

  Parameter   Type        Required   Format
  ----------- ----------- ---------- ------------
  startDate   LocalDate   Yes        yyyy-MM-dd
  endDate     LocalDate   Yes        yyyy-MM-dd

------------------------------------------------------------------------

# SUCCESS RESPONSE

Example Response (Customer 1 - John):

{ "customerId": 1, "customerName": "John", "monthlyRewards": {
"JANUARY": { "totalPoints": 90, "totalAmount": 120.0 }, "FEBRUARY": {
"totalPoints": 30, "totalAmount": 80.0 }, "MARCH": { "totalPoints": 0,
"totalAmount": 40.0 } }, "transactions": \[ { "id": 1, "amount": 120.0,
"date": "2025-01-15" }, { "id": 2, "amount": 80.0, "date": "2025-02-10"
}, { "id": 3, "amount": 40.0, "date": "2025-03-05" } \] }

------------------------------------------------------------------------

# VALIDATION & ERROR SCENARIOS

1.  Invalid Date

GET /api/rewards/1?startDate=2025-03-01&endDate=2025-04-31

Response: { "status": 400, "message": "Invalid date. Please use
yyyy-MM-dd and provide a valid calendar date." }

2.  From Date Greater Than To Date

GET /api/rewards/1?startDate=2025-05-01&endDate=2025-03-01

Response: { "status": 400, "message": "From date cannot be after end
date" }

3.  No Transactions Found

GET /api/rewards/1?startDate=2026-01-01&endDate=2026-03-31

Response: { "status": 404, "message": "No transactions found" }

4.  Invalid Customer ID

GET /api/rewards/999?startDate=2025-01-01&endDate=2025-03-31

Response: { "status": 404, "message": "Customer not found" }

------------------------------------------------------------------------

# Logging

-   INFO → API start and completion\
-   DEBUG → Transaction reward calculations\
-   ERROR → Validation and exception handling

------------------------------------------------------------------------

# Testing

mvn test

Uses @ExtendWith(MockitoExtension.class), @Mock and @InjectMocks.

------------------------------------------------------------------------

# Project Structure

com.homework\
├── controller\
├── service\
├── repository\
├── model\
├── dto\
├── exception

------------------------------------------------------------------------

# Author

Manoj
