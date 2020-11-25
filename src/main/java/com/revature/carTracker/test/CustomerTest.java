package com.revature.carTracker.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.revature.carTracker.model.Car;
import com.revature.carTracker.model.Customer;

public class CustomerTest {
	
	Customer customer;
	
	@Before
	public void setEmptyCustomer() throws Exception {
		customer = new Customer();
	}
	
	@After
	public void deleteCustomerObject() {
		customer = null;
	}

	@Test
	public void testCustomerCreated() {
		customer = new Customer(5, "Bill");
		assertNotNull(customer);
	}
	
	@Test
	public void testIdSet() {
		customer.setId(1);
		assertEquals(customer.getId(), 1);
	}
	
	@Test
	public void testNameSet() {
		customer.setName("New Customer");
		assertEquals(customer.getName(), "New Customer");
	}
}
