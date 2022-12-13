package project.main;

import java.util.ArrayList;
import java.util.List;

import project.entity.Customer;
import project.entity.Table;
import project.service.Chef;
import project.service.Restaurant;
import project.service.Waiter;

/**
 * simülasyon gerçekleştirme
 */

public class Main {

	private final static Restaurant restaurant = new Restaurant();
	private static List<Customer> customerList = createCustomers(5);
	private static long startTime = System.currentTimeMillis();

	public static void main(String[] args) {
		createTables(5);
		createChef(2);
		Waiter waiter = new Waiter("The Waiter", restaurant);
		Customer customer = new Customer("Customer");
		restaurant.getWaitingCustomers().add(customer);

		while (true) {
			sendNewCustomerToRestaurant(startTime);
			waiter.getCustomerFromQueueAndSitAroundTable();
			waiter.getOrder();
			chefsControlAndPrepareOrders();
			waiter.sendOrderToCustomer();
		}

	}
	
	/**
	 * belli aralıklarla yeni müşteriler gelmesini sağlayan metot
	 */
	private static void sendNewCustomerToRestaurant(long startTime) {
		long nextCustomerArrivalTime = (long) (Math.random() * 100L) + 1L;
		long now = System.currentTimeMillis();
		if ((now - nextCustomerArrivalTime) > nextCustomerArrivalTime) {
			if (customerList.isEmpty()) {
				return;
			}
			Customer customer = customerList.remove(0);
			restaurant.getWaitingCustomers().add(customer);
		}
	}
	
	/**
	 * şef meşgul değilse gelen siparişi hazırlamaya başlar
	 */
	private static void chefsControlAndPrepareOrders() {
		restaurant.getChefs().forEach(chef -> {
			if (!chef.getIsBusy()) {
				chef.prepareOrder();
			}
		});
	}

	public static void createTables(int numberOfTables) {
		for (int i = 0; i < numberOfTables; i++) {
			new Table(i, restaurant);
		}
	}

	public static void createChef(int numberOfChefs) {
		for (int i = 0; i < numberOfChefs; i++) {
			new Chef("Chef-" + i, restaurant);
		}
	}

	public static List<Customer> createCustomers(int numberOfCustomers) {
		List<Customer> customerList = new ArrayList<>();
		for (int i = 0; i <= numberOfCustomers; i++) {
			customerList.add(new Customer("Customer-" + i));
		}
		return customerList;
	}
}
