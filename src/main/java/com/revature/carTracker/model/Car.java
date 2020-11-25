package com.revature.carTracker.model;

public class Car {
	
	private int id;
	private String name;
	private String price;
	private Customer customer;
	
	public Car() {
		super();
	}
	
	public Car (int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public Car (int id, String name, String price)
	{
		this.id = id;
		this.price = price;
		this.name = name;
	}
	
	public Car (int id, String name, String price, Customer customer)
	{
		this.id = id;
		this.price = price;
		this.name = name;
		this.customer = customer;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return "ID: " + id + "\n" + "NAME: " + name + "\n" + " PRICE: " + price + "\n ";
	}

}
