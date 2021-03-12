package business.collections;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import business.entities.Order;

public class OrderList implements Iterable<Order>, Serializable {
	private List<Order> orders = new LinkedList<Order>();

	/**
	 * adds order to list of orders.
	 * 
	 * @param order order to add to the list
	 * @return true what order added
	 */
	public boolean addOrder(Order order) {
		orders.add(order);
		return true;
	}

	/**
	 * Removes order of a specific product
	 * 
	 * @param productId id of product order to remove
	 * @return the removed order item if it was removed, else null
	 */
	public Order removeOrder(String productId) {
		for (ListIterator<Order> iterator = orders.listIterator(); iterator.hasNext();) {
			Order order = iterator.next();
			String id = order.getProduct().getProductId();
			if (id.equals(productId)) {
				iterator.remove();
				return order;
			}
		}
		return null;
	}

	@Override
	public Iterator<Order> iterator() {
		return orders.iterator();
	}

}
