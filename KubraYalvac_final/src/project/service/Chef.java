package project.service;

import project.entity.Order;

public class Chef extends Thread {

	private String name;
	private Restaurant restaurant;
	private Boolean isBusy;

	public Chef(String name, Restaurant restaurant) {
		this.name = name;
		this.restaurant = restaurant;
		restaurant.getChefs().add(this);
		this.isBusy = false;
	}

	/**
	 * @return şef meşgulse isBusy döndürür
	 */
	public Boolean getIsBusy() {
		return isBusy;
	}

	/**
	 * 	prepareOrder metoduyla garsondan gelen siparişleri sırasıyla hazırlar
	 */
	public void prepareOrder() {
		Order orderToPrepare = getOrderFromQueue(this.restaurant);
		if (orderToPrepare == null) {
			return;
		}
		new OrderPrepare(orderToPrepare).run();
	}

	private Order getOrderFromQueue(Restaurant restaurant) {
		return restaurant.getOrdersToPrepare().poll();
	}

	private class OrderPrepare implements Runnable {

		private Order order;

		public OrderPrepare(Order order) {
			this.order = order;
		}
		
		/**
		 * sırasıyla siparişleri hazırlarken hazırlanma süresi boyunca sleep çağrısı ile bekletilir
		 * hazırlanma süresi bitenler getOrdersPrepared listesine eklenir
		 */
		public void run() {
			isBusy = true;
			System.out.println(name + " started to prepare the food of a " + order.getCustomerName());
			int numberOfFoods = order.getFoodList().size();
			int numberOfDrinks = order.getDrinkList().size();
			long a = (numberOfFoods * 1000) + (numberOfDrinks * 500);
			try {
				Thread.sleep(a);
			} catch (InterruptedException e) {
				System.err.println(name + " had an error preparing " + order.getCustomerName() + "'s order!");
			}
			System.out.println(name + " prepared the " + order.getCustomerName() + "'s order");
			restaurant.getOrdersPrepared().add(order);
			isBusy = false;
			System.out.println(order.getCustomerName() + "'s order is waiting to be delivered.");
		}
	}
}
