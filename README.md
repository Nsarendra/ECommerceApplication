# E-Commerce Backend Project

## Overview

### This project is a backend service for an e-commerce application built using Java Spring Boot. It includes features like JWT-based authentication, role-based access control for "user" and "admin" roles, and CRUD operations for managing products and inventory by Admin and also CRUD operations for cart by user. This project also has  Paginated Product Search API that allows users to search for products based on name and also has mechanisms to handle concurrency during critical operations 

## Prerequisites
### Java 17 or higher: Make sure Java is installed and configured in your environment.
### Maven: Ensure Maven is installed for managing dependencies and building the project.
### Git: For cloning the repository.
### Mysql Database: The project uses Mysql database for data persistence.
### IDE (e.g. Eclipse): Optional but recommended for development.
### Postman or any API client: To test the APIs.

## Setting Up the Project

### Clone the Repository
### Open your terminal and run the following command to clone the project:
## git clone <This repository-url>
## cd <This repository-directory>

### Run the Application
### Run the Application from your IDE.

### Access the Application
### The application will run on http://localhost:8080 by default. You can test the APIs using Postman or any other API client.

## API Endpoints

### POST /register: Register a new user .
### POST /login: Login and receive a JWT token.

## User Endpoints

### POST /addInCart: Add products to the user's cart.
### GET /getCartStatus/{userId}: View the current status of the user's cart.
### DELETE /deleteInCart/{productId}/{userId}: Remove a product from the user's cart.
### POST /placeOrder/{userId}: Place an order.

## Admin Endpoints

### POST /addOrUpdateProduct: Add or update product details.
### DELETE /deleteProduct/{productId}: Delete a product from the inventory.

## public Endpoint

## GET /getAllProducts: Retrieve all products in the inventory.

## JWT Authentication

## When logging in, a JWT token will be issued. This token must be included in the Authorization header for all subsequent requests that require authentication.

## Conclusion

### This backend service provides robust authentication and authorization mechanisms for both user and admin roles and provides efficient handling of concurrency and synchronization during high-traffic operations








