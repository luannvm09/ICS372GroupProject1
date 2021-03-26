package business.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;

public class Order implements Serializable {
	// Unique id of order
	private String orderId;
	// Product associated with the order
	private Product product;
	// Quantity being ordered
	private int quantity;
	// Order creation date
	private Calendar date;
	// Prefix with "R" instead of "O" because "O" looks like "0"
	private static final String ORDER_ID_PREFIX = "R";
	// id counter used to ensure unique order ids
	private static int idCounter = 0;

	/**
	 * Constructor. Generates a unique order id by prefixing the idcounter
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

	/**
	 * Getter for orderId
	 * 
	 * @return order id for the object
	 */
	public String getOrderId() {
		return this.orderId;
	}

	/**
	 * Getter for product
	 * 
	 * @return the Product associated with Order
	 */
	public Product getProduct() {
		return this.product;
	}

	/**
	 * Getter for quantity
	 * 
	 * @return the quantity of product being ordered
	 */
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 * Getter for date
	 * 
	 * @return order creation date
	 */
	public Calendar getDate() {
		return this.date;
	}

	/**
	 * Method for serializing idCounter used for generating unique ids
	 * 
	 * @param output output stream used to write the idCounter
	 * @throws IOException
	 */
	public static void save(ObjectOutputStream output) throws IOException {
		output.writeObject(idCounter);
	}

	/**
	 * Retrieves the serialized idCounter and assigns it to the static field
	 * 
	 * @param input input stream used to read the serialized data
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void retrieve(ObjectInputStream input)
			throws IOException, ClassNotFoundException {
		Order.idCounter = (int) input.readObject();
	}

	/**
	 * Order toString method
	 * 
	 * @return string representation of an Order
	 */
	@Override
	public String toString() {
		return "Order [ id = " + this.orderId + " product id=" + this.product.getProductId()
				+ ", product name=" + this.product.getProductName() + ", quantity=" + this.quantity
				+ "]";
	}

}
