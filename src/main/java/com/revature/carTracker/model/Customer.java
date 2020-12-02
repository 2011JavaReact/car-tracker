package com.revature.carTracker.model;

public class Customer {

	private int id;
	private int carId;
	private String name;
	
	public Customer( ) {
		super();
	}
	
	public Customer(int id, int carId, String name) {
		this.id = id;
		this.carId = carId;
		this.name = name;
	}

	
	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "ID: " + id + " Name: " + name + " ";
	}
}
