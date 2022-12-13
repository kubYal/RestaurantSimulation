package project.entity;

import project.service.Restaurant;

public class Table {

	private int tableNo;
	private Restaurant restaurant;
	private Customer currentCustomer;

	public Table(int tableNo, Restaurant restaurant) {
		this.tableNo = tableNo;
		this.restaurant = restaurant;
		restaurant.getTables().add(this);
	}

	public int getTableNo() {
		return tableNo;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	/**
	 * sitRoundTable
	 * müşterinin boş masaya oturmasını, 
	 * masa doluysa exception fırlatmasını sağlayan metot
	 */
	public void sitRoundTable(Customer customer) { 
		if (!isTableEmpty()) { 
			throw new IllegalArgumentException("No other customers can sit when the table is full.");
		}
		this.currentCustomer = customer;
	}
	/**
	 * leaveTable masayı boşaltmayı sağlayan metot
	 */
	public void leaveTable() { 
		if (!isTableEmpty()) {
			this.currentCustomer = null;
		}
		throw new IllegalArgumentException("The table is already empty.");
	}
	
	private boolean isTableEmpty() { 
		if (currentCustomer == null) {
			return true;
		} else {
			return false;
		}
	}

}
