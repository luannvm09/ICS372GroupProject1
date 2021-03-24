package business.facade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import business.entities.LineItem;
import business.entities.Member;
import business.entities.Order;
import business.entities.Product;
import business.entities.Transaction;
import business.entities.iterator.FilteredIterator;
import business.entities.iterator.SafeIterator;

public class Grocery implements Serializable {
	private static final long serialVersionUID = 1L;
	private Stock stock = new Stock();
	private MemberList members = new MemberList();
	private OrderList orders = new OrderList();
	private TransactionList transactions = new TransactionList();
	private static Grocery grocery;

	private class TransactionList implements Iterable<Transaction>, Serializable {
		private static final long serialVersionUID = 1L;

		private List<Transaction> transactions = new LinkedList<Transaction>();

		public boolean insertTransaction(Transaction transaction) {
			return this.transactions.add(transaction);
		}

		public Transaction getTransactionById(String id) {
			Iterator<Transaction> iterator = this.transactions.iterator();

			if (!iterator.hasNext()) {
				return null;
			}

			while (iterator.hasNext()) {
				Transaction transaction = iterator.next();

				if (transaction.getTransactionId().equals(id)) {
					return transaction;
				}
			}

			return null;
		}

		@Override
		public Iterator<Transaction> iterator() {
			return this.transactions.iterator();
		}

	}

	private class OrderList implements Iterable<Order>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Order> orders = new LinkedList<Order>();

		public Iterator<Order> getOrders() {
			return this.orders.iterator();
		}

		/**
		 * adds order to list of orders.
		 * 
		 * @param order order to add to the list
		 * @return true what order added
		 */
		public boolean addOrder(Order order) {
			return orders.add(order);
		}

		/**
		 * Removes order of a specific product
		 * 
		 * @param orderId id of order to be removed
		 * @return the removed order item if it was removed, else null
		 */
		public Order removeOrder(String orderId) {
			Iterator<Order> matchedOrders = new FilteredIterator<Order>(this.orders.iterator(),
					order -> order.getOrderId() == orderId);

			if (!matchedOrders.hasNext()) {
				return null;
			}

			Order order = matchedOrders.next();
			this.orders.remove(order);

			return order;
		}

		/**
		 * Checks whether an order with a given order id exists.
		 * 
		 * @param orderId the id of the order
		 * @return true iff the order exists
		 * 
		 */
		public Order search(String orderId) {
			for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext();) {
				Order order = (Order) iterator.next();
				if (order.getOrderId().equals(orderId)) {
					return order;
				}
			}
			return null;
		}

		@Override
		public Iterator<Order> iterator() {
			return this.orders.iterator();
		}

	}

	private class Stock implements Iterable<Product>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Product> products = new LinkedList<Product>();

		/**
		 * Checks whether a product with a given product id exists.
		 * 
		 * @param productId the id of the product
		 * @return true iff the product exists
		 * 
		 */
		public Product search(String productId) {
			for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
				Product product = iterator.next();
				if (product.getProductId().toLowerCase().equals(productId)) {
					return product;
				}
			}
			return null;
		}

		/**
		 * Removes a product from the stock
		 * 
		 * @param productId product id
		 * @return true if product could be removed
		 */
		public boolean removeProduct(String productId) {
			Product product = search(productId);
			if (product == null) {
				return false;
			} else {
				return products.remove(product);
			}
		}

		/**
		 * Adding a product into the stock
		 * 
		 * @param product the product to be added
		 * @return true iff the product could be added. Currently always true
		 */
		public boolean addProduct(Product product) {
			products.add(product);
			return true;
		}

		/**
		 * Returns an iterator to all products
		 * 
		 * @return iterator to the stock
		 */
		public Iterator<Product> iterator() {
			return products.iterator();
		}

		/**
		 * gives you a list of products whose name contains the specified string
		 * 
		 * @param name string of beginning of product name you want
		 * @return a list with products whose name starts with name
		 */
		public Iterator<Product> retrieveProductsByName(String name) {
			Iterator<Product> iterator = this.products.iterator();

			return new FilteredIterator<Product>(iterator, product -> product.getProductName()
					.toLowerCase().startsWith(name.toLowerCase()));
		}

		/**
		 * String form of the stock collection
		 * 
		 */
		public String toString() {
			return products.toString();
		}
	}

	private class MemberList implements Iterable<Member>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Member> members = new LinkedList<Member>();

		/**
		 * Checks whether a member with a given member id exists.
		 * 
		 * @param memberId the id of the member
		 * @return Member iff member exists. Otherwise null.
		 * 
		 */
		public Member search(String memberId) {
			for (Iterator<Member> iterator = members.iterator(); iterator.hasNext();) {
				Member member = iterator.next();
				if (member.getMemberId().equals(memberId)) {
					return member;
				}
			}
			return null;
		}

		/**
		 * Inserts a member into the collection
		 * 
		 * @param member the member to be inserted
		 * @return true iff the member could be inserted. Currently always true
		 */
		public boolean insertMember(Member member) {
			members.add(member);
			return true;
		}

		public Member removeMember(String memberId) {
			Member memberToRemove = search(memberId);

			if (memberToRemove == null) {
				return null;
			}

			this.members.remove(memberToRemove);
			return memberToRemove;
		}

		public Iterator<Member> iterator() {
			return members.iterator();
		}

		/**
		 * gives you a list of members whose name starts with specified string (case insensitive)
		 * 
		 * @param name string of beginning of member name you want
		 * @return a list with members whose name starts with name
		 */
		public Iterator<Member> retrieveMembersByName(String name) {
			// Convert prefix to lower case to avoid case sensitivity issues.
			String prefix = name.toLowerCase();
			Iterator<Member> iterator = this.members.iterator();
			return new FilteredIterator<Member>(iterator,
					member -> member.getMemberName().toLowerCase().startsWith(prefix));
		}

		/**
		 * String form of the collection
		 * 
		 */
		@Override
		public String toString() {
			return members.toString();
		}
	}

	/**
	 * Private for the singleton pattern creates the stock and member collection objects
	 */
	private Grocery() {

	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static Grocery instance() {
		if (grocery == null) {
			return grocery = new Grocery();
		} else {
			return grocery;
		}
	}

	/**
	 * 
	 * Member Functions
	 * 
	 */

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Result addMember(Request request) {
		Result result = new Result();
		String name = request.getMemberName();
		String address = request.getMemberAddress();
		String phoneNumber = request.getMemberPhoneNumber();
		Calendar joinDate = request.getDateJoined();
		double feePaid = request.getFeePaid();
		Member member = new Member(name, address, phoneNumber, joinDate, feePaid);
		if (members.insertMember(member)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setMemberFields(member);
			return result;
		}

		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	public Iterator<Result> getMembers() {
		return new SafeIterator<Member>(members.iterator(), SafeIterator.MEMBER);
	}


	public Result removeMemberByID(Request request) {
		Member removedMember = this.members.removeMember(request.getMemberId());

		Result result = new Result();

		// Member did not exist.
		if (removedMember == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}

		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setMemberFields(removedMember);
		return result;
	}

	/**
	 * Returns a SafeIterator of results for members that have names that contain
	 * request.getMemberName()
	 * 
	 * @param request the request object which contains specified member name
	 * @return an iterator of members whose name start with specified string
	 */
	public Iterator<Result> retrieveMembersByName(Request request) {
		Iterator<Member> filteredMembers =
				this.members.retrieveMembersByName(request.getMemberName());

		return new SafeIterator<Member>(filteredMembers, SafeIterator.MEMBER);
	}

	public Result retrieveMemberById(Request request) {
		String memberId = request.getMemberId();
		Member member = this.members.search(memberId);
		Result result = new Result();
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setMemberFields(member);
		return result;
	}

	/**
	 * Gets transactions of a member
	 * 
	 * @param memberId  memberId of member you want transactions of
	 * @param startDate start date you want transactions from(inclusive)
	 * @param endDate   end date of period you want transactions(inclusive)
	 * @return iterator of transaction items of member requested if memberId and dates are valid
	 */
	public Iterator<Result> getMembersTransactions(Request request) {
		Member member = members.search(request.getMemberId());
		if (member == null || request.getStartDate().after(request.getEndDate())) {
			// Return an empty iterator
			Iterator<Transaction> emptyIterator = (new LinkedList<Transaction>()).iterator();
			return new SafeIterator<Transaction>(emptyIterator, SafeIterator.TRANSACTION);
		}
		Iterator<Transaction> membersTransactions =
				member.getTransactionsOnDate(request.getStartDate(), request.getEndDate());
		return new SafeIterator<Transaction>(membersTransactions, SafeIterator.TRANSACTION);
	}

	/**
	 * Product Functions
	 */

	/**
	 * 
	 * @param instance request DTO
	 * @return
	 */
	public Result addProduct(Request instance) {
		String productName = instance.getProductName();
		String productId = instance.getProductId();
		int productStock = instance.getStockOnHand();
		int reorderQuantity = instance.getReorderLevel();
		double productPrice = instance.getCurrentPrice();
		Product newProduct =
				new Product(productName, productId, reorderQuantity, productStock, productPrice);
		this.stock.addProduct(newProduct);

		// Immediately create order for double of the reorder quantity
		int orderQuantity = reorderQuantity * 2;
		Order initialOrder = new Order(newProduct, orderQuantity, Calendar.getInstance());
		boolean success = this.orders.addOrder(initialOrder);

		Result result = new Result();
		if (!success) {
			result.setResultCode(Result.OPERATION_FAILED);
			return result;
		}

		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setProductFields(newProduct);
		return result;
	}

	public Iterator<Result> retrieveAllProducts() {
		Iterator<Product> iterator = this.stock.iterator();
		return new SafeIterator<Product>(iterator, SafeIterator.PRODUCT);
	}

	/**
	 * gets an iterator of products whose name start with specified string
	 * 
	 * @param name the start of the product name you want
	 * @return an iterator of products whose name start with specified string
	 */
	public Iterator<Result> retrieveProductsByName(Request request) {
		Iterator<Product> filteredProducts =
				this.stock.retrieveProductsByName(request.getProductName());

		return new SafeIterator<Product>(filteredProducts, SafeIterator.PRODUCT);
	}

	public Result updateProductPrice(Request request) {
		Result result = new Result();
		double newPrice = request.getCurrentPrice();
		Product product = this.stock.search(request.getProductId());
		product.setCurrentPrice(newPrice);
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setProductFields(product);
		return result;
	}

	/**
	 * Order Helpers
	 */

	/**
	 * @param request
	 * @return
	 */
	public Iterator<Result> getOutstandingOrders() {
		Iterator<Order> iterator = this.orders.getOrders();

		return new SafeIterator<Order>(iterator, SafeIterator.ORDER);
	}

	public Result processShipment(Request request) {
		Result result = new Result();
		String orderId = request.getOrderId();
		Order order = this.orders.search(orderId);
		// Order was not found
		if (order == null) {
			result.setResultCode(Result.ORDER_NOT_FOUND);
			return result;
		}
		// Order was found, update stock with order quantity + stock on hand
		Product product = order.getProduct();
		int newStock = product.getStockOnHand() + order.getQuantity();
		product.setStockOnHand(newStock);
		// Order no longer outstanding, remove from orders
		this.orders.removeOrder(orderId);
		result.setProductFields(product);
		return result;
	}

	/**
	 * Transaction Helpers
	 */
	public Result addTransactionLineItem(Request request) {
		Result result = new Result();

		String transactionId = request.getTransactionId();
		String productId = request.getProductId();
		int checkoutQuantity = request.getCheckoutQuantity();
		Transaction currentTransaction = this.transactions.getTransactionById(transactionId);
		Product currentProduct = this.stock.search(productId);
		double lineTotal = currentProduct.getCurrentPrice() * checkoutQuantity;

		if (currentTransaction == null) {
			result.setResultCode(Result.TRANSACTION_NOT_FOUND);
			return result;
		}

		if (currentProduct == null) {
			result.setResultCode(Result.PRODUCT_NOT_FOUND);
			return result;
		}

		currentTransaction.addLineItem(currentProduct, checkoutQuantity);
		double checkoutCost = currentTransaction.getTotalCost();
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setLineTotal(lineTotal);
		result.setCheckoutTotal(checkoutCost);
		return result;
	}

	/**
	 * Creates a new transaction and returns the id of the new transaction to caller.
	 * 
	 * @return
	 */
	public Result beginTransaction() {
		Transaction createdTransaction = new Transaction();
		this.transactions.insertTransaction(createdTransaction);

		Result result = new Result();
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setTransactionId(createdTransaction.getTransactionId());
		return result;
	}

	/**
	 * Ends transaction by adding the Transaction object to the corresponding member's transaction
	 * instance variable
	 * 
	 * @param request
	 * @return
	 */
	public Result endTransaction(Request request) {
		Result result = new Result();
		String transactionId = request.getTransactionId();
		Transaction transaction = this.transactions.getTransactionById(transactionId);
		String memberId = request.getMemberId();
		Member member = this.members.search(memberId);
		// Member wasn't found, cannot add transaction to their transactions instance variable
		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}

		// Add the transaction to member
		member.addNewUserTransaction(transaction);
		// Make any necessary orders for stock that hits reorder level
		this.verifyStock(transaction);
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setTransactionFields(transaction);
		return result;
	}

	private void verifyStock(Transaction transaction) {
		List<LineItem> lineItems = transaction.getLineItems();
		Iterator<LineItem> iterator = lineItems.iterator();

		while (iterator.hasNext()) {
			LineItem lineItem = iterator.next();
			Product product = lineItem.getProduct();
			int lineItemQuantity = lineItem.getQuantity();
			int stockOnHand = product.getStockOnHand();
			int reorderLevel = product.getReorderLevel();
			// If quantity in stock is less than or equal to reorder level, create new order
			if (stockOnHand - lineItemQuantity <= reorderLevel) {
				int orderQuantity = reorderLevel * 2;
				Order restockOrder = new Order(product, orderQuantity, Calendar.getInstance());
				this.orders.addOrder(restockOrder);
			}
		}
		return;
	}

	/**
	 * Retrieve the product by product Id
	 * 
	 * @param request
	 * @return result
	 */

	public Result retrieveProductsById(Request request) {
		String productId = request.getProductId();
		Product product = this.stock.search(productId);
		Result result = new Result();
		if (product == null) {
			result.setResultCode(Result.PRODUCT_NOT_FOUND);
			return result;
		}
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setProductFields(product);
		return result;
	}

}

