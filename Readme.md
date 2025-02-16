# Cubeia Wallet Application

This is a Java wallet application designed for handling basic bookkeeping operations. The application allows users to create accounts, transfer funds, check balances, and list transactions. It is implemented using Java 21, Spring Boot, and provides a REST API to interact with the wallet service.

## Prerequisites

To run, build, or test this application, you will need the following installed on your system:

- Docker (to run the application)
- Maven (if you choose to build or test the application manually)
- Java 21 (if you are running the application without Docker)

## Running the Application with Docker

To simplify running the application, you can use Docker. Follow these steps:

### Build and Run the Application using Docker

1. **Build the Docker image:**

   Run the following command from the project root directory:

   ```bash
   docker build -t cubeia-wallet .
   ```

2. **Run the Docker container:**

   ```bash
   docker run -p 8080:8080 cubeia-wallet
   ```

   The application will now be accessible at `http://localhost:8080`.

3. **Access Swagger Documentation:**

   Once the application is running, you can access the automatically generated Swagger documentation for the API at:

   Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

   API Specification: You can download or view the OpenAPI specification directly from the Swagger interface.

### Timezone Configuration

The application will run in CET (Central European Time) by default. No additional timezone setup is required.

## Manual Build and Run

If you prefer to build and run the application locally without Docker, follow these steps:

### Build the Application

1. **Build using Maven:**

   Ensure Maven is installed, and run the following command in the project root:

   ```bash
   mvn clean package
   ```

   This will create a JAR file under the `target/` directory.

### Run the Application

2. **Run the JAR file:**

   Use the following command to start the application:

   ```bash
   java -jar target/app.jar
   ```

   The application will be available at `http://localhost:8080`.

## Running Tests

To run unit and integration tests, use the following Maven command:

```bash
mvn test
```

Maven will automatically compile the tests and execute them.

## API Endpoints

As previously mentioned, the API documentation is auto-generated and available through Swagger once the application is running.

- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Example Usage via cURL

Here are some example cURL commands to interact with the API:

1. **Create User**:
   ```bash
   curl -X POST http://localhost:8080/api/v1/user \
   -H "Content-Type: application/json" \
   -d '{"email": "test@example.com"}'
   ```

2. **Create Account**:
   ```bash
   curl -X POST http://localhost:8080/api/v1/account/create \
   -H "Content-Type: application/json" \
   -d '{"userId": 1, "initialBalance": 100.00}'
   ```

3. **Get Account Balance**:
   ```bash
   curl -X GET "http://localhost:8080/api/v1/account/balance/1?userId=1"
   ```

4. **Transfer Funds**:
   ```bash
   curl -X PUT http://localhost:8080/api/v1/transactions/transfer \
   -H "Content-Type: application/json" \
   -d '{"fromAccountId": 1, "toAccountId": 2, "userId": 1, "amount": 50.00}'
   ```

5. **Withdraw Funds**:
   ```bash
   curl -X PUT http://localhost:8080/api/v1/transactions/withdraw \
   -H "Content-Type: application/json" \
   -d '{"accountId": 1, "userId": 1, "amount": 50.00}'
   ```

6. **Deposit Funds**:
   ```bash
   curl -X PUT http://localhost:8080/api/v1/transactions/deposit \
   -H "Content-Type: application/json" \
   -d '{"accountId": 1, "userId": 1, "amount": 100.00}'
   ```

7. **Get Transaction History**:
   ```bash
   curl -X GET "http://localhost:8080/api/v1/transactions/1?userId=1"
   ```

---

## Known Shortcuts and Considerations

This implementation contains some shortcuts and simplifications to meet the time constraints of the coding challenge. Below are the key points outlining the decisions made:

1. **Validation Instead of Full Security:**
   Instead of implementing a complete security infrastructure (e.g., OAuth or JWT authentication), validation has been used to ensure that the correct user interacts with the correct account. This approach simplifies the codebase and accelerates development but is not suitable for production environments where security protocols should be strictly enforced.

2. **Implicit Account Creation:**
   Accounts are created implicitly if they do not exist when performing a transfer. This shortcut has been taken to make testing easier and more fluid. However, in a real-world scenario, strict control over account creation is crucial, and implicit creation is not advisable.

3. **Simplified Error Handling:**
   Error handling has been simplified by using basic exceptions, and more granular error management (such as custom error codes or detailed logs) has not been implemented. In a production system, more robust error-handling mechanisms would be necessary.

4. **Performance Optimizations:**
   This solution does not include certain performance optimizations, such as caching or optimized database queries, that could be necessary for larger-scale systems. These could be addressed in further development iterations.

By documenting these considerations, I acknowledge that certain aspects of this implementation could be improved in a real-world scenario, and I have outlined potential improvements where appropriate.

## Additional Information

- This project uses **H2** as the database for simplicity.
- You can use **Postman** or **curl** to interact with the API.