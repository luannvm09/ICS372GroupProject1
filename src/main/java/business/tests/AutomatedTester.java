package business.tests;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import business.entities.Member;
import business.entities.Product;
import business.facade.Grocery;
import business.facade.Request;
import business.facade.Result;

public class AutomatedTester {
	private final Grocery grocery = Grocery.instance();
	private final String[] names = {"n1", "n2", "n3", "n4", "n5", "n6", "n7"};
	private final String[] addresses = {"a1", "a2", "a3", "a4", "a5", "a6", "a7"};
	private final String[] phones = {"p1", "p2", "p3", "p4", "p5", "p6", "p7"};
	private final double[] fees = {1.00, 2.20, 3.10, 4.56, 5.33, 6.99, 7.65};
	private final Member[] members = new Member[7];
	private final String[] ids =
			{"i1", "i2", "i3", "i4", "i5", "i6", "i7", "i8", "i9", "i10", "i11", "i12", "i13",
					"i14", "i15", "i16", "i17", "i18", "i19", "i20", "i21", "i22", "i23"};
	private final String[] productName =
			{ "pro1", "prod2", "pro3", "product4", "p5", "p6", "p7", "p8", "p9", "p10", "p11", "p12",
					"p13", "p14", "p15", "p16", "p17", "p18", "p19", "p20", "p21", "p22", "p23"};
	private final int[] currentStock = {7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 19, 18,
			17, 16, 15, 14, 13, 12, 11};
	private final double[] currentPrice =
			{1.11, 2.22, 3.33, 4.44, 5.55, 6.66, 7.77, 8.88, 9.99, 10.10, 11.11, 12.12, 13.13,
					14.14, 15.15, 16.16, 17.17, 18.18, 19.19, 20.20, 21.21, 22.22, 23.23};
	private final int[] reorderQty =
			{5, 6, 7, 8, 9, 10, 5, 6, 7, 8, 9, 10, 5, 6, 7, 8, 9, 10, 5, 6, 7, 8, 9};
	private final Product[] products = new Product[23];
	private final Random random = new Random();

	/**
	 * Tests addMember function.
	 */
	public void testAddMember() {
		for (int count = 0; count < members.length; count++) {
			Request.instance().setMemberAddress(addresses[count]);
			Request.instance().setMemberName(names[count]);
			Request.instance().setMemberPhoneNumber(phones[count]);
			Request.instance().setFeePaid(fees[count]);
			Request.instance().setDateJoined(Calendar.getInstance());
			Result result = grocery.addMember(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getMemberName().equals(names[count]);
			assert result.getMemberPhoneNumber().equals(phones[count]);
			assert result.getMemberAddress().equals(addresses[count]);
			assert result.getFeePaid() == fees[count];
		}
	}

	/**
	 * Tests removeMember function.
	 */
	public void testRemoveMember() {
		Request.instance().setMemberId("M8");
		Result result = grocery.removeMember(Request.instance());
		assert result.getResultCode() == Result.NO_SUCH_MEMBER;
		Request.instance().setMemberId("M1");
		result = grocery.removeMember(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
		// test member was removed
		Iterator<Result> memberResults = grocery.getMembers();
		while (memberResults.hasNext()) {
			Result memberResult = memberResults.next();
			assert !memberResult.getMemberId().equals("M1");
		}
	}

	/**
	 * Tests addProduct function.
	 */
	public void testAddProduct() {
		for (int count = 0; count < products.length; count++) {
			Request.instance().setProductId(ids[count]);
			Request.instance().setProductName(productName[count]);
			Request.instance().setStockOnHand(currentStock[count]);
			Request.instance().setCurrentPrice(currentPrice[count]);
			Request.instance().setReorderLevel(reorderQty[count]);
			Result result = grocery.addProduct(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getProductName().equals(productName[count]);
			assert result.getProductId().equals(ids[count]);
			assert result.getStockOnHand() == currentStock[count];
			assert result.getCurrentPrice() == currentPrice[count];
			assert result.getReorderLevel() == reorderQty[count];
		}
		// test for ordering products after adding them
		Iterator<Result> orderResults = grocery.getOutstandingOrders();
		int count = 0;
		while (orderResults.hasNext()) {
			Result orderResult = orderResults.next();
			// assert orderResult.getOrderProduct().equals(products[count]);
			count++;
		}
	}

	/**
	 * Creates a random number of transactions. Each transaction contains a random amount of
	 * products/quantities. Each transaction is assigned to a random member.
	 */
	public void testCheckout() {
		// Get a list of all available product ids
		ArrayList<String> productIds = new ArrayList<String>();
		Iterator<Result> productResults = grocery.getProducts();
		while (productResults.hasNext()) {
			Result productResult = productResults.next();
			productIds.add(productResult.getProductId());
		}
		// Get a list of all available member ids
		ArrayList<String> memberIds = new ArrayList<String>();
		Iterator<Result> memberResults = grocery.getMembers();
		while (memberResults.hasNext()) {
			Result memberResult = memberResults.next();
			memberIds.add(memberResult.getMemberId());
		}
		// Create a random number of transactions between 1 - 50
		int numberOfTransactions = getRandomInt(1, 50);
		for (int transactionNumber =
				0; transactionNumber < numberOfTransactions; transactionNumber++) {
			// Member who is checking out
			String customerId = getRandomElement(memberIds);
			// Begin transaction and assert successful completion
			Result beginTransactionResult = grocery.beginTransaction();
			assert beginTransactionResult.getResultCode() == Result.OPERATION_COMPLETED;
			// Assert that a transaction id was provided in the result
			String transactionId = beginTransactionResult.getTransactionId();
			assert transactionId != null;
			// A random number of random products/quantity to the transaction between 1 and 10
			int numberOfLineItems = getRandomInt(1, 10);
			for (int lineNumber = 0; lineNumber < numberOfLineItems; lineNumber++) {
				// Create request and set necessary fields to add transaction line item
				Request instance = Request.instance();
				String randomProductId = getRandomElement(productIds);
				instance.setProductId(randomProductId);
				instance.setCheckoutQuantity(getRandomInt(1, 20));
				instance.setTransactionId(transactionId);
				// Add the LineItem to transaction and assert operation completed
				Result addLineItemRequest = grocery.addTransactionLineItem(instance);
				assert addLineItemRequest.getResultCode() == Result.OPERATION_COMPLETED;
			}
			// Now that line items were added to the transaction, end the transaction
			Request instance = Request.instance();
			instance.setMemberId(customerId);
			instance.setTransactionId(transactionId);
			Result endTransactionResult = grocery.endTransaction(instance);
			assert endTransactionResult.getResultCode() == Result.OPERATION_COMPLETED;
			// Assert that transaction has the correct number of line items
			assert endTransactionResult.getLineItems().size() == numberOfLineItems;
		}
	}

	/**
	 * Tests processShipment function.
	 */
	public void testProcessShipment() {
		// Get a list of all available order ids
		ArrayList<String> orderIds = new ArrayList<String>();
		Iterator<Result> orderResults = grocery.getOutstandingOrders();
		while (orderResults.hasNext()) {
			Result orderResult = orderResults.next();
			orderIds.add(orderResult.getOrderId());
		}
		/**
		 * The order ids are shuffled into a random order. Since orders are removed after prcessing
		 * shipment, this prevents the test from trying to process the same order twice and throw an
		 * error.
		 */
		Collections.shuffle(orderIds);
		// Randomly select a number of orders from outstanding orders and process the shipment
		int numberOfOrders = getRandomInt(0, orderIds.size());
		for (int orderNumber = 0; orderNumber < numberOfOrders; orderNumber++) {
			Request instance = Request.instance();
			String orderId = orderIds.get(orderNumber);
			instance.setOrderId(orderId);
			Result processShipmentResult = grocery.processShipment(instance);
			assert processShipmentResult.getResultCode() == Result.OPERATION_COMPLETED;
		}
	}

	/**
	 * Tests changePrice function.
	 */
	public void testChangePrice() {
		// Get a list of all the valid product ids
		ArrayList<String> productIds = new ArrayList<String>();
		Iterator<Result> productResults = grocery.getProducts();
		while (productResults.hasNext()) {
			Result productResult = productResults.next();
			productIds.add(productResult.getProductId());
		}

		// Change a random number of product's prices
		int numberOfChanges = getRandomInt(1, 50);
		for (int changeNumber = 0; changeNumber < numberOfChanges; changeNumber++) {
			// Get the protect and take note of its current price
			Request instance = Request.instance();
			String productId = getRandomElement(productIds);
			// Get a random double between 1.0 and 1000.0 (inclusive)
			double newPrice = 1.0 + getRandomInt(0, 999);
			instance.setProductId(productId);
			instance.setCurrentPrice(newPrice);
			Result priceChangeResult = grocery.changePrice(instance);
			assert priceChangeResult.getResultCode() == Result.OPERATION_COMPLETED;
			assert priceChangeResult.getCurrentPrice() == newPrice;
		}
	}

	/**
	 * Helper class for returning a random element in a generic array list
	 * 
	 * @param <T>  the type of the array list
	 * @param list the array list which the element will be selected from
	 * @return T the random element selected
	 */
	public <T> T getRandomElement(ArrayList<T> list) {
		int listSize = list.size();
		int elementIndex = getRandomInt(0, listSize);
		return list.get(elementIndex);
	}

	/**
	 * Helper class for generating a random bounded int
	 * 
	 * @param min minimum value of int
	 * @param max max value of int
	 * @return a random interger [min, max]
	 */
	public int getRandomInt(int min, int max) {
		return random.nextInt(max - min) + min;
	}

	/**
	 * Invokes all the test methods.
	 */
	public void autoTest() {
		testAddMember();
		testRemoveMember();
		testAddProduct();
		testCheckout();
		testProcessShipment();
		testChangePrice();
	}

}
