DROP TABLE IF EXISTS Inventory;

DROP TABLE IF EXISTS Customers;

CREATE TABLE Inventory (

	car_id SERIAL PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	price VARCHAR(255) NOT NULL
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

INSERT INTO Inventory (name, price)
VALUES ('Toyota_Tacoma', '26150'),
		('Ford_F-150', '28745');
	
INSERT INTO Inventory (name, price)
VALUES ('Jeep_Grand_Cherokee', '1000');

INSERT INTO Inventory (name, price)
VALUES ('Honda', '1000');

INSERT INTO Customers (name, car_id)
VALUES  ('Frank', 1),
		('Ralph', 2),
		('Jake', 1),
		('Frank', 3);
	