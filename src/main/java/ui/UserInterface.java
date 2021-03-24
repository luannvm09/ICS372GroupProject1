package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import business.entities.LineItem;
import business.facade.Grocery;
import business.facade.Request;
import business.facade.Result;

/**
 * This is the UserInterface Class that will be used to display information to user, and recieve
 * commands. It follows Singleton Pattern
 */
public class UserInterface {
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Grocery grocery;
	private static final int EXIT = 0;
	private static final int ADD_MEMBER = 1;
	private static final int REMOVE_MEMBER = 2;
	private static final int ADD_PRODUCT = 3;
	private static final int CHECKOUT = 4;
	private static final int PROCESS_SHIPMENT = 5;
	private static final int CHANGE_PRODUCT_PRICE = 6;
	private static final int PRODUCT_INFO = 7;
	private static final int MEMBER_INFO = 8;
	private static final int PRINT_TRANSACTIONS = 9;
	private static final int OUTSTANDING_ORDERS = 10;
	private static final int LIST_MEMBERS = 11;
	private static final int LIST_PRODUCTS = 12;
	private static final int SAVE = 13;
	private static final int HELP = 14;

	/**
	 * private constructor for singleton pattern
	 */
	private UserInterface() {
		if(getYesOrNoInput("Look for saved data: (yes or no)")) {
			retrieveData();
		}else {
			grocery = Grocery.instance();
		}
	}

	/**
	 * instance method that returns existing UI if it is already created, if not it returns a new
	 * UI.
	 */
	public static UserInterface instance() {
		if (userInterface == null) {
			return userInterface = new UserInterface();
		} else {
			return userInterface;
		}
	}

	/**
	 * This is a method to get the list of input commands
	 * 
	 * @returns menu - used in help command
	 */
	private String showMenu() {
		String menu = "Make a selection, enter: \n" + ADD_MEMBER + ") Enroll a member\n"
				+ REMOVE_MEMBER + ") Remove a member\n" + ADD_PRODUCT + ") Add a product\n"
				+ CHECKOUT + ") Check out a member's items\n" + PROCESS_SHIPMENT
				+ ") Process a shipment\n" + CHANGE_PRODUCT_PRICE
				+ ") Change the price of a product\n" + PRODUCT_INFO + ") Retrieve product info\n"
				+ MEMBER_INFO + ") Retrieve member info\n" + PRINT_TRANSACTIONS
				+ ") Print transactions\n" + OUTSTANDING_ORDERS + ") List all outstanding orders\n"
				+ LIST_MEMBERS + ") List all members with member info\n" + LIST_PRODUCTS
				+ ") List all products with product info\n" + SAVE + ") Save\n" + HELP + ") Help \n"
				+ "Press " + EXIT + " at any time to quit the application";
		return menu;
	}

	/**
	 * a method to get the first word entered from user.
	 * 
	 * @param message - desired message to instruction
	 * @return the first word of the user input
	 */
	private String getFirstWord(String message) {
		do {
			try {
				System.out.print(message);
				String line = reader.readLine();
				String[] words = line.split("\\s");
				return words[0];
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * returns a line of text entered and shows user a prompt
	 * 
	 * @param message - command given
	 * @return String - answer provided by user
	 */
	private String getStringInput(String message) {
		do {
			try {
				System.out.print(message);
				String line = reader.readLine();
				return line;
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * A method to get an Int value from user input to be used in
	 * for making a command
	 * 
	 * @param message
	 * @return int value from String input
	 */
	private int getIntegerInput(String message) {
		do {
			try {
				String rawUserInput = getFirstWord(message);
				Integer userIntegerInput = Integer.valueOf(rawUserInput);
				return userIntegerInput.intValue();
			} catch (NumberFormatException nfe) {
				System.out
						.println("Input must be a number 0 - 14\n" + "Enter " + HELP + " for help");
			}
		} while (true);
	}

	/**
	 * 
	 * Method to get user inputs in form of a double
	 * @param message
	 * @return
	 */
	private double getDoubleInput(String message) {

		do {
			try {
				String rawUserInput = getFirstWord(message);
				return Double.parseDouble(rawUserInput);
			} catch (NumberFormatException nfe) {
				System.out.println("Input must be in integer or decimal form. Try again.");
			}
		} while (true);
	}

	private boolean getYesOrNoInput(String message) {
		do {
			String input = getStringInput(message).toLowerCase();
			if (input.equals("yes") || input.equals("y")) {
				return true;
			} else if (input.equals("no") || input.equals("n")) {
				return false;
			} else {
				System.out.println("Answer must be 'yes' or 'no'. Try again.");
			}
		} while (true);
	}

	/**
	 * method to get a date from user
	 * 
	 * @param message -- message shown to user
	 * @return -- date object input
	 */
	private Calendar getDate(String message) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getStringInput(message);
				DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			} catch (Exception de) {
				System.out.println("Invalid input. Format input as mm/dd/yy.");
			}
		} while (true);
	}

	private String formatCalendar(Calendar calendar) {
		String output = "";
		output += calendar.get(Calendar.MONTH) + "/";
		output += calendar.get(Calendar.DAY_OF_MONTH) + "/";
		output += calendar.get(Calendar.YEAR);
		return output;
	}

	/**
	 * Member Helpers
	 */
	private void addMember() {
		Request.instance().setMemberName(getStringInput("Enter new member's name: "));
		Request.instance().setMemberAddress(getStringInput("Enter new member's address: "));
		Request.instance().setMemberPhoneNumber(getStringInput("Enter new member's phone #: "));
		Request.instance().setFeePaid(getDoubleInput("Enter the amount of fee for new member: "));
		Request.instance().setDateJoined(Calendar.getInstance());
		Result result = grocery.addMember(Request.instance());
		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Could not add member to co-op");
		} else {
			System.out.println(result.getMemberName() + "'s id is " + result.getMemberId());
		}
	}	

	public void removeMember() {
		Request.instance().setMemberId(getStringInput("Enter ID of member to remove: "));
		String memberId = Request.instance().getMemberId();
		Result result = grocery.removeMemberByID(Request.instance());

		if (result.getResultCode() == Result.NO_SUCH_MEMBER) {
			System.out.println("Failed to remove member with ID " + memberId);
			System.out.println("Member does not exist.");
		}

		if (result.getResultCode() == Result.OPERATION_COMPLETED) {
			System.out.println("Removed Member with ID " + memberId);
		}
	}

	/**
	 * TODO
	 */
	public void retrieveMemberInfo() {
		Request.instance().setMemberName(getStringInput("Enter the beginning of Members name: "));

		Iterator<Result> results = grocery.retrieveMembersByName(Request.instance());

		if (!results.hasNext()) {
			System.out.println("Unable to find any users that have a name starting with: '"
					+ Request.instance().getMemberName() + "'");
			return;
		}
		System.out.println("-- Members --");
		while (results.hasNext()) {
			Result result = results.next();

			System.out.println("ID: " + result.getMemberId());
			System.out.println("Name: " + result.getMemberName());
			System.out.println("Address: " + result.getMemberAddress());
			System.out.println("Phone Number: " + result.getMemberPhoneNumber());
			System.out.println("Join Date: " + formatCalendar(result.getDateJoined()));
			System.out.println("Fee Paid: " + result.getFeePaid());
			// Add a new line
			System.out.println();
		}
	}
	
	/**
	 * TODO
	 */
	public void printMembers() {
		Iterator<Result> iterator = grocery.getMembers();
		
		System.out.println("\n\t---Members---\n");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println("Member ID: " + result.getMemberId());
			System.out.println("Member Name: " + result.getMemberName());
			System.out.println("Member Join Date: " + formatCalendar(result.getDateJoined()));
			System.out.println("Member Address: " + result.getMemberAddress());
			System.out.println("Member Phone Number: " + result.getMemberPhoneNumber());
			System.out.println("Member Fee Paid: $" + result.getFeePaid() + "\n");
		}
	}

	/**
	 * Helper functions for pretty-printing the line items of a Transaction
	 * 
	 * @param lineItems
	 */
	private void printTransactionLineItems(List<LineItem> lineItems) {
		System.out.println(lineItems.size() + " line items.");
		Iterator<LineItem> iterator = lineItems.iterator();
		while (iterator.hasNext()) {
			LineItem lineItem = iterator.next();
			String productName = lineItem.getProduct().getProductName();
			double productPrice = lineItem.getProduct().getCurrentPrice();
			int quantity = lineItem.getQuantity();
			String output = " - ";
			output += "id " + lineItem.getProduct().getProductId();
			output += ": " + productName;
			output += " @$" + productPrice;
			output += " Qty: " + quantity;
			output += " Line: $" + (quantity * productPrice);
			System.out.println(output);
		}

	}

	/**
	 * TODO
	 */
	public void getMembersTransactions() {
		String memberId = getStringInput("Enter member id: ");
		// Check and make sure member even exists first.
		Request.instance().setMemberId(memberId);
		Result checkMemberIdResult = grocery.retrieveMemberById(Request.instance());
		if (checkMemberIdResult.getResultCode() == Result.NO_SUCH_MEMBER) {
			System.out.println("Member with ID " + memberId + " does not exist.");
			return;
		}
		// Get date range
		Request.instance().setStartDate(getDate(
				"Enter the start date of period you want transactions in format mm/dd/yy: "));
		Request.instance().setEndDate(
				getDate("Enter the end date of period you want transactions in format mm/dd/yy: "));
		Iterator<Result> results = grocery.getMembersTransactions(Request.instance());
		// No transactions found
		if (!results.hasNext()) {
			System.out.println("No found transactions between specified dates.");
			return;
		}
		// Print the queried transactions
		System.out.println("--Transactions--\n");
		while (results.hasNext()) {
			Result result = results.next();
			System.out.println("ID: " + result.getTransactionId());
			System.out.println("Date: " + formatCalendar(result.getTransactionDate()));
			printTransactionLineItems(result.getLineItems());
			System.out.println("Total Transaction Cost: $" + result.getCheckoutTotal() + "\n");
		}
		System.out.println("--End of transactions--\n");
	}

	/**
	 * TODO
	 */
	public void findMemberByName() {
		Request.instance().setMemberName(getStringInput("Enter beginning of member name: "));
		Iterator<Result> members = grocery.retrieveMembersByName(Request.instance());
		while (members.hasNext()) {
			Result result = members.next();
			System.out.println(result.getMemberId() + ": " + result.getMemberName());
		}
	}

	/**
	 * Order Helpers TODO
	 */
	private void listOutstandingOrders() {
		Iterator<Result> orders = grocery.getOutstandingOrders();

		if (!orders.hasNext()) {
			System.out.println("There are no current outstanding orders.");
			return;
		}

		System.out.println("--Orders--\n");
		while (orders.hasNext()) {
			Result result = orders.next();
			System.out.println("ID: " + result.getOrderId());
			System.out.println("Product Name: " + result.getOrderProduct().getProductName());
			System.out.println("Order Date: " + formatCalendar(result.getOrderDate()));
			System.out.println("Quantity: " + result.getOrderQuantity());
			// Print a new line for ease of reading
			System.out.println();
		}
	}

	/**
	 * TODO
	 */
	public void processShipment() {
		Request instance = Request.instance();
		boolean moreOrders = true;
		while (moreOrders) {
			String orderId = getStringInput("Enter order id: ");
			instance.setOrderId(orderId);
			Result findOrderResult = grocery.processShipment(instance);
			// Ensure that order exists first
			if (findOrderResult.getResultCode() == Result.ORDER_NOT_FOUND) {
				System.out.println("Order with ID " + orderId + " was not found.");
			} else {
				System.out.println("Order succesfully processed!");
				System.out.println("Product ID: " + findOrderResult.getProductId());
				System.out.println("Product Name: " + findOrderResult.getProductName());
				System.out.println("New Quantity: " + findOrderResult.getStockOnHand());
			}
			// Add new line
			System.out.println("");
			moreOrders = getYesOrNoInput("Would you like to process more order? (yes/no): ");
		}
	}

	/**
	 * Product Helpers TODO
	 */
	public void retrieveProductsByName() {
		String keyword = getStringInput("Enter beginning of product name: ");
		Request.instance().setProductName(keyword);
		Iterator<Result> results = grocery.retrieveProductsByName(Request.instance());
		// Check if there are no results
		if (!results.hasNext()) {
			System.out.println("\nUnable to find any products beginning with '" + keyword + "'.");
			return;
		}
		System.out.println("\n--Products--");
		while (results.hasNext()) {
			Result result = results.next();
			String output = "\n -";
			output += " ID: " + result.getProductId();
			output += "\n    - Name: " + result.getProductName();
			output += "\n    - Qty: " + result.getStockOnHand();
			output += "\n    - Price: " + result.getCurrentPrice();
			output += "\n    - Reorder Qty: " + result.getReorderLevel();
			System.out.println(output);
		}
		System.out.println("\n");
	}

	/**
	 * Prompts user to create a new product. Then, calls addProduct from grocery. Grocery
	 * addProduct() creates an initial order for double the reorder quantity.
	 */
	public void addProduct() {
		Request instance = Request.instance();
		instance.setProductId(getStringInput("Enter new product's ID: "));
		instance.setProductName(getStringInput("Enter new product's name: "));
		instance.setStockOnHand(getIntegerInput("Enter new product's current stock: "));
		instance.setCurrentPrice(getDoubleInput("Enter new product's current price: "));
		instance.setReorderLevel(getIntegerInput("Enter new product's reorder quantity: "));

		Result result = grocery.addProduct(instance);

		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Product creation failed.");
			return;
		}

		System.out.println("Succesfully created Product with ID " + instance.getProductId());
	}

	/**
	 * TODO
	 */
	public void printProducts() {
		Iterator<Result> results = grocery.retrieveAllProducts();

		if (!results.hasNext()) {
			System.out.println("There are currently no products.");
		}

		System.out.println("--Products--\n");
		while (results.hasNext()) {
			Result result = results.next();
			System.out.println("ID: " + result.getProductId());
			System.out.println("Name: " + result.getProductName());
			System.out.println("In Stock: " + result.getStockOnHand());
			System.out.println("Current Price: " + result.getCurrentPrice());
			System.out.println("Reorder Level: " + result.getReorderLevel());
			// Print new line to help readability
			System.out.println();
		}
	}

	/**
	 * Transaction Helpers TODO
	 */

	private Request getCheckoutItemRequest() {
		Request instance = Request.instance();

		instance.setProductId(getStringInput("Enter product ID: "));
		instance.setCheckoutQuantity(getIntegerInput("Enter quantity: "));

		return instance;
	}

	/**
	 * TODO
	 */
	private void checkout() {
		/**
		 * Before starting transaction, ensure that the user's id is valid.
		 */
		String memberId = getStringInput("Enter member's ID: ");
		Request.instance().setMemberId(memberId);
		Result searchResults = grocery.retrieveMemberById(Request.instance());
		if (searchResults.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("There is no member with ID " + memberId);
			return;
		}
		/**
		 * Inform grocery to begin a transaction. Result will contain the transaction id
		 */
		Result beginTransactionResult = grocery.beginTransaction();
		if (beginTransactionResult.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Unable to begin new transactions.");
			return;
		}
		String transactionId = beginTransactionResult.getTransactionId();
		/**
		 * Continue accepting checkout items until the user enters no
		 */
		boolean moreItems = true;
		while (moreItems) {
			Request checkoutItemRequest = getCheckoutItemRequest();
			checkoutItemRequest.setTransactionId(transactionId);
			Result lineItemResult = grocery.addTransactionLineItem(checkoutItemRequest);

			if (lineItemResult.getResultCode() == Result.TRANSACTION_NOT_FOUND) {
				System.out.println("There is no valid transaction with ID " + transactionId);
			}

			if (lineItemResult.getResultCode() == Result.PRODUCT_NOT_FOUND) {
				System.out.println(
						"There is no valid product with ID " + checkoutItemRequest.getProductId());
			}

			System.out.println("Line Total: $" + lineItemResult.getLineTotal());
			System.out.println("Total Cost: $" + lineItemResult.getCheckoutTotal());

			boolean shouldContinue = getYesOrNoInput("Add more items for checkout? (yes/no): ");
			if (!shouldContinue) {
				moreItems = false;
			}
		}

		/**
		 * Inform grocery to end the transaction with request object. Request object should contain
		 * transaction id and member id. endTransaction() will add the transaction to the member's
		 * transaction LinkedList
		 **/
		Request instance = Request.instance();
		instance.setMemberId(memberId);
		instance.setTransactionId(transactionId);
		Result endTransactionResult = grocery.endTransaction(instance);

		if (endTransactionResult.getResultCode() == Result.NO_SUCH_MEMBER) {
			System.out.println("Failed to finalize transaction because member with ID " + memberId
					+ " was not found.");
			return;
		}

		System.out.println(
				"Successfully completed transaction " + endTransactionResult.getTransactionId());
	}

	/**
	 * This method change the product price by the given product id
	 */
	private void changeProductPrice() {
		Request instance = Request.instance();
		String productId = getStringInput("Enter product's ID: ");
		instance.setProductId(productId);
		Result productSearchResult = grocery.retrieveProductsById(instance);
		if (productSearchResult.getResultCode() == Result.PRODUCT_NOT_FOUND) {
			System.out.println("There is no product with ID " + productId);
			return;
		}
		double newPrice = getDoubleInput("Enter the new price: ");
		instance.setCurrentPrice(newPrice);
		Result priceChangeResult = grocery.updateProductPrice(instance);
		if (priceChangeResult.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Failed to update product price.");
			return;
		}
		System.out.println("New price for " + priceChangeResult.getProductName() + " is $"
				+ priceChangeResult.getCurrentPrice());
		return;
	}
	
	/**
	 * This method catches user inputs, and relies on getIntegerInput and getFirstWordInput to
	 * process inputs
	 * 
	 * @param none
	 * @return void
	 */
	public void showUserInterface() {
		System.out.println(showMenu());
		boolean continueApplication = true;
		while (continueApplication) {
			try {

				int userChoice = getIntegerInput("\nEnter a command (14 for help): ");
				switch (userChoice) {
					case (EXIT):
						System.out.println("Program Succesfully close");
						System.exit(0);
					case (ADD_MEMBER):
						addMember();
						// enroll a member
						break;
					case (REMOVE_MEMBER):
						// remmove a member
						removeMember();
						break;
					case (ADD_PRODUCT):
						// add a product
						addProduct();
						break;
					case (CHECKOUT):
						// check out a members products
						checkout();
						break;
					case (PROCESS_SHIPMENT):
						// process a shipment
						processShipment();
						break;
					case (CHANGE_PRODUCT_PRICE):
						// change product price
						changeProductPrice();
						break;
					case (PRODUCT_INFO):
						// get products starting with keyword
						retrieveProductsByName();
						break;
					case (MEMBER_INFO):
						// retrieve member info
						retrieveMemberInfo();
						break;
					case (PRINT_TRANSACTIONS):
						getMembersTransactions();
						// print transactions
						break;
					case (OUTSTANDING_ORDERS):
						// list outstanding orders
						listOutstandingOrders();
						break;
					case (LIST_PRODUCTS):
						// list member and member info
						printProducts();
						break;
					case (LIST_MEMBERS):
						// list prods and prod info
						printMembers();
						break;
					case (SAVE):
						// save
						saveData();
						break;
					case (HELP):
						System.out.println(showMenu());
						break;
					default:
						System.out.println("Invalid entry. Enter " + HELP + " for help");
				}
			} catch (Exception e) {
				System.out.println("Unexpected Error. Restart Program.");
				System.exit(0);
			}
		}
	}
	
	/**
	 * retrieve data helper
	 */
	private void retrieveData() {
		try {
			if (grocery == null) {
				grocery = Grocery.retrieveData();
				if (grocery != null) {
					System.out.println(" The grocery has been successfully retrieved from the file GroceryData \n");
				} else {
					System.out.println("File doesnt exist; creating new grocery");
					grocery = Grocery.instance();
				}
			}
		} catch (Exception cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	/**
	 * 
	 * TODO give better names
	 */
    private void saveData() {
        if (grocery.saveData()) {
            System.out.println(" The current data has been successfully saved in the file GroceryyData \n");
        } else {
            System.out.println(" There has been an error in saving \n");
        }
    }
	/**
	 * TODO
	 * @param args
	 */
	public static void main(String[] args) {
		UserInterface.instance().showUserInterface();
	}
}
