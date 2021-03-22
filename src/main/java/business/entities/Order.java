package business.entities;

import java.util.Calendar;

public class Order {
	private String orderId;
	private Product product;
	private int quantity;
	private Calendar date;

	private static final String ORDER_ID_PREFIX = "0";
	private static int idCounter = 0;

	/**
	 * Stores product, quantity and date.
	 * 
	 * @param product  product ordered
	 * @param quantity quantity ordered
	 * @param date     date ordered
	 */
	public Order(Product product, int quantity, Calendar date) {
		this.orderId = ORDER_ID_PREFIX + ++idCounter;
		this.product = product;
		this.quantity = quantity;
		this.date = date;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public Product getProduct() {
		return this.product;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public Calendar getDate() {
		return this.date;
	}

	@Override
	public String toString() {
		return "Order [product id=" + this.product.getProductId() + ", product name="
				+ this.product.getProductName() + ", quantity=" + this.quantity + "]";
	}

}
