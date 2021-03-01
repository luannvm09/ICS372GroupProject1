import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class UserInterface {
	private static UserInterface userInterface;

	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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
		//
	}

	/**
	 * instance method that returns existing UI if it is already
	 * created, if not it returns a new UI.
	 */
	public static UserInterface instance() {
		if(userInterface == null) {
			return userInterface = new UserInterface();
		}
		else {
			return userInterface;
		}
	}

	/**
	 * This is a method to get the list of input commands
	 * @returns menu
	 */
	public String showMenu() {
		String menu = "Make a selection, enter: \n"
				+ ADD_MEMBER + ") Enroll a member\n"
				+ REMOVE_MEMBER + ") Remove a member\n"
				+ ADD_PRODUCT + ") Add a product\n"
				+ CHECKOUT + ") Check out a member's items\n"
				+ PROCESS_SHIPMENT + ") Process a shipment\n"
				+ CHANGE_PRODUCT_PRICE + ") Change the price of a product\n"
				+ PRODUCT_INFO +") Retrieve product info\n"
				+ MEMBER_INFO + ") Retrieve member info\n"
				+ PRINT_TRANSACTIONS + ") Print transactions\n"
				+ OUTSTANDING_ORDERS + ") List all outstanding orders\n"
				+ LIST_MEMBERS + ") List all members with member info\n"
				+ LIST_PRODUCTS +") List all products with product info\n"
				+ SAVE + ") Save\n"
				+ HELP + ") Help \n"
				+ "Press " + EXIT + " at any time to quit the application";
		return menu;
	}

	/**
	 * a method to get the first word entered from user.
	 * @param message
	 * @return
	 */
	public String getFirstWord(String message) {
		do {
			try {
				System.out.print(message);
				String line = reader.readLine();
				String[] words = line.split("\\s"); 
				return words[0];

			}catch(IOException ioe) {
				System.exit(0);
			}
		} while(true);
	}

	/**
	 *  returns a line of text 
	 * @param message
	 * @return
	 */
	public String getStringInput(String message) {
		do {
			try {
				System.out.print(message);
				String line = reader.readLine();
				return line;

			}catch(IOException ioe) {
				System.exit(0);
			}
		} while(true);
	}

	/**
	 * A method to get an Int value from user input 
	 * @param message
	 * @return
	 */
	public int getIntegerInput(String message) {
		do {
			try {
				String rawUserInput = getFirstWord(message);
				Integer userIntegerInput = Integer.valueOf(rawUserInput);
				return userIntegerInput.intValue();
			}catch(NumberFormatException nfe) {
				System.out.println("Input must be a number 0 - 14\n"
						+ "Enter " + HELP + " for help");
			}
		}while(true);
	}

	public void showUI() {
		System.out.println(showMenu());
		
		boolean continueApplication = true;
		while(continueApplication) {
			try {
				int userChoice = getIntegerInput("Enter: ");
				switch(userChoice) {
				case(0):
					System.out.println("Program Succesfully close");
				System.exit(0);
				case (1):
					//enroll a member
					break;
				case(2):
					//remove a member
					break;
				case(3):
					//add a product
					break;
				case(4):
					//check out a members products
					break;
				case(5):
					//process a shipment
					break;
				case(6):
					//change product price
					break;
				case(7):
					//retrieve prod info
					break;
				case(8):
					//retrieve member info
					break;
				case(9):
					//print transactions
					break;
				case(10):
					//list outstanding orders
					break;
				case(11):
					//list member and member info
					break;
				case(12):
					//list prods and prod info
					break;
				case(13):
					//save
					break;
				case(14):
					System.out.println(showMenu());
				break;
				default:
					System.out.println("Invalid entry. Enter 14 for help");
				}
			} catch (Exception e) {
				System.out.println("Invalid entry. Restart Program.");
				System.exit(0);
			}
		}

	}
}



