Här är ett förslag till en README-fil i markdown (MD) för ditt projekt:

```md
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

Swagger UI: http://localhost:8080/swagger-ui/index.html

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

- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`

### Key Endpoints:

1. **Create User**:  
   `POST /api/v1/user`  
   Payload: `{ "email": "test@example.com" }`

2. **Create Account**:  
   `POST /api/v1/account/create`  
   Payload: `{ "userId": 1, "initialBalance": 100.00 }`

3. **Get Account Balance**:  
   `GET /api/v1/account/balance/{accountId}?userId={userId}`

4. **Transfer Funds**:  
   `PUT /api/v1/transactions/transfer`  
   Payload: `{ "fromAccountId": 1, "toAccountId": 2, "userId": 1, "amount": 50.00 }`  
   *Note: If the target account (`toAccountId`) doesn't exist, it will be created automatically.*

5. **Withdraw Funds**:  
   `PUT /api/v1/transactions/withdraw`  
   Payload: `{ "accountId": 1, "userId": 1, "amount": 50.00 }`

6. **Deposit Funds**:  
   `PUT /api/v1/transactions/deposit`  
   Payload: `{ "accountId": 1, "userId": 1, "amount": 100.00 }`

7. **Get Transaction History**:  
   `GET /api/v1/transactions/{accountId}?userId={userId}`

## Additional Information

- This project uses **H2** as the database for simplicity.
- You can use **Postman** or **curl** to interact with the API.
