# Hotel Booking - Hotel Services

This repository contains the **User Service** component of the Hotel Booking system, responsible for user registration, authentication, hotel creation, hotel booking, and email verification via OTP.

## ✨ Features

- **User Registration & Login**  
  Secure registration and login system using JWT authentication.

- **Email Verification via OTP**  
  Sends a One-Time Password (OTP) to users via email using SMTP to verify accounts.

- **Hotel Creation**  
  Allows authorized users to create and manage hotel listings.

- **Hotel Booking**  
  Enables users to search and book hotels.

- **Paystack Integration**  
  Uses Paystack to handle payments for hotel bookings.

## 🛠 Tech Stack

- Java 21  
- Spring Boot  
- Maven  
- RabbitMQ  
- Paystack API  
- SMTP (Email Verification)  
- Docker & Docker Compose

## 🚀 Getting Started

### Prerequisites

Make sure you have the following installed:

- Java 21+
- Maven
- Docker & Docker Compose

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/mowumib/hotel.booking.user-services.git
   cd hotel.booking.user-services
   ```

2. **Configure SMTP, Paystack & Environment Variables**

   Update the `application.properties` or use environment variables for:

   - SMTP host, port, username, and password
   - RabbitMQ connection details
   - JWT secret key
   - Paystack public and secret keys

3. **Build the Project**

   ```bash
   ./mvnw clean install
   ```
   
4. **Start Services with Docker Compose**

   ```bash
   docker-compose up --build
   ```

   This will spin up:
   - RabbitMQ on port `5672` (management UI at [http://localhost:15672](http://localhost:15672))
   - The user service container

## 🔐 Role-Based Access Control (RBAC)

The service supports two roles:

- **Admin**
  - Can add hotels and rooms
  - Manages listings

- **Client**
  - Can view and book hotels

 ## 📬 Email Verification

Upon user registration, the service sends a verification OTP via email using configured SMTP credentials. Users must verify their email before accessing restricted features.

## 💳 Payment Integration (Paystack)

This service integrates with the Paystack API to process payments during hotel booking. Payment validation and confirmation are handled securely via Paystack callbacks and verification endpoints.

## 📫 API Endpoints (Sample)

| Method | Endpoint                      | Description                         |
|--------|-------------------------------|-------------------------------------|
| POST   | `/hotel/user/signup`          | Register a new user                 |
| POST   | `/hotel/user/login`           | Authenticate and get token          |
| POST   | `/hotel/user/verify-otp-code` | Verify user with OTP                |
| POST   | `/hotels/add-hotel`           | Create a new hotel (auth required)  |
| GET    | `hotels/get-all-hotels`       | List available hotels               |
| POST   | `/hotels/bookings`            | Book a hotel                        |
| POST   | `/api/v1/paystack/transaction`| Initialize payment via Paystack     |


## 🐇 RabbitMQ Integration
RabbitMQ is used **internally** by this service for tasks like email OTP handling.  
Access RabbitMQ UI: [http://localhost:15672](http://localhost:15672)  
(Default login: `guest` / `guest`)
