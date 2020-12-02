package com.revature.carTracker.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.Driver;

import com.revature.carTracker.model.Car;

/**
 * Tests for successful instantiation of Car class
 * and working get and set methods.
 * @author Frank Johnson III
 *
 */
public class CarTest {
	
	Car car;
	
	@Before
	public void setEmptyCar() throws Exception {
		car = new Car();
	}
	
	@After
	public void deleteCarObject() throws Exception {
		car = null;
	}

	@Test
	public void testCarCreated() {
		car = new Car(8, "Test", 80000);
		assertNotNull(car);
	}
	
	@Test
	public void testIdSet() {
		car.setId(1);
		assertEquals(car.getId(), 1);
	}
	
	@Test
	public void testNameSet() {
		car.setName("New Car");
		assertEquals(car.getName(), "New Car");
	}
	
	@Test
	public void testPriceSet() {
		car.setPrice(1000);
		assertEquals(car.getPrice(), 1000);
	}
}
