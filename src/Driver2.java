
public class Driver2 {

	public static void main(String[] args) {
		
		System.out.println("Here is the UI using singlton design pattern");
		UserInterface ui = UserInterface.instance();		
		ui.showUI();
		
	}

}
