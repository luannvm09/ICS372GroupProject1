package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.NumberFormat;
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
	private static final int CHANGE_PRICE = 6;
	private static final int RETRIEVE_PRODUCT_INFO = 7;
	private static final int RETRIEVE_MEMBER_INFO = 8;
	private static final int PRINT_TRANSACTIONS = 9;
	private static final int LIST_OUTSTANDING_ORDERS = 10;
	private static final int LIST_MEMBERS = 11;
	private static final int LIST_PRODUCTS = 12;
	private static final int SAVE = 13;
	private static final int HELP = 14;

	/**
	 * private constructor for singleton pattern
	 */
	private UserInterface() {
		if (getYesOrNoInput("Look for saved data: (yes or no): ")) {
			retrieveData();
		} else if (getYesOrNoInput(
				"Generate a test bed and invoke functionality?: (yes or no): ")) {
			grocery = Grocery.autoTest();
		} else {
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
	 * @return string which shows the available commands
	 */
	private String showMenu() {
		String menu = "Make a selection, enter: \n" + ADD_MEMBER + ") Enroll a member\n"
				+ REMOVE_MEMBER + ") Remove a member\n" + ADD_PRODUCT + ") Add a product\n"
				+ CHECKOUT + ") Check out a member's items\n" + PROCESS_SHIPMENT
				+ ") Process a shipment\n" + CHANGE_PRICE + ") Change the price of a product\n"
				+ RETRIEVE_PRODUCT_INFO + ") Retrieve product info\n" + RETRIEVE_MEMBER_INFO
				+ ") Retrieve member info\n" + PRINT_TRANSACTIONS + ") Print transactions\n"
				+ LIST_OUTSTANDING_ORDERS + ") List all outstanding orders\n" + LIST_MEMBERS
				+ ") List all members with member info\n" + LIST_PRODUCTS
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
	 * A method to get an Int value from user input to be used in for making a command
	 * 
	 * @param message
	 * @return int value from String input
	 */
	private int getIntegerInput(String message) {
		do {
			try {
				String rawUserInput = getFirstWord(message);
				Integer userIntegerInput = Integer.valueOf(rawUserInput);
				if(userIntegerInput.intValue() < 0) {
					System.out.println("Input must be a positive integer!");
				}
				else return userIntegerInput.intValue();
			} catch (NumberFormatException nfe) {
				System.out
						.println("Please enter a valid integer value!");
			}
		} while (true);
	}

	/**
	 * 
	 * Method to get user inputs in form of a double
	 * 
	 * @param message prompt to display to user
	 * @return user input double
	 */
	private double getDoubleInput(String message) {
		do {
			try {
				String rawUserInput = getFirstWord(message);
				Double userDoubleInput = Double.parseDouble(rawUserInput);
				if(userDoubleInput.doubleValue() < 0) {
					System.out.println("Input must be a positive number!");
				}
				else {
					return userDoubleInput.doubleValue();
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Input must be in integer or decimal form. Try again.");
			}
		} while (true);
	}

	/**
	 * Prompt the user for a yes or no response. Don't accept any other input.
	 * 
	 * @param message Prompt for user
	 * @return true if user entered yes, else false
	 */
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
	 * Get a date formatted string from user
	 * 
	 * @param message message shown to user
	 * @return calendar object representing user input
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

	/**
	 * Convert a calendar object into a readable string
	 * 
	 * @param calendar calendar object to be converted to a string
	 * @return readable string that represents calendar
	 */
	private String formatCalendar(Calendar calendar) {
		String output = "";
		output += calendar.get(Calendar.MONTH) + "/";
		output += calendar.get(Calendar.DAY_OF_MONTH) + "/";
		output += calendar.get(Calendar.YEAR);
		return output;
	}

	/**
	 * method to format dollar amounts
	 * 
	 * @param amount - amount in dollar to format
	 * @return the proper format in the form of a string
	 */
	private String formatDollar(double amount) {
		NumberFormat dollarFormat = NumberFormat.getCurrencyInstance();
		String output = dollarFormat.format(amount);
		return output;
	}

	/**
	 * Add a member to the coop
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

	/**
	 * Remove a member from the coop
	 */
	public void removeMember() {
		Request.instance().setMemberId(getStringInput("Enter ID of member to remove: "));
		String memberId = Request.instance().getMemberId();
		Result result = grocery.removeMember(Request.instance());

		if (result.getResultCode() == Result.NO_SUCH_MEMBER) {
			System.out.println("Failed to remove member with ID " + memberId);
			System.out.println("Member does not exist.");
		}

		if (result.getResultCode() == Result.OPERATION_COMPLETED) {
			System.out.println("Removed Member with ID " + memberId);
		}
	}

	/**
	 * Get a member's information using their name for search
	 */
	public void retrieveMemberInfo() {
		Request.instance().setMemberName(getStringInput("Enter the beginning of Members name: "));
		Iterator<Result> results = grocery.retrieveMemberInfo(Request.instance());
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
			System.out.println("Fee Paid: " + formatDollar(result.getFeePaid()));
			// Add a new line
			System.out.println();
		}
	}

	/**
	 * List all of the coop's members
	 */
	public void listMembers() {
		Iterator<Result> iterator = grocery.getMembers();
		System.out.println("\n---Members---\n");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.println("Member ID: " + result.getMemberId());
			System.out.println("Member Name: " + result.getMemberName());
			System.out.println("Member Join Date: " + formatCalendar(result.getDateJoined()));
			System.out.println("Member Address: " + result.getMemberAddress());
			System.out.println("Member Phone Number: " + result.getMemberPhoneNumber());
			System.out.println("Member Fee Paid: " + formatDollar(result.getFeePaid()) + "\n");
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
			output += " @" + formatDollar(productPrice);
			output += " Qty: " + quantity;
			output += " Line: " + formatDollar(quantity * productPrice);
			System.out.println(output);
		}
	}

	/**
	 * Print all of a member's transactions between two dates
	 */
	public void printTransactions() {
		String memberId = getStringInput("Enter member id: ");
		// Check and make sure member even exists first.
		Request.instance().setMemberId(memberId);
		Result checkMemberIdResult = grocery.searchMembership(Request.instance());
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
			System.out.println(
					"Total Transaction Cost: " + formatDollar(result.getCheckoutTotal()) + "\n");
		}
		System.out.println("--End of transactions--\n");
	}

	/**
	 * Print all outstanding product orders
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
	 * Process a product order shipment
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
	 * Print a product's information using it's name to find it
	 */
	public void retrieveProductInfo() {
		String keyword = getStringInput("Enter beginning of product name: ");
		Request.instance().setProductName(keyword);
		Iterator<Result> results = grocery.retrieveProductInfo(Request.instance());
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
			output += "\n    - Price: " + formatDollar(result.getCurrentPrice());
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
	 * Print all of the coop's products
	 */
	public void listProducts() {
		Iterator<Result> results = grocery.getProducts();
		if (!results.hasNext()) {
			System.out.println("There are currently no products.");
		}
		System.out.println("--Products--\n");
		while (results.hasNext()) {
			Result result = results.next();
			System.out.println("ID: " + result.getProductId());
			System.out.println("Name: " + result.getProductName());
			System.out.println("In Stock: " + result.getStockOnHand());
			System.out.println("Current Price: " + formatDollar(result.getCurrentPrice()));
			System.out.println("Reorder Level: " + result.getReorderLevel());
			// Print new line to help readability
			System.out.println();
		}
	}

	/**
	 * Helper function to create an addTransactionLineItem request instance
	 * 
	 * @return Request instance to be used with addTransactionLineItem
	 */
	private Request getCheckoutItemRequest() {
		Request instance = Request.instance();
		instance.setProductId(getStringInput("Enter product ID: "));
		instance.setCheckoutQuantity(getIntegerInput("Enter quantity: "));
		return instance;
	}

	/**
	 * Checkout a member by creating a new transaction.
	 */
	private void checkout() {
		/**
		 * Before starting transaction, ensure that the user's id is valid.
		 */
		String memberId = getStringInput("Enter member's ID: ");
		Request.instance().setMemberId(memberId);
		Result searchResults = grocery.searchMembership(Request.instance());
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
			Result productInformation = grocery.searchProduct(checkoutItemRequest);
			System.out.println("--------------");
			System.out.println("Product name: " + productInformation.getProductName());
			System.out.println("Unit price: " + formatDollar(productInformation.getCurrentPrice()));
			System.out.println("Quantity: " + checkoutItemRequest.getCheckoutQuantity());
			System.out.println("Line Total: " + formatDollar(lineItemResult.getLineTotal()));
			System.out.println("Total Cost: " + formatDollar(lineItemResult.getCheckoutTotal()));
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
	 * Change a coop product's price
	 */
	private void changePrice() {
		Request instance = Request.instance();
		String productId = getStringInput("Enter product's ID: ");
		instance.setProductId(productId);
		Result productSearchResult = grocery.searchProduct(instance);
		if (productSearchResult.getResultCode() == Result.PRODUCT_NOT_FOUND) {
			System.out.println("There is no product with ID " + productId);
			return;
		}
		double newPrice = getDoubleInput("Enter the new price: ");
		instance.setCurrentPrice(newPrice);
		Result priceChangeResult = grocery.changePrice(instance);
		if (priceChangeResult.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println("Failed to update product price.");
			return;
		}
		System.out.println("New price for " + priceChangeResult.getProductName() + " is "
				+ formatDollar(priceChangeResult.getCurrentPrice()));
		return;
	}

	/**
	 * Save all of the coop's data to be loaded later
	 */
	private void save() {
		if (grocery.save()) {
			System.out.println(
					" The current data has been successfully saved in the file GroceryyData \n");
		} else {
			System.out.println(" There has been an error in saving \n");
		}
	}

	/**
	 * Load coop's serialized data
	 */
	private void retrieveData() {
		try {
			if (grocery == null) {
				grocery = Grocery.retrieveData();
				if (grocery != null) {
					System.out.println(
							" The grocery has been successfully retrieved from the file GroceryData \n");
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
	 * This method catches user inputs, and relies on getIntegerInput and getFirstWordInput to
	 * process inputs
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
						// remove a member
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
					case (CHANGE_PRICE):
						// change product price
						changePrice();
						break;
					case (RETRIEVE_PRODUCT_INFO):
						// get products starting with keyword
						retrieveProductInfo();
						break;
					case (RETRIEVE_MEMBER_INFO):
						// retrieve member info
						retrieveMemberInfo();
						break;
					case (PRINT_TRANSACTIONS):
						printTransactions();
						// print transactions
						break;
					case (LIST_OUTSTANDING_ORDERS):
						// list outstanding orders
						listOutstandingOrders();
						break;
					case (LIST_PRODUCTS):
						// list member and member info
						listProducts();
						break;
					case (LIST_MEMBERS):
						// list prods and prod info
						listMembers();
						break;
					case (SAVE):
						// save
						save();
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
	 * Main function
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		UserInterface.instance().showUserInterface();
	}
}
