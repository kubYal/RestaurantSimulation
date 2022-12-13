package project.entity;

import java.util.List;

import project.exception.NullOrderException;

/**
 * @param name         String tipinde müşteri ismi tutar
 * @param currentTable müşterinin oturmakta olduğu masayı belirtir
 * @param order        müşterinin oluşturduğu siparişleri tutar
 * 
 */
public class Customer extends Thread {

	private String name;
	private Table currentTable;
	private Order order;

	public Customer(String name) {
		this.name = name;
	}

	public String getCustomerName() {
		return name;
	}

	public Order getOrder() {
		return order;
	}

	/**
	 * sitAroundTable metodu müşterinin boş bir masaya oturmasını gerçekleştirir
	 * masaya oturan müşteri chooseOrder metot çağrısıyla siparişine karar verir
	 */
	public void sitAroundTable(Table table) {
		table.sitRoundTable(this);
		this.currentTable = table;
		System.out.println(this.name + " sat at table " + table.getTableNo());
		this.chooseOrder();
	}

	/**
	 * chooseOrder metodunda currentTable boşsa exception fırlatır/müşteri masaya
	 * oturmadan sipariş veremez currentTable objesi boş değilse yeni bir order
	 * oluşturulur Math.random üzerinden uygulanan işlemle 2'den fazla yiyecek,
	 * 1'den fazla içecek seçmemesi sağlanır
	 */
	private void chooseOrder() {
		if (currentTable == null) {
			throw new IllegalArgumentException("You cannot order without sitting at the table.");
		} else {
			this.order = new Order(this.name);
			int numberOfFoods = (int) (Math.random() * 2) + 1;
			for (int i = 0; i < numberOfFoods; i++) {
				long consumeTime = (long) (Math.random() * 100L) + 1L;
				Food food = new Food("Food-" + i, consumeTime);
				order.addProduct(food);
			}

			int numberOfDrinks = (int) (Math.random() + 0.5);
			for (int i = 0; i < numberOfDrinks; i++) {
				long consumeTime = (long) (Math.random() * 50L) + 1L;
				Drink drink = new Drink("Drink-" + i, consumeTime);
				order.addProduct(drink);
			}
		}
		System.out.println(this.name + " created an order.");
	}

	/**
	 * 
	 * @return müşterinin seçtiği siparişleri döndürür
	 * @throws NullOrderException sipariş seçilmediği durumda fırlatılır
	 * 
	 * seçilen sipariş garsona iletilir ve iletildikten sonra temizlenmesi için null atanır
	 */
	public Order giveOrder() throws NullOrderException {
		if (order == null) {
			throw new NullOrderException();
		} else {
			Order result = this.order;
			this.order = null;
			System.out.println( this.name + " placed an order.");
			return result;
		}
	}

	/**
	 * @param order siparişlerin tüketilme süresini bilmek için tutulur 
	 * 
	 * sipariş müşterinin masasına gelir ve müşteri siparişlerin tüketilme süresi bittiğinde masadan kalkar
	 */
	public void eatAndLeave(Order order) {
		System.out.println("The order of the " + this.name + " came to her table.");
		new EatAndLeave(this, order).run();
	}

	private class EatAndLeave implements Runnable {

		Customer customer;
		Order order;

		public EatAndLeave(Customer customer, Order order) {
			this.customer = customer;
			this.order = order;
		}

		public void run() {
			List<Food> foodList = order.getFoodList();
			List<Drink> drinkList = order.getDrinkList();
			consumeProducts(foodList, drinkList);
			customer.currentTable.setCurrentCustomer(null);
			customer.currentTable = null;
			System.out.println( customer.name + " consumed her order and left the table.");
		}

		/**
		 * 
		 * müşteri siparişlerinin consumeTime'ı kadar masada bekletilir consumeTime
		 * bitmeden kalkarsa exception fırlatılır
		 */
		private void consumeProducts(List<Food> foods, List<Drink> drinks) {
			
			foods.forEach(f -> {
				try {
					sleep(f.getConsumeTime()); 
				} catch (InterruptedException e) {
					System.err.println("Create error while customer is eating");
				}
			});
			drinks.forEach(d -> {
				try {
					sleep(d.getConsumeTime()); 
				} catch (InterruptedException e) {
					System.err.println("Create error while customer is drinking!");
				}
			});
		}

	}
}
