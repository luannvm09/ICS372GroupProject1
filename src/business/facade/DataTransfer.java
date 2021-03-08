package business.facade;

import business.entities.Product;

/**
 * DataTransfer is a class that will carry out the transfer of data from the UI
 * and the GroceryStore class.  It will store the instance variables of both member
 * and product.  These fields can be supplied and sent in a request, or used to 
 * return values in a result.
 * 
 * @author jordan dodd
 */
public abstract class DataTransfer {  // FIXME this class does not contian a setMemberFields() method yet
	private final static int NONE_INT = Integer.MIN_VALUE;
	private final static double NONE_DOUBLE = Double.MIN_VALUE;
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
	private int stockOnHand;
	private double currentPrice;
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
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getMemberPhoneNumber() {
		return memberPhoneNumber;
	}

	public void setMemberPhoneNumber(String memberPhoneNumber) {
		this.memberPhoneNumber = memberPhoneNumber;
	}

	public String getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(String dateJoined) {
		this.dateJoined = dateJoined;
	}

	public String getFeePaid() {
		return feePaid;
	}

	public void setFeePaid(String feePaid) {
		this.feePaid = feePaid;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}



	public int getStockOnHand() {
		return stockOnHand;
	}

	public void setStockOnHand(int stockOnHand) {
		this.stockOnHand = stockOnHand;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public int getReorderLevel() {
		return reorderLevel;
	}

	public void setReorderLevel(int reorderLevel) {
		this.reorderLevel = reorderLevel;
	}
	
	/**
	 * This class will set the product fields with the values from a passed
	 * in product.  It will leave the member fields in this DataTransfer = "none"
	 * @param product
	 */
	public void setProductFields(Product product) {
		productId = product.getProductId();
		productName = product.getProductName();
		stockOnHand = product.getStockOnHand();
		currentPrice = product.getCurrentPrice();
		reorderLevel = product.getReorderLevel();
	}
	/**
	 *  Reset sets all fields to a default value of NONE
	 *  or constants NONE_INT and NONE_DOUBLE.  reset() does not
	 *  return null values, so it is clear when a field does not contain
	 *  meaningful data.  Constants are used as to not confuse a sentinel
	 *  value for an actual requested value.
	 *  @param none
	 *  @return void
	 */
	public void reset() {
		//member fields
		memberId = "none";
		memberName = "none";
		memberAddress = "none";
		memberPhoneNumber = "none";
		dateJoined = "none";
		feePaid = "none";
		//product fields
		productId = "none";
		productName = "none";
		stockOnHand = NONE_INT;
		currentPrice = NONE_DOUBLE;
		reorderLevel = NONE_INT;
		
	}

}
