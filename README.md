# Car Tracker
## Product Description
This application connects to a remote database that has been pre-populated with data, retireves it, and displays it to the client after they've entered a username and password. 

This application was created using: Java 8, JDBC, Servlets, Tomcat 9.0, PostgreSQL, JUnit, Maven, AWS RDS, and DBeaver.

Logging in with and administrator password allows the user to view all data in all tables (Inventory and Customers table).

The administrator password is "admin".
  
Logging in with a non-administrator password allows the user to view data only in the Inventory table.

# Endpoints

## GET /cars?username=john&password=smith
  Get all of the cars in the database Inventory table. See example output below:
  ```json
  {"id":"1", "name":"Toyota", "price":"28000"}
  ```
## GET /customers?username=john&password=smith
  ```json
  {"id":"1", "car_id":"2", "price":"Bill"}
  ```

## GET /cars?username=john&password=admin
Get all data in both the Inventory and Customers table. See example output below:
  ```json
  {"id":"1", "name":"Toyota", "price":"28000"},
  {"id":"1", "car_id":"2", "price":"Bill"}
  ```
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
  
    1.database connections
    2.HTTP requests
    3.User logins
