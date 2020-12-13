package com.revature.carTracker.model;

import java.util.ArrayList;

public class AdminList {
	private ArrayList<Car> cars;
	private ArrayList<Customer> customers;
	
	public AdminList() {
		super();
	}

	public AdminList(ArrayList<Car> cars, ArrayList<Customer> customers) {
		super();
		this.cars = cars;
		this.customers = customers;
	}

	public ArrayList<Car> getCars() {
		return cars;
	}

	public void setCars(ArrayList<Car> cars) {
		this.cars = cars;
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}
	
	
	
}
