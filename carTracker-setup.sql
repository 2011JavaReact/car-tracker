DROP TABLE IF EXISTS Inventory;

DROP TABLE IF EXISTS Customers;

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

SELECT *
FROM Inventory;

SELECT *
FROM Customers;

INSERT INTO Inventory (name, price)
VALUES ('Toyota Tacoma', '26150'),
		('Ford F-150', '28745'),
		('Jeep Grand Cherokee', '1000'),
		('Honda Civic', '2300');

INSERT INTO Customers (name, car_id)
VALUES ('Frank', 1),
		('Ralph', 2),
		('Jake', 1);
	