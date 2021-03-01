import java.util.Scanner;

/**
 * Driver class for running application. Contains main function.
 */
public class Driver {
	
	public static void main(String[] args) {
		// TODO Write Code
		System.out.println("Running...");
		showUserInterface();
		System.out.println("Done.");

		return;
	}
	/**
	 * Function: showUserInterface
	 * @param none
	 * @return none
	 * Prompts the user for commands to run. Loops until user enters 0 
	 */
	public static void showUserInterface() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to small co-op blah blah"); //fix introduction
		String menu = "Make a selection, enter: \n"
				+ "1) Enroll a member\n"
				+ "2) Remove a member\n"
				+ "3) Add a product\n"
				+ "4) Check out a member's items\n"
				+ "5) Process a shipment\n"
				+ "6) Change the price of a product\n"
				+ "7) Retrieve product info\n"
				+ "8) Retrieve member info\n"
				+ "9) Print transactions\n"
				+ "10) List all outstanding orders\n"
				+ "11) List all members with member info\n"
				+ "12) List all products with product info\n"
				+ "13) Save\n"
				+ "14) Help \n"
				+ "Press 0 at any time to quit the application";
		System.out.println(menu);
		boolean continueApplication = true;
		while(continueApplication) {
			System.out.print("Enter a command: ");
			int userInput;
			try {
				userInput = scanner.nextInt();
				switch(userInput) {
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
					System.out.println(menu);
				break;
				default:
					System.out.println("Invalid entry. Enter 14 for help");
				}
				} catch (Exception e) {
					System.out.println("Invalid entry. Restart Program.");
					System.exit(0);
				}

		}
		scanner.close();
	}
}

//heres a change, checking to see if when I push to my fork, it gets added to a pull request
//now that the pull request isn't open.

//i hope it just goes to my fork


