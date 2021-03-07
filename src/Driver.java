/**
 * Driver class for running application. Contains main function.
 */
public class Driver {
	public static void main(String[] args) {
		// TODO Write Code
		System.out.println("Running...");
		UserInterface userInterface = UserInterface.instance();
		userInterface.showUserInterface();
		System.out.println("Finished...");
	}
}



