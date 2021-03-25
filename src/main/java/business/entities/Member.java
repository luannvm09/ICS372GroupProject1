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
	private List<Transaction> transactions = new LinkedList<Transaction>();

	private static final String MEMBER_STRING = "M";
	private static int idCounter;

	public Member(String memberName, String memberAddress, String memberPhoneNumber,
			Calendar dateJoined, double feePaid) {
		this.memberId = MEMBER_STRING + ++idCounter;
		this.memberName = memberName;
		this.memberAddress = memberAddress;
		this.memberPhoneNumber = memberPhoneNumber;
		this.dateJoined = dateJoined;
		this.feePaid = feePaid;
	}

	public boolean addNewUserTransaction(Transaction transaction) {
		return this.transactions.add(transaction);
	}

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
	 * Getter for memberAddress
	 * 
	 * @return member address
	 */
	public String getMemberAddress() {
		return this.memberAddress;
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
	 * Getter for dateJoined
	 * 
	 * @return member date joined
	 */
	public Calendar getDateJoined() {
		return this.dateJoined;
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
