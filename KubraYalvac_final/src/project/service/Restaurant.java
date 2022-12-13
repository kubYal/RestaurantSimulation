package project.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import project.entity.Customer;
import project.entity.Order;
import project.entity.Table;
/**
 * nesneler listede tutulur
 * sıra gerektirenler için Queue
 */
public class Restaurant {

	private Queue<Customer> waitingCustomers = new LinkedList<>();
	private List<Customer> customers = new ArrayList<>();
	private List<Table> tables = new ArrayList<>();
	private List<Waiter> waiters = new ArrayList<>();
	private List<Chef> chefs = new ArrayList<>();
	private Queue<Order> ordersToPrepare = new LinkedList<>();
	private Queue<Order> ordersPrepared = new LinkedList<>();

	public Queue<Customer> getWaitingCustomers() {
		return waitingCustomers;
	}

	public void setWaitingCustomers(Queue<Customer> waitingCustomers) {
		this.waitingCustomers = waitingCustomers;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public List<Waiter> getWaiters() {
		return waiters;
	}

	public void setWaiters(List<Waiter> waiters) {
		this.waiters = waiters;
	}

	public List<Chef> getChefs() {
		return chefs;
	}

	public void setChefs(List<Chef> chefs) {
		this.chefs = chefs;
	}

	public Queue<Order> getOrdersToPrepare() {
		return ordersToPrepare;
	}

	public void setOrdersToPrepare(Queue<Order> ordersToPrepare) {
		this.ordersToPrepare = ordersToPrepare;
	}

	public Queue<Order> getOrdersPrepared() {
		return ordersPrepared;
	}

	public void setOrdersPrepared(Queue<Order> ordersPrepared) {
		this.ordersPrepared = ordersPrepared;
	}
	
	/** 
	 * @return t müşterisi olmayan -boş- masa döndürür
	 */
	public Table getEmptyTable() {
		for (Table t : this.tables) {
			if (t.getCurrentCustomer() == null) {
				return t;
			}
		}
		return null;
	}
	
	/**
	 * @return c henüz sipariş vermemiş olan müşteri döndürür
	 */
	public Customer getUnorderedCustomers() {
		for (Customer c : this.customers) {
			if (c.getOrder() != null) {
				return c;
			}
		}
		return null;
	}
}
