package business.tests;



import business.entities.Member;
import business.entities.Product;
import business.facade.Grocery;
import business.facade.Request;
import business.facade.Result;

public class AutomatedTester {
	private Grocery grocery;
	private String[] names = { "n1", "n2", "n3", "n4", "n5", "n6", "n7" };
	private String[] addresses = { "a1", "a2", "a3", "a4", "a5", "a6", "a7" };
	private String[] phones = { "p1", "p2", "p3", "p4", "p5", "p6", "p7" };
	private double[] fees = { 1.00, 2.20, 3.10, 4.56, 5.33, 6.99, 7.65 };
	private Member[] members = new Member[7];
	private String[] ids = { "i1", "i2", "i3", "i4", "i5", "i6", "i7", "i8", "i9", "i10", "i11", "i12", "i13", "i14", "i15", "i16", "i17", "i18", "i19", "i20", "i21", "i22", "i23"};
	private String[] prodNames = { "p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8", "p9", "p10", "p12", "p11", "p12", "p13", "p14", "p15", "p16", "p17", "p18", "p19", "p20", "p21", "p22", "p23" };
	private int[] currentStock = { 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11};
	private double[] currentPrice = { 1.11, 2.22, 3.33, 4.44, 5.55, 6.66, 7.77, 8.88, 9.99, 10.10, 11.11, 12.12, 13.13, 14.14, 15.15, 16.16, 17.17, 18.18, 19.19, 20.20, 21.21, 22.22, 23.23};
	private int[] reorderQty = { 5, 6, 7, 8, 9, 10, 5, 6, 7, 8, 9, 10, 5, 6, 7, 8, 9, 10, 5, 6, 7, 8, 9};
	private Product[] products = new Product[23];

	/**
	 * Tests addMember function.
	 */
	public void testAddMember() {
		for (int count = 0; count < members.length; count++) {
			Request.instance().setMemberAddress(addresses[count]);
			Request.instance().setMemberName(names[count]);
			Request.instance().setMemberPhoneNumber(phones[count]);
			Request.instance().setFeePaid(fees[count]);
			Result result = Grocery.instance().addMember(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getMemberName().equals(names[count]);
			assert result.getMemberPhoneNumber().equals(phones[count]);
			assert result.getMemberAddress().equals(addresses[count]);
			assert result.getFeePaid()==fees[count];
		}
	}
	
	/**
	 * Tests removeMember function. 
	 */
	public void testRemoveMember() {
		Request.instance().setMemberId("M8");
		Result result = Grocery.instance().removeMember(Request.instance());
		assert result.getResultCode() == Result.NO_SUCH_MEMBER;
		Request.instance().setMemberId("M1");
		result = Grocery.instance().removeMember(Request.instance());
		assert result.getResultCode() == Result.OPERATION_COMPLETED;
		//more asserts needed
		
	}

	/**
	 * Tests addProduct function.
	 */
	public void testAddProduct() {
		for (int count = 0; count < products.length; count++) {
			Request.instance().setProductId(ids[count]);
			Request.instance().setProductName(prodNames[count]);
			Request.instance().setStockOnHand(currentStock[count]);
			Request.instance().setCurrentPrice(currentPrice[count]);
			Request.instance().setReorderLevel(reorderQty[count]);
			Result result = Grocery.instance().addProduct(Request.instance());
			assert result.getResultCode() == Result.OPERATION_COMPLETED;
			assert result.getProductName().equals(prodNames[count]);
			assert result.getProductId().equals(ids[count]);
			assert result.getStockOnHand() ==currentStock[count];
			assert result.getCurrentPrice()==currentPrice[count];
			assert result.getReorderLevel()==reorderQty[count];
			//add a test for ordering products after adding them
		}
	}
	
	/**
	 * Tests checkout function.
	 */
	public void testCheckout() {
		
	}
	
	/**
	 * Tests processShipment function.
	 */
	public void testProcessShipment() {
		
	}

	/**
	 * Tests changePrice function.
	 */
	public void testChangePrice() {
		
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
