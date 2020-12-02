package com.revature.carTracker.model;

public class Car {
	
	private int id;
	private String name;
	private int price;
	
	public Car() {
		super();
	}
	
	public Car (int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public Car (int id, String name, int price)
	{
		this.id = id;
		this.price = price;
		this.name = name;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
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
