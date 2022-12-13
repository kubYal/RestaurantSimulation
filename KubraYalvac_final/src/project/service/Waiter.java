package project.service;

import java.util.List;

import project.entity.Customer;
import project.entity.Order;
import project.entity.Table;
import project.exception.NullOrderException;

public class Waiter {

	private Restaurant restaurant;
	private String name;

	public Waiter(String name, Restaurant restaurant) {
		this.name = name;
		this.restaurant = restaurant;
	}
	
	/**
	 * boş masa varsa sıradaki müşterinin geçmesini sağlayan metot
	 * poll ile sıradaki müşteriyi alır ve sıradan çıkartır
	 */
	public void getCustomerFromQueueAndSitAroundTable() {
		Table emptyTable = restaurant.getEmptyTable();
		if (emptyTable == null) {
			System.err.println("No empty table!");
			return;
		}
		Customer newCustomer = restaurant.getWaitingCustomers().poll(); 
		if (newCustomer == null) {
			System.err.println("No customers waiting in line!");
			return;
		}
		newCustomer.sitAroundTable(emptyTable);
		this.restaurant.getCustomers().add(newCustomer);
		System.out.println( this.name + " sat the " + newCustomer.getCustomerName()
				+ " on his table.");

	}
	/**
	 * sipariş vermeyen müşterilerden sipariş alan metot
	 * siparişi alıp müşterinin order'ına ekler
	 * 
	 * @exception NullOrderException müşterinin siparişi olmadığı durumda gelişir
	 */
	public void getOrder() {
		Customer customer = this.restaurant.getUnorderedCustomers();
		if (customer == null) {
			System.err.println("No customers waiting the order!");
			return;
		}
		try {
			Order order = customer.giveOrder();
			this.restaurant.getOrdersToPrepare().add(order);
			System.out.println( this.name + " took the order of the "
					+ customer.getCustomerName());
		} catch (NullOrderException e) {
			System.err.println( this.name + " wanted to take the order of the "
					+ customer.getCustomerName() + " but the customer has not selected an order yet! ");
		}
	}
	
	/**
	 * garson, şef tarafından hazırlanmış olan siparişi sırayla müşteriye götürür
	 */
	public void sendOrderToCustomer() {
		Order preparedOrder = this.restaurant.getOrdersPrepared().poll();
		while (preparedOrder == null) {
			preparedOrder = this.restaurant.getOrdersPrepared().poll();
		}
		Customer customer = findOrdersCustomer(preparedOrder);
		System.out.println( this.name + " delivered the order of the "
				+ customer.getCustomerName());
		customer.eatAndLeave(preparedOrder);
	}
	
	/** 
	 * @return customer, order ile customer eşleşen müşterileri döndürür
	 */
	private Customer findOrdersCustomer(Order order) {
		List<Customer> customersInRestaurant = this.restaurant.getCustomers();
		Customer customer = customersInRestaurant.stream()
				.filter(c -> c.getCustomerName().equals(order.getCustomerName())).findFirst().get();
		return customer;
	}
}
