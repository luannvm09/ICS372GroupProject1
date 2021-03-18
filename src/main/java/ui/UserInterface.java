package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import business.entities.Transaction;
import business.facade.Grocery;
import business.facade.Request;
import business.facade.Result;

/**
 * This is the UserInterface Class that will be used to display information to
 * user, and recieve commands. It follows Singleton Pattern
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
		// FIXME check if there is saved data
		UserInterface.grocery = Grocery.instance();
	}

	/**
	 * instance method that returns existing UI if it is already created, if not it
	 * returns a new UI.
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
		String menu = "Make a selection, enter: \n" + ADD_MEMBER + ") Enroll a member\n" + REMOVE_MEMBER
				+ ") Remove a member\n" + ADD_PRODUCT + ") Add a product\n" + CHECKOUT
				+ ") Check out a member's items\n" + PROCESS_SHIPMENT + ") Process a shipment\n" + CHANGE_PRODUCT_PRICE
				+ ") Change the price of a product\n" + PRODUCT_INFO + ") Retrieve product info\n" + MEMBER_INFO
				+ ") Retrieve member info\n" + PRINT_TRANSACTIONS + ") Print transactions\n" + OUTSTANDING_ORDERS
				+ ") List all outstanding orders\n" + LIST_MEMBERS + ") List all members with member info\n"
				+ LIST_PRODUCTS + ") List all products with product info\n" + SAVE + ") Save\n" + HELP + ") Help \n"
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
	 * A method to get an Int value from user input
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
				System.out.println("Input must be a number 0 - 14\n" + "Enter " + HELP + " for help");
			}
		} while (true);
	}

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
			System.out.println(result.getMemberName() + "'s id is " + result.getMemberAddress());
		}
	}

	public void getTransactions() {
		Request.instance().setMemberId(getStringInput("Enter member id: "));
		Request.instance()
				.setStartDate(getDate("Enter the start date of period you want transactions in format mm/dd/yy: "));
		Request.instance()
				.setEndDate(getDate("Enter the end date of period you want transactions in format mm/dd/yy: "));
		Iterator<Transaction> result = grocery.getTransactions(Request.instance());
		while (result.hasNext()) {
			Transaction transaction = (Transaction) result.next();
			System.out.println(transaction.getType() + "   " + transaction.getName() + "\n");
		}
		System.out.println("\n End of transactions \n");
	}

	/**
	 * This method catches user inputs, and relies on getIntegerInput and
	 * getFirstWordInput to process inputs
	 * 
	 * @param none
	 * @return void
	 */
	public void showUserInterface() {
		System.out.println(showMenu());
		boolean continueApplication = true;
		while (continueApplication) {
			try {
				int userChoice = getIntegerInput("Enter: ");
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
					break;
				case (ADD_PRODUCT):
					// add a product
					break;
				case (CHECKOUT):
					// check out a members products
					break;
				case (PROCESS_SHIPMENT):
					// process a shipment
					break;
				case (CHANGE_PRODUCT_PRICE):
					// change product price
					break;
				case (PRODUCT_INFO):
					// retrieve prod info
					break;
				case (MEMBER_INFO):
					// retrieve member info
					break;
				case (PRINT_TRANSACTIONS):
					getTransactions();
					// print transactions
					break;
				case (OUTSTANDING_ORDERS):
					// list outstanding orders
					break;
				case (LIST_PRODUCTS):
					// list member and member info
					break;
				case (LIST_MEMBERS):
					// list prods and prod info
					break;
				case (SAVE):
					// save
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
}
