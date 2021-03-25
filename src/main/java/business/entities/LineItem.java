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

    public LineItem(Product product, int quantity, double lineTotal) {
        this.product = product;
        this.quantity = quantity;
        this.lineTotal = lineTotal;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getLineTotal() {
        return this.lineTotal;
    }

    public void setLineTotal(double lineTotal) {
        this.lineTotal = lineTotal;
    }


}
