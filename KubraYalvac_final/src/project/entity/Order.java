package project.entity;

import java.util.ArrayList;
import java.util.List;

/** 
 * siparişin hangi müşteriye ait olduğunu bilmemiz için customerName 
 * verilen siparişlerin tutulması için foodList & drinkList oluşturulur
 */

public class Order {

	private String customerName;
	private List<Food> foodList = new ArrayList<Food>(); 
	private List<Drink> drinkList = new ArrayList<Drink>();

	public Order(String name) {
		this.customerName = name;
	}

	public String getCustomerName() {
		return customerName;
	}

	public List<Food> getFoodList() {
		return foodList;
	}

	public List<Drink> getDrinkList() {
		return drinkList;
	}
	
	/**
	 * addProduct metodunda sipariş edilebilecek ürün sınırlandırılır
	 * @exception ArrayStoreException belirlenen sınırdan fazla ürün eklenmeye çalışıldığında fırlatılır
	 */
	public void addProduct(Food food) {
		if (foodList.size() >= 2) {
			throw new ArrayStoreException();
		} else {
			foodList.add(food);
		}
	}

	public void addProduct(Drink drink) {
		if (drinkList.size() >= 1) {
			throw new ArrayStoreException();
		} else {
			drinkList.add(drink);
		}
	}
}
