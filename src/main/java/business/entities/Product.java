package business.entities;

import java.io.Serializable;

/**
 * Product class which represents a product in the coop's inventory. New products are given a user
 * provided id and are not automatically generated.
 */
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	// Name of product
	private String productName;
	// Id of product
	private String productId;
	// Quantity of product in stock
	private int stockOnHand;
	// The quantity that triggers a reorder if stock is equal or less than this value
	private int reorderLevel;
	// Current price of product
	private double currentPrice;

	/**
	 * Constructor. Assigns instance variable to constructor parameters.
	 * 
	 * @param name
	 * @param reorderLevel
	 * @param price
	 */
	public Product(String name, String id, int reorderLevel, int initialStockOnHand, double price) {
		this.productName = name;
		this.productId = id;
		this.stockOnHand = initialStockOnHand;
		this.reorderLevel = reorderLevel;
		this.currentPrice = price;
	}

	/**
	 * Getter for product name
	 * 
	 * @return name of the product
	 */
	public String getProductName() {
		return this.productName;
	}

	/**
	 * Getter for product id
	 * 
	 * @return id of product
	 */
	public String getProductId() {
		return this.productId;
	}

	/**
	 * Getter for stockOnHand
	 * 
	 * @return current stock of product
	 */
	public int getStockOnHand() {
		return this.stockOnHand;
	}

	/**
	 * Setter for stockOnHand
	 * 
	 * @param stockOnHand new current stock for product
	 */
	public void setStockOnHand(int stockOnHand) {
		this.stockOnHand = stockOnHand;
	}

	/**
	 * Getter for reorderLevel
	 * 
	 * @return reorder quantity for product
	 */
	public int getReorderLevel() {
		return this.reorderLevel;
	}

	/**
	 * Getter for currentPrice
	 * 
	 * @return current price of product
	 */
	public double getCurrentPrice() {
		return this.currentPrice;
	}

	/**
	 * Setter for currentPrice
	 * 
	 * @param currentPrice new price for product
	 */
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	/**
	 * Hash code method produces a unique hash code for each instance assuming the products ID's are
	 * all unique
	 * 
	 * @param none
	 * @return unique hash code for each instance
	 */
	@Override
	public int hashCode() {
		int prime = 37;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}

	/**
	 * An equals method that checks to see if two products are equal. This equals method is based on
	 * the product ID and assumes that each product has a unique ID.
	 * 
	 * @param Object - the other book passed as object
	 * @return true if books are equal, false if not
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		Product otherProduct = (Product) object;
		if (productId == null) {
			if (otherProduct.productId != null) {
				return false;
			}
		}
		if (!this.productId.equals(otherProduct.productId)) {
			return false;
		}
		return true;
	}

	/**
	 * Product toString method
	 * 
	 * @return string representation of Product
	 */
	@Override
	public String toString() {
		return "Product name " + this.productName + "; id " + this.productId + "; stock on hand "
				+ this.stockOnHand + "; price $ " + this.currentPrice + "; reorder level "
				+ this.reorderLevel;
	}

}
