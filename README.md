# Customer Reward Points API

## Overview
This Spring Boot application calculates customer reward points
based on purchase transactions.

The system supports both:
- Synchronous API flow
- Asynchronous API flow

Reward Rules:
- 2 points for every dollar spent above $100
- 1 point for every dollar spent between $50 and $100
- No points for purchases below $50

---

## Tech Stack
- Java 8
- Spring Boot
- Spring Data JPA
- H2 In-Memory Database
- JUnit 5
- Mockito
- SLF4J + Logback
- CompletableFuture (Async processing)

---

## Data Setup
Schema and test data are automatically loaded from:

- `src/main/resources/schema.sql`
- `src/main/resources/data.sql`

Data includes:
- Customers
- Products
- 3 months of transactions

---

## How to Run

```bash
mvn spring-boot:run
```

Application runs at:

```
http://localhost:8080
```

---

## H2 Database Console

```
http://localhost:8080/h2-console
```

JDBC URL:
```
jdbc:h2:mem:rewardsdb
```

Username:
```
manoj
```

Password:
```
manoj
```

---

# API Endpoints

## 1️⃣ Synchronous API

```
GET /api/rewards/sync/{customerId}
```

Example:

```
http://localhost:8080/api/rewards/sync/1?from=2025-01-01&to=2025-03-31
```

---

## 2️⃣ Asynchronous API

```
GET /api/rewards/async/{customerId}
```

Example:

```
http://localhost:8080/api/rewards/async/1?from=2025-01-01&to=2025-03-31
```

Both APIs return the same response structure.

---

# Query Parameters

| Parameter | Type | Required | Format |
|------------|--------|------------|----------|
| from | LocalDate | Yes | yyyy-MM-dd |
| to | LocalDate | Yes | yyyy-MM-dd |

---

# SUCCESS RESPONSE

Example Request:

```
GET /api/rewards/sync/1?from=2025-01-01&to=2025-03-31
```

Example Response:

```json
{
  "customerId": 1,
  "monthlyRewards": {
    "2025-01": 90,
    "2025-02": 30,
    "2025-03": 0
  },
  "totalRewards": 120,
  "transactions": [
    {
      "txnId": 1,
      "customerId": 1,
      "productId": 101,
      "amount": 120.0,
      "txnDate": "2025-01-15"
    }
  ]
}
```

---

# VALIDATION & ERROR SCENARIOS

---

## ❌ 1. Invalid Date (Non-existent date)

Request:

```
GET /api/rewards/sync/1?from=2025-03-01&to=2025-04-31
```

Response:

```json
{
  "status": 400,
  "message": "Invalid date. Please use yyyy-MM-dd and valid calendar date."
}
```

---

## ❌ 2. Invalid Date Format

Request:

```
GET /api/rewards/sync/1?from=03-01-2025&to=03-31-2025
```

Response:

```json
{
  "status": 400,
  "message": "Invalid date format or invalid date value"
}
```

---

## ❌ 3. From Date Greater Than To Date

Request:

```
GET /api/rewards/sync/1?from=2025-05-01&to=2025-03-01
```

Response:

```json
{
  "status": 400,
  "message": "From date cannot be after To date"
}
```

---

## ❌ 4. No Transactions Found

Request:

```
GET /api/rewards/sync/1?from=2026-01-01&to=2026-03-31
```

Response:

```json
{
  "status": 404,
  "message": "No transactions found"
}
```

---

## ❌ 5. Invalid Customer ID

Request:

```
GET /api/rewards/sync/999?from=2025-01-01&to=2025-03-31
```

Response:

```json
{
  "status": 404,
  "message": "No transactions found"
}
```

---

# Sync vs Async Flow

| Feature | Sync | Async |
|----------|-------|--------|
| URL | /sync/ | /async/ |
| Thread Type | Blocking | Non-blocking |
| Return Type | RewardResponse | CompletableFuture<RewardResponse> |
| JSON Output | Same | Same |

---

# Logging

Logging levels:

- INFO → API request start/end
- DEBUG → Transaction calculations
- ERROR → Exception handling

---

# Project Structure

```
com.homework
 ├── controller
 ├── service
 ├── repository
 ├── model
 ├── dto
 ├── util
 ├── exception
```

---

# Author

Manoj
