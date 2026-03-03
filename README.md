# Customer Reward Points API

## Overview

This Spring Boot application calculates customer reward points based on
purchase transactions.

The API supports:

-   Default last 3 months logic
-   Custom number of months
-   Custom date range
-   Proper validation handling
-   Global exception handling

------------------------------------------------------------------------

## Reward Rules

-   2 points for every dollar spent above \$100
-   1 point for every dollar spent between \$50 and \$100
-   0 points for purchases below \$50

------------------------------------------------------------------------

## Tech Stack

-   Java 17
-   Spring Boot
-   Spring Data JPA
-   H2 In-Memory Database
-   Lombok
-   JUnit 5
-   Mockito
-   SLF4J + Logback

------------------------------------------------------------------------

## How to Run

mvn clean install\
mvn spring-boot:run

Application runs at:

http://localhost:8080

http://localhost:8080/h2-console

JDBC URL : jdbc:h2:mem:rewardsdb
Username : manoj
Password : manoj

------------------------------------------------------------------------

# API Endpoint

GET /api/rewards

------------------------------------------------------------------------

## Query Parameters

  Parameter    Type        Required   Description
  ------------ ----------- ---------- ------------------
  customerId   Long        Yes        Customer ID
  months       Integer     No         Number of months
  startDate    LocalDate   No         yyyy-MM-dd
  endDate      LocalDate   No         yyyy-MM-dd

------------------------------------------------------------------------

# 1  Default (Last 3 Months)

### Request

GET /api/rewards?customerId=1

### Response

``` json
{
  "customerId": 1,
  "customerName": "John",
  "monthlyRewards": {
    "JANUARY": {
      "totalPoints": 90,
      "totalAmount": 120.0
    },
    "FEBRUARY": {
      "totalPoints": 30,
      "totalAmount": 80.0
    },
    "MARCH": {
      "totalPoints": 150,
      "totalAmount": 150.0
    }
  },
  "transactions": [
    {
      "id": 1,
      "amount": 120.0,
      "date": "2026-01-15"
    },
    {
      "id": 2,
      "amount": 80.0,
      "date": "2026-02-10"
    },
    {
      "id": 3,
      "amount": 150.0,
      "date": "2026-03-05"
    }
  ]
}
```

------------------------------------------------------------------------

# 2️  Custom Months

### Request

GET /api/rewards?customerId=6&months=4

### Response

``` json
{
  "customerId": 6,
  "customerName": "Charlie",
  "monthlyRewards": {
    "DECEMBER": {
      "totalPoints": 350,
      "totalAmount": 300.0
    },
    "FEBRUARY": {
      "totalPoints": 70,
      "totalAmount": 110.0
    }
  },
  "transactions": [
    {
      "id": 12,
      "amount": 300.0,
      "date": "2025-12-20"
    },
    {
      "id": 13,
      "amount": 110.0,
      "date": "2026-02-25"
    }
  ]
}
```

------------------------------------------------------------------------

# 3️  Custom Date Range

### Request

GET /api/rewards?customerId=2&startDate=2026-02-01&endDate=2026-03-31

### Response

``` json
{
  "customerId": 2,
  "customerName": "Alice",
  "monthlyRewards": {
    "FEBRUARY": {
      "totalPoints": 250,
      "totalAmount": 200.0
    },
    "MARCH": {
      "totalPoints": 10,
      "totalAmount": 60.0
    }
  },
  "transactions": [
    {
      "id": 4,
      "amount": 200.0,
      "date": "2026-02-18"
    },
    {
      "id": 5,
      "amount": 60.0,
      "date": "2026-03-01"
    }
  ]
}
```

------------------------------------------------------------------------

# 4️  Validation Errors

### months + date range together

GET
/api/rewards?customerId=1&months=3&startDate=2026-01-01&endDate=2026-03-31

``` json
{
  "status": 400,
  "message": "Provide either months or startDate & endDate, not both"
}
```

------------------------------------------------------------------------

### From date greater than End date

GET /api/rewards?customerId=1&startDate=2026-03-01&endDate=2026-01-01

``` json
{
  "status": 400,
  "message": "From date cannot be after end date"
}
```

------------------------------------------------------------------------

### No Transactions Found

GET /api/rewards?customerId=4

``` json
{
  "status": 404,
  "message": "No transactions found"
}
```

------------------------------------------------------------------------

# Testing

mvn test

Uses:

-   @ExtendWith(MockitoExtension.class)
-   @Mock
-   @InjectMocks

------------------------------------------------------------------------

# Author

Manoj
