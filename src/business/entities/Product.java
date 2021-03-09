package business.entities;

import java.io.Serializable;

// FIXME this is the beginning of the Product class, the product id needs to be unique,
// I'm not sure if we wanted to implement that in the GroceryStore Facade, or here in Product
// I've written some classes like hashCode() and equals() based on the assumption that product's ID
// will be unique
public class Product implements Serializable{	
	private static final long serialVersionUID = 1L;
    private String productName;
    private String productId;
    private int stockOnHand;
    private final int reorderLevel;
    
    private double currentPrice;
    
    /**
     * The constructor for product will take the name, 
     * @param name
     * @param reorderLevel
     * @param price
     */
    public Product(String name, String id, int reorderLevel, int initialStockOnHand, double price) {
    	productName = name;
    	productId = id;
    	stockOnHand = initialStockOnHand;
    	this.reorderLevel = reorderLevel;
    	currentPrice = price;
    }
 
    /**
       * Getters and Setters
     */
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getStockOnHand() {
		return stockOnHand;
	}

	public void setStockOnHand(int stockOnHand) {
		this.stockOnHand = stockOnHand;
	}

	public int getReorderLevel() {
		return reorderLevel;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
		System.out.println("Product: " + productName + "\tNew Price: " + currentPrice);
	}
	
	/**
	 *   Hash code method produces a unique hash code for each instance 
	 *   assuming the products ID's are all unique
	 *   @param none
	 *   @return int - unique hash code for each instance
	 */
	@Override
	public int hashCode() {
		int prime = 37;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}
	
	/**
	 *  An equals method that checks to see if two products are equal. 
	 *  This equals method is based on the product ID and assumes that each
	 *  product has a unique ID.
	 *  @param Object - the other book passed as object
	 *  @return boolean - true if books are equal, false if not  
	 */
	@Override
	public boolean equals(Object object) {
		if(this == object) {
			return true;
		}
		if(object == null) {
			return false;
		}
		if(getClass() != object.getClass()) {
			return false;
		}
		Product otherProduct = (Product)object;
		if(productId == null) {
			if(otherProduct.productId != null) {
				return false;
			}
		}
		if(!this.productId.equals(otherProduct.productId)) {
			return false;
		}
		return true;
	}
	

}
