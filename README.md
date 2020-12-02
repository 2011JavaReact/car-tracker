# Car Tracker
## Product Description
This application connects to a remote database that has been pre-populated with data, modifies it, and displays it to the client after they've entered a username, password
and other criteria depending on the endpoint used. 

This application was created using: Java 8, JDBC, Servlets, Tomcat 9.0, PostgreSQL, JUnit, Maven, AWS RDS, and DBeaver.

Logging in with an administrator password allows the user to view all data in all tables (Inventory and Customers table) as well as modify it.

The administrator password is "admin".
  
Logging in with a non-administrator password restricts the user's view to the Inventory table. They have no other priviliges.

# Endpoints

## GET /cars?username=john&password=smith
  Get all of the cars in the Inventory table.
  ```json
  {"id":"1", "name":"Toyota Tacoma", "price":"28000"}
  ```
## GET /customers?username=john&password=admin
Get all of the customers in the Customers table.
  ```json
  {"id":"1", "car_id":"2", "name":"Bill"}
  ```

## GET /cars?username=john&password=smith&carId=4
  Get all of the cars in the Inventory table by ID.
  ```json
  {"id":"4", "name":"Honda Civic", "price":"21000"}
  ```
  
  ## GET /customes?username=john&password=admin&customerId=4
  Get all of the customers in the Customers table by ID.
  ```json
  {"customer_id":"4", "car_id":"1", "name":"Jake"}
  ```
  
  ## GET /cars?username=john&password=smith&carName=Ford F-150
  Get all of the cars in the Inventory table by name.
  ```json
  {"id":"3", "name":"Ford F-150", "price":"30000"}
  ```
  
   ## GET /customes?username=john&password=admin&customerName=Cameron
  Get all of the customers in the Customers table by name.
  ```json
  {"customer_id":"2", "car_id":"4", "name":"Cameron"}
  ```
  
  ## POST /cars?username=john&password=admin&carName=Toyota Venza&carPrice=10200
  Add a new car to the Inventory table.
  ```json
  {"id":"1", "name":"Toyota Venza", "price":"10200"}
  ```
  
  ## POST /customers?username=john&password=admin&carId=1&customerName=Dalvin
  Add a new customer to the Customers table.
  ```json
  {"id":"5", "car_id":"1", "name":"Dalvin"}
  ```
  
  ## DELETE /cars?username=john&password=admin&carId=4
  Delete a car from the Inventory table.
  
  ## DELETE /customers?username=john&password=admin&customerId=4
  Delete a customer from the Customers table.
  
# How to Startup the Application
## Prerequisites
  You will have to download the following in order to set up the app locally:
  - maven
  - tomcat
  - jdk8
  - Running PostgreSQL DB

Make sure you have the following environment variables configured:
  - JAVA_HOME
  - CATALINA_HOME
  - DB_URL = jdbc:postgresql://carsdb.c6k6qxez43qs.us-east-2.rds.amazonaws.com:5432/postgres
  - DB_USERNAME = postgres
  - DB_PASSWORD = revature

NOTICE! This app should use the above environment variables for the database.


## Setup

1. clone the application from repository

> git clone 

2. Pre-populate the db using the `carTracker-setup.sql` file that is provided

3. setup tomcat:

  - Manually:
      - package into a .war file with Maven:
          > mvn clean package
      - deploy to tomcat by copying the war to the `webapps` folder in Tomcat Directory
      - run startup.sh/startup.bat in tomcat folder
      - navigate to {URL}
  - IDE:
      - setup tomcat to run virtually within your ide
      - startup tomcat and navigate to {URL} (usually localhost:8080)
      
      
## Logging
  The following logs will be created:
  
    1. Database connections
    2. HTTP requests
    3. User logins
    4. Successful or unsuccessful attempts to acces or modify data
