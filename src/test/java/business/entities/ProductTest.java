package business.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {
    private Product product1;
    private Product product2;
    @Test
    public void testEquals() {
        product1 = new Product("Tomato", "1", 1, 1, 9.99);
        product2 = new Product("Tomato", "1", 1, 1, 9.99);

        assertEquals(product1, product2);
    }
}