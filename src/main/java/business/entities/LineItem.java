package business.entities;

import java.io.Serializable;

public class LineItem implements Serializable {
    private static final long serialVersionUID = 1L;
    // Fields
    private Product product;
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
