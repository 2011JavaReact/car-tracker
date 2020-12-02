DROP TABLE IF EXISTS Inventory;

DROP TABLE IF EXISTS Customers;

DROP TABLE IF EXISTS Credentials;

CREATE TABLE Inventory (

	car_id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	price INT NOT NULL
);

CREATE TABLE Customers (

	customer_id SERIAL,
	car_id int,
	name VARCHAR(255) NOT null,
	primary key(customer_id),
	constraint fk_car
		foreign key(car_id)  
			references Inventory(car_id)
);

CREATE TABLE Credentials (

	id SERIAL PRIMARY KEY,
	username VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255) NOT null,
	customer_id INT not null,
	isAdmin boolean not null,
	constraint fk_customer
		foreign key(customer_id)
			references Customers(customer_id)
);

INSERT INTO Inventory (name, price)
VALUES ('Toyota Tacoma', '26150'),
		('Ford F-150', '28745'),
		('Jeep Grand Cherokee', '1000'),
		('Honda Civic', '2300');

INSERT INTO Customers (name, car_id)
VALUES ('Frank', 1),
		('Ralph', 2),
		('Jake', 1);

INSERT INTO Credentials (username, password, customer_id, isadmin)
VALUES ('adamAdmin', 'admin', 1, true),
		('normanNorm', 'normie', 3, false);
	