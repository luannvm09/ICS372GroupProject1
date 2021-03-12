package business.facade;

import business.entities.Member;
import business.entities.Product;

/**
 * DataTransfer is a class that will carry out the transfer of data from the UI
 * and the GroceryStore class. It will store the instance variables of both
 * member and product. These fields can be supplied and sent in a request, or
 * used to return values in a result.
 * 
 * @author jordan dodd
 */
public abstract class DataTransfer {
	private final static int NONE_INT = Integer.MIN_VALUE;
	private final static double NONE_DOUBLE = Double.MIN_VALUE;
	private final static String NONE_STRING = "none";
	// Member fields
	private String memberId;
	private String memberName;
	private String memberAddress;
	private String memberPhoneNumber;
	private String dateJoined;
	private String feePaid;
	// Product fields
	private String productId;
	private String productName;
	private double currentPrice;
	private int stockOnHand;
	private int reorderLevel;

	/**
	 * constructor sets all fields to none
	 */
	public DataTransfer() {
		reset();
	}

	/**
	 * Getters and setters
	 */
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberAddress() {
		return this.memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getMemberPhoneNumber() {
		return this.memberPhoneNumber;
	}

	public void setMemberPhoneNumber(String memberPhoneNumber) {
		this.memberPhoneNumber = memberPhoneNumber;
	}

	public String getDateJoined() {
		return this.dateJoined;
	}

	public void setDateJoined(String dateJoined) {
		this.dateJoined = dateJoined;
	}

	public String getFeePaid() {
		return this.feePaid;
	}

	public void setFeePaid(String feePaid) {
		this.feePaid = feePaid;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getStockOnHand() {
		return this.stockOnHand;
	}

	public void setStockOnHand(int stockOnHand) {
		this.stockOnHand = stockOnHand;
	}

	public double getCurrentPrice() {
		return this.currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public int getReorderLevel() {
		return this.reorderLevel;
	}

	public void setReorderLevel(int reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	/**
	 * This class will set the product fields with the values from a passed in
	 * product. It will leave the member fields in this DataTransfer =
	 * DataTransfer.NONE_STRING
	 * 
	 * @param product
	 */
	public void setProductFields(Product product) {
		this.productId = product.getProductId();
		this.productName = product.getProductName();
		this.stockOnHand = product.getStockOnHand();
		this.currentPrice = product.getCurrentPrice();
		this.reorderLevel = product.getReorderLevel();
	}

	public void setMemberFields(Member member) {
		this.memberId = member.getMemberId();
		this.memberName = member.getMemberName();
		this.memberPhoneNumber = member.getMemberPhoneNumber();
		this.memberAddress = member.getMemberAddress();
	}

	/**
	 * Reset sets all fields to a default value of NONE or constants NONE_INT and
	 * NONE_DOUBLE. reset() does not return null values, so it is clear when a field
	 * does not contain meaningful data. Constants are used as to not confuse a
	 * sentinel value for an actual requested value.
	 * 
	 * @param
	 * @return void
	 */
	public void reset() {
		// member fields
		memberId = DataTransfer.NONE_STRING;
		memberName = DataTransfer.NONE_STRING;
		memberAddress = DataTransfer.NONE_STRING;
		memberPhoneNumber = DataTransfer.NONE_STRING;
		dateJoined = DataTransfer.NONE_STRING;
		feePaid = DataTransfer.NONE_STRING;
		// product fields
		productId = DataTransfer.NONE_STRING;
		productName = DataTransfer.NONE_STRING;
		stockOnHand = DataTransfer.NONE_INT;
		currentPrice = DataTransfer.NONE_DOUBLE;
		reorderLevel = DataTransfer.NONE_INT;
	}

}
