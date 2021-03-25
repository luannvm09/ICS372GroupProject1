package business.facade;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import business.tests.AutomatedTester;

/**
 * Grocery facade class that encapsulates the complexity of the various functions provided. This
 * class follows a singleton pattern and acts as the sole contact point for the user interface.
 */
public class Grocery implements Serializable {
	private static final long serialVersionUID = 1L;
	// The coop's stock of products
	private Stock stock = new Stock();
	// The coop's members
	private MemberList members = new MemberList();
	// The coop's outstanding product orders
	private OrderList orders = new OrderList();
	// The coop's member transactions
	private TransactionList transactions = new TransactionList();
	// singleton static instance
	private static Grocery grocery;

	/**
	 * A custom collection of Transaction objects. Implemented with a LinkedList.
	 */
	private class TransactionList implements Iterable<Transaction>, Serializable {
		private static final long serialVersionUID = 1L;
		// LinkedList adaptee
		private List<Transaction> transactions = new LinkedList<Transaction>();

		/**
		 * Insert transaction into list of transactions.
		 * 
		 * @param transaction transaction to add to the list.
		 * @return true iff transaction is successfully added
		 */
		public boolean insertTransaction(Transaction transaction) {
			return this.transactions.add(transaction);
		}

		/**
		 * Retrieve a transaction object by it's id
		 * 
		 * @param id id of desired transaction
		 * @return transaction with the specified id if it exists. Otherwise, null.
		 */
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

		/**
		 * TransactionList iterator function.
		 * 
		 * @return iterator used to iterate through all transactions stored in list
		 */
		@Override
		public Iterator<Transaction> iterator() {
			return this.transactions.iterator();
		}
	}

	/**
	 * A custom collection of Orders. Implemented with a linked list
	 */
	private class OrderList implements Iterable<Order>, Serializable {
		private static final long serialVersionUID = 1L;
		// LinkedList adaptee used to store Order objects
		private List<Order> orders = new LinkedList<Order>();

		/**
		 * get all outstanding product orders
		 * 
		 * @return iterator of all outstanding orders
		 */
		public Iterator<Order> getOutstandingOrders() {
			return this.orders.iterator();
		}

		/**
		 * Adds order to list of orders.
		 * 
		 * @param order order to add to the list
		 * @return true iff order added
		 */
		public boolean addOrder(Order order) {
			return orders.add(order);
		}

		/**
		 * Removes order from list specified by an order id
		 * 
		 * @param orderId id of order to be removed
		 * @return the removed order item if it was removed, else null
		 */
		public Order removeOrder(String orderId) {
			Iterator<Order> matchedOrders = new FilteredIterator<Order>(this.iterator(),
					order -> order.getOrderId().equals(orderId));
			// Order with id doesn't exist. Return null.
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

		/**
		 * OrderList iterator() method
		 * 
		 * @return iterator of all orders in list
		 */
		@Override
		public Iterator<Order> iterator() {
			return this.orders.iterator();
		}

	}

	/**
	 * A custom collection of Product objects. Implemented with linked list
	 */
	private class Stock implements Iterable<Product>, Serializable {
		private static final long serialVersionUID = 1L;
		// LinkedList adaptee
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
		 * Get products whose name start with specified string
		 * 
		 * @param name prefix of product name
		 * @return a list with products whose name starts with name
		 */
		public Iterator<Product> retrieveProductInfo(String name) {
			Iterator<Product> iterator = this.products.iterator();
			return new FilteredIterator<Product>(iterator, product -> product.getProductName()
					.toLowerCase().startsWith(name.toLowerCase()));
		}
	}

	/**
	 * Custom collection of Member objects. Implemented with linked list.
	 */
	private class MemberList implements Iterable<Member>, Serializable {
		private static final long serialVersionUID = 1L;
		// LinkedList adaptee
		private List<Member> members = new LinkedList<Member>();

		/**
		 * Checks whether a member with a given member id exists.
		 * 
		 * @param memberId the id of the member
		 * @return Member iff member exists. Otherwise null.
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

		/**
		 * Remove member with specified id from list of members.
		 * 
		 * @param memberId id of member to remove
		 * @return member that was removed. Otherwise null if member did not exist.
		 */
		public Member removeMember(String memberId) {
			Member memberToRemove = search(memberId);
			if (memberToRemove == null) {
				return null;
			}
			this.members.remove(memberToRemove);
			return memberToRemove;
		}

		/**
		 * Member iterator method
		 * 
		 * @return iterator of all members held in linked list
		 */
		@Override
		public Iterator<Member> iterator() {
			return members.iterator();
		}

		/**
		 * gives you a list of members whose name starts with specified string (case insensitive)
		 * 
		 * @param name string of beginning of member name you want
		 * @return a list with members whose name starts with name
		 */
		public Iterator<Member> retrieveMemberInfo(String name) {
			// Convert prefix to lower case to avoid case sensitivity issues.
			String prefix = name.toLowerCase();
			Iterator<Member> iterator = this.members.iterator();
			return new FilteredIterator<Member>(iterator,
					member -> member.getMemberName().toLowerCase().startsWith(prefix));
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
	 * Add a member to members
	 * 
	 * @param request Request object containing member fields
	 * @return Result with member fields filled with created Member
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
		result.setMemberFields(member);
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * Get a read only list of all coop members
	 * 
	 * @return SafeIterator of Result objects with filled member fields
	 */
	public Iterator<Result> getMembers() {
		return new SafeIterator<Member>(members.iterator(), SafeIterator.MEMBER);
	}


	/**
	 * Remove a member from members
	 * 
	 * @param request Request object containing member id of member to remove
	 * @return Result object with member fields filled with removed Member object. If member did not
	 *         exist, return a result with appropriate result code.
	 */
	public Result removeMember(Request request) {
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
	 * Get a read only list of results for members that have names that contain
	 * request.getMemberName()
	 * 
	 * @param request the request object which contains specified member name
	 * @return an iterator of members whose name start with specified string
	 */
	public Iterator<Result> retrieveMemberInfo(Request request) {
		Iterator<Member> filteredMembers = this.members.retrieveMemberInfo(request.getMemberName());
		return new SafeIterator<Member>(filteredMembers, SafeIterator.MEMBER);
	}

	/**
	 * Find member with a member id
	 * 
	 * @param request Request object with member id field set
	 * @return Result with member fields filled with found member. If does not exist, return result
	 *         with appropriate result code.
	 */
	public Result searchMembership(Request request) {
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
	 * Add a product to stock
	 * 
	 * @param instance Request DTO filled with product fields
	 * @return Result object with filled product fields if successfully added. Otherwise, Result
	 *         object with appropriate result code.
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

	/**
	 * Get read only list of products in stock
	 * 
	 * @return SafeIterator of all products in stock
	 */
	public Iterator<Result> getProducts() {
		Iterator<Product> iterator = this.stock.iterator();
		return new SafeIterator<Product>(iterator, SafeIterator.PRODUCT);
	}

	/**
	 * Return readonly list of products whose name start with specified string
	 * 
	 * @param name the start of the product name you want
	 * @return an iterator of products whose name start with specified string
	 */
	public Iterator<Result> retrieveProductInfo(Request request) {
		Iterator<Product> filteredProducts =
				this.stock.retrieveProductInfo(request.getProductName());
		return new SafeIterator<Product>(filteredProducts, SafeIterator.PRODUCT);
	}

	/**
	 * Change price of a product in stock
	 * 
	 * @param request Request object filled with product id of product that needs price changed
	 * @return Result object with filled product fields
	 */
	public Result changePrice(Request request) {
		Result result = new Result();
		double newPrice = request.getCurrentPrice();
		Product product = this.stock.search(request.getProductId());
		product.setCurrentPrice(newPrice);
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setProductFields(product);
		return result;
	}

	/**
	 * Get all outstanding orders
	 * 
	 * @return SafeIterator of outstanding orders
	 */
	public Iterator<Result> getOutstandingOrders() {
		Iterator<Order> iterator = this.orders.getOutstandingOrders();

		return new SafeIterator<Order>(iterator, SafeIterator.ORDER);
	}

	/**
	 * Process an outstanding order and update product stock
	 * 
	 * @param request Request object with order id filled.
	 * @return Result object with product fields filled from product that had its stock updated
	 */
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
		result.setResultCode(Result.OPERATION_COMPLETED);
		return result;
	}

	/**
	 * Add a LineItem to a transaction.
	 * 
	 * @param request Request object containing transaction id, product id and checkout quantity
	 * @return Result contains line total for the new LineItem and the current transaction total. If
	 *         transaction or product does not exist, a Result with appropriate result code will be
	 *         returned.
	 */
	public Result addTransactionLineItem(Request request) {
		Result result = new Result();
		String transactionId = request.getTransactionId();
		String productId = request.getProductId();
		int checkoutQuantity = request.getCheckoutQuantity();
		Transaction currentTransaction = this.transactions.getTransactionById(transactionId);
		Product currentProduct = this.stock.search(productId);
		if (currentTransaction == null) {
			result.setResultCode(Result.TRANSACTION_NOT_FOUND);
			return result;
		}
		if (currentProduct == null) {
			result.setResultCode(Result.PRODUCT_NOT_FOUND);
			return result;
		}
		double lineTotal = currentProduct.getCurrentPrice() * checkoutQuantity;
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
	 * @return Result containing transaction id for the created transaction
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
	 * @param request Request object with transaction id and member id.
	 * @return Result object with filled transaction fields. If member does not exist, a Result
	 *         object with appropriate result code will be returned.
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
		this.updateStock(transaction);
		result.setResultCode(Result.OPERATION_COMPLETED);
		result.setTransactionFields(transaction);
		return result;
	}

	/**
	 * Update stock after processing a transaction. Creates orders for products that hit their
	 * reorder quantities.
	 * 
	 * @param transaction Transaction created from a checkout.
	 */
	private void updateStock(Transaction transaction) {
		List<LineItem> lineItems = transaction.getLineItems();
		Iterator<LineItem> iterator = lineItems.iterator();
		while (iterator.hasNext()) {
			LineItem lineItem = iterator.next();
			Product product = lineItem.getProduct();
			int lineItemQuantity = lineItem.getQuantity();
			int stockOnHand = product.getStockOnHand();
			int reorderLevel = product.getReorderLevel();
			int newStockOnHand = stockOnHand - lineItemQuantity;
			product.setStockOnHand(newStockOnHand);
			// If quantity in stock is less than or equal to reorder level, create new order
			if (newStockOnHand <= reorderLevel) {
				int orderQuantity = reorderLevel * 2;
				Order restockOrder = new Order(product, orderQuantity, Calendar.getInstance());
				this.orders.addOrder(restockOrder);
				// Print a message informing user that a restock order was created
				System.out.println("\nCreating restock order for " + product.getProductName());
				System.out.println("Order:");
				System.out.println(" - Order ID: " + restockOrder.getOrderId());
				System.out.println(" - Order Quantity: " + restockOrder.getQuantity());
				System.out.println(" - Order Product: " + product.getProductName());
			}
		}
		return;
	}

	/**
	 * Retrieve a product by its product Id
	 * 
	 * @param request Request object with product id filled
	 * @return Result object filled with product fields. If product does not exist, a Result object
	 *         is returned with appropriate result code.
	 */
	public Result searchProduct(Request request) {
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

	/**
	 * Deserialize saved grocery data
	 * 
	 * @return instance of Grocery if successful deserialization occured. Otherwise, returns null.
	 */
	public static Grocery retrieveData() {
		try {
			FileInputStream savedFile = new FileInputStream("GroceryData");
			ObjectInputStream inputStream = new ObjectInputStream(savedFile);
			grocery = (Grocery) inputStream.readObject();
			Member.retrieve(inputStream);
			Order.retrieve(inputStream);
			Transaction.retrieve(inputStream);
			return grocery;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}


	/**
	 * Serialize Grocery into a data file to be loaded at a later time.
	 * 
	 * @return true iff serialization occured without error. Else, return false.
	 */
	public static boolean save() {
		try {
			FileOutputStream dataFile = new FileOutputStream("GroceryData");
			ObjectOutputStream outputStream = new ObjectOutputStream(dataFile);
			outputStream.writeObject(grocery);
			Member.save(outputStream);
			Order.save(outputStream);
			Transaction.save(outputStream);
			dataFile.close();
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}

	/**
	 * Invokes the AutomatedTest.
	 * 
	 * @return grocery after applying test methods, includes test values.
	 */
	public static Grocery autoTest() {
		new AutomatedTester().autoTest();
		return Grocery.instance();
	}
}

