package business.entities;

import java.util.Calendar;

public class Order {
	private Product product;
	private int quantity;
	private Calendar date;

	/**
	 * Stores product, quantity and date.
	 * 
	 * @param product  product ordered
	 * @param quantity quantity ordered
	 * @param date     date ordered
	 */
	public Order(Product product, int quantity, Calendar date) {
		this.product = product;
		this.quantity = quantity;
		this.date = date;
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
		return "Order [product id=" + this.product.getProductId() + ", product name=" + this.product.getProductName()
				+ ", quantity=" + this.quantity + "]";
	}

}
