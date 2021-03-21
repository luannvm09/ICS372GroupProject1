package business.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.entities.iterator.FilteredIterator;

public class Member implements Serializable {
	private static final long serialVersionUID = 1L;
	private String memberId;
	private String memberName;
	private String memberAddress;
	private String memberPhoneNumber;
	private double feePaid;
	private Calendar dateJoined;

	private static final String MEMBER_STRING = "M";
	private static int idCounter;

	/**
	 * Idea is creating the productPurchased List to store all of product in the certain time. If
	 * the customer want to return, the returned products should be in this list.
	 */
	private List<Product> productPurchased = new LinkedList<Product>();
	private List<Transaction> transactions = new LinkedList<Transaction>();

	public Member(String memberName, String memberAddress, String memberPhoneNumber,
			Calendar dateJoined, double feePaid) {
		this.memberId = MEMBER_STRING + ++idCounter;
		this.memberName = memberName;
		this.memberAddress = memberAddress;
		this.memberPhoneNumber = memberPhoneNumber;
		this.dateJoined = dateJoined;
		this.feePaid = feePaid;
	}

	// FIXME
	// I am not sure these lines of code. I want to save the purchased products on
	// the list
	// then if the members return products, we will mark on transaction "return"
	/**
	 * Stores the product is purchased to the member
	 * 
	 * @param product the product to be purchased
	 * @return true if the product could be marked as issued. always true currently
	 */
	/*
	 * public boolean purchase(Product product) { if (productPurchased.add(product)) {
	 * transactions.add(new Transaction("Purchased", product.getProductName())); return true; }
	 * return false; }
	 * 
	 *//**
		 * Marks the product as not issued to the member
		 * 
		 * @param product the product to be returned
		 * @return true if the product could be marked as marked as returned
		 *//*
			 * public boolean returnProduct(Product product) { if (productPurchased.remove(product))
			 * { transactions.add(new Transaction("Returned", product.getProductName())); return
			 * true; } return false; }
			 */

	/**
	 * Gets an iterator to a collection of selected transactions
	 * 
	 * @param date the date for which the transactions have to be retrieved
	 * @return the iterator to the collection
	 */
	public Iterator<Transaction> getTransactionsOnDate(Calendar startDate, Calendar endDate) {
		return new FilteredIterator<Transaction>(transactions.iterator(),
				transaction -> transaction.betweenDates(startDate, endDate));
	}

	/**
	 * Getter for memberId
	 * 
	 * @return member Id
	 */
	public String getMemberId() {
		return this.memberId;
	}

	/**
	 * Getter for memberName
	 * 
	 * @return member name
	 */
	public String getMemberName() {
		return this.memberName;
	}

	/**
	 * Setter for name
	 * 
	 * @param newMemberName member's new name
	 */
	public void setMemberName(String newMemberName) {
		this.memberName = newMemberName;
	}

	/**
	 * Getter for memberAddress
	 * 
	 * @return member address
	 */
	public String getMemberAddress() {
		return this.memberAddress;
	}

	/**
	 * Setter for memberAddress
	 * 
	 * @param newMemberAddress member's new address
	 */
	public void setMemberAddress(String newMemberAddress) {
		this.memberAddress = newMemberAddress;
	}

	/**
	 * Getter for memberPhoneNumber
	 * 
	 * @return member phone number
	 */
	public String getMemberPhoneNumber() {
		return this.memberPhoneNumber;
	}

	/**
	 * Setter for Phone Number
	 * 
	 * @param newMemberPhoneNumber member's new phone number
	 */
	public void setMemberPhoneNumber(String newMemberPhoneNumber) {
		this.memberPhoneNumber = newMemberPhoneNumber;
	}

	/**
	 * Getter for dateJoined
	 * 
	 * @return member date joined
	 */
	public Calendar getDateJoined() {
		return this.dateJoined;
	}

	/**
	 * Setter for dateJoined
	 * 
	 * @param newDateJoined member's new date joined
	 */
	public void setDateJoined(Calendar dateJoined) {
		this.dateJoined = dateJoined;
	}

	/**
	 * Getter for feePaid
	 * 
	 * @return member fee paid
	 */
	public double getFeePaid() {
		return this.feePaid;
	}

	/**
	 * Setter for feePaid
	 * 
	 * @param newFeePaid member's new fee paid
	 */
	public void setFeePaid(Double feePaid) {
		this.feePaid = feePaid;
	}


	/**
	 * a way of representing member different from toString
	 * 
	 * @return string representation of member object
	 */
	public String output() {
		return "Member id " + this.memberId + "; address " + this.memberAddress + "; fee paid $"
				+ this.feePaid;
	}

	/**
	 * returns string representation of member object
	 */
	@Override
	public String toString() {
		return "Member name " + this.memberName + "; date joined " + this.dateJoined + "; address "
				+ this.memberAddress + "; phone number " + this.memberPhoneNumber;
	}

	/**
	 * Hash code method produces a unique hash code for each instance assuming the members ID's are
	 * all unique
	 * 
	 * @param none
	 * @return int - unique hash code for each instance
	 */
	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((this.memberId == null) ? 0 : this.memberId.hashCode());
		return result;
	}

	/**
	 * An equals method that checks to see if two members are equal. This equals method is based on
	 * the member ID and assumes that each member has a unique ID.
	 * 
	 * @param Object - the other member passed as object
	 * @return boolean - true if member are equal, false if not
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		Member otherMember = (Member) object;
		if (this.memberId == null) {
			if (otherMember.memberId != null) {
				return false;
			}
		}
		if (!this.memberId.equals(otherMember.memberId)) {
			return false;
		}
		return true;
	}

	public static void save(ObjectOutputStream output) throws IOException {
		output.writeObject(idCounter);
	}

	public static void retrieve(ObjectInputStream input)
			throws IOException, ClassNotFoundException {
		Member.idCounter = (int) input.readObject();
	}

}
