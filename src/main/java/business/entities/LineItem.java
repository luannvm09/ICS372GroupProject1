package business.entities;

import java.io.Serializable;

/**
 * LineItem class that represents a product being bought in a transaction. The LineItem keeps track
 * of the product, quantity and lineTotal.
 */
public class LineItem implements Serializable {
    private static final long serialVersionUID = 1L;
    // product being purchased in transaction
    private Product product;
    // quantity of product being ordered
    private int quantity;
    private double lineTotal;

    /**
     * Constructor
     * 
     * @param product   The product associated with the line item
     * @param quantity  The quantity of product
     * @param lineTotal The cost of the line item (product price * quantity)
     */
    public LineItem(Product product, int quantity, double lineTotal) {
        this.product = product;
        this.quantity = quantity;
        this.lineTotal = lineTotal;
    }

    /**
     * Getter for product
     * 
     * @return product
     */
    public Product getProduct() {
        return this.product;
    }

    /**
     * Setter for product
     * 
     * @param product Product object to assign to product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Getter for quantity
     * 
     * @return The quantity in the LineItem
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Setter for quantity
     * 
     * @param quantity the quantity for the line item
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter for lineTotal
     * 
     * @return the total cost of the line item
     */
    public double getLineTotal() {
        return this.lineTotal;
    }

    /**
     * Setter for lineTotal
     * 
     * @param lineTotal the total cost of the line item
     */
    public void setLineTotal(double lineTotal) {
        this.lineTotal = lineTotal;
    }
}
