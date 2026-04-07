# TinyLedger

A simple REST API for managing money movements in an in-memory wallet.

## Functional Requirements

The application should support the following features:

- Record money movements:
    - Deposits
    - Withdrawals
- View the current balance
- View the transaction history

## Technical Requirements

- Deliver a functional web application with APIs only, with no UI.
- The application must run locally.
- You may use any programming language and framework of your choice.
- For simplicity, data should be stored in memory only, using structures such as a map or an array.
- No optional software should be required to run the application, other than any libraries you choose to include.

## Scope and Assumptions

You are free to make assumptions whenever necessary, but please document them clearly in the README.

Please keep the solution simple. The goal of this exercise is to understand your approach to problem solving and your thought process, rather than to test advanced technical knowledge, even if that means making trade-offs.

As a result, you are not expected to implement any of the following:

- Authentication / authorization
- Logging / monitoring
- Transactions / atomic operations
- Persistence in an external database
- Scalability or distributed-system concerns

Feel free to omit other non-essential aspects if needed to keep the solution focused and manageable within the available time.

## Submission

- The solution should be submitted as a link to a public git repository such as GitHub, GitLab, or Bitbucket.

--------------------------------------------------------------------


## Assumptions made:
- Java 21 + Spring Boot
- Account initializes with balance = 0.00
- Data stored in memory (no external persistence, per spec)
- Authentication and authorization won't be implemented
- This project will use Java and Spring Boot.
- Balance cannot be negative
- As currency wasn't specified, transactions won't have currency associated
- Transaction types will be an ENUM
- BigDecimal for monetary values (instead of double for financial precision)
- Transaction types: DEPOSIT / WITHDRAW (enum)
- Account ID auto-generated (assumes 1, 2, 3...)
- Response always includes id, name, balance (AccountResponse)

## Setup & Run

### With Maven installed
```bash
mvn spring-boot:run
```

### Without Maven installed

#### Linux / macOS
```bash
./mvnw spring-boot:run
```

#### Windows
```powershell
.\mvnw.cmd spring-boot:run
```

## Endpoints
The application has the following endpoints:

- POST /v1/accounts - create an account
- GET /api/v1/accounts/{id} - get the balance of an account
- GET /api/v1/accounts/{id}/transactions - get all transactions for an account
- POST /api/v1/accounts/{id}/transactions - create a transaction for an account