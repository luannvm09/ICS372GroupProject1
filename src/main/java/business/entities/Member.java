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

/**
 * Member entity which represents a member of the coop. In addition to demographic information
 * associated with the member, all of the member's transactions are stored as well.
 */
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Member demographics/basic info
	 */
	private String memberId;
	private String memberName;
	private String memberAddress;
	private String memberPhoneNumber;
	private double feePaid;
	private Calendar dateJoined;
	// All of the transactions completed by this member
	private List<Transaction> transactions = new LinkedList<Transaction>();
	// "M" is prefixed to all generated member ids
	private static final String MEMBER_ID_PREFIX = "M";
	// Current id counter used to ensure unique ids for members
	private static int idCounter;

	/**
	 * Member constructor. Generated a unique id for the member.
	 * 
	 * @param memberName        name of the member
	 * @param memberAddress     address of the member
	 * @param memberPhoneNumber phone number of the member
	 * @param dateJoined        join date of the member
	 * @param feePaid           the fee amount paid by the member
	 */
	public Member(String memberName, String memberAddress, String memberPhoneNumber,
			Calendar dateJoined, double feePaid) {
		// Prefix unique id with "M"
		this.memberId = MEMBER_ID_PREFIX + ++idCounter;
		this.memberName = memberName;
		this.memberAddress = memberAddress;
		this.memberPhoneNumber = memberPhoneNumber;
		this.dateJoined = dateJoined;
		this.feePaid = feePaid;
	}

	/**
	 * Associates a transaction with a member by adding a transaction to the transactions field
	 * 
	 * @param transaction transaction object to associate with member
	 * @return true iff transaction was successfully added to transactions
	 */
	public boolean addNewUserTransaction(Transaction transaction) {
		return this.transactions.add(transaction);
	}

	/**
	 * Returns an iterator to a collection of selected transactions that was completed between a
	 * start and end date
	 * 
	 * @param startDate the minimum date for range of acceptable dates
	 * @param endDate   the maximum date for range of acceptable dates
	 * @return An iterator of transactions that fall between [startDate, endDate]
	 */
	public Iterator<Transaction> getTransactionsOnDate(Calendar startDate, Calendar endDate) {
		return new FilteredIterator<Transaction>(transactions.iterator(),
				transaction -> transaction.betweenDates(startDate, endDate));
	}

	/**
	 * Getter for memberId
	 * 
	 * @return the member id of the Member object
	 */
	public String getMemberId() {
		return this.memberId;
	}

	/**
	 * Getter for memberName
	 * 
	 * @return the member name of the Member object
	 */
	public String getMemberName() {
		return this.memberName;
	}

	/**
	 * Getter for memberAddress
	 * 
	 * @return the member address of the Member object
	 */
	public String getMemberAddress() {
		return this.memberAddress;
	}

	/**
	 * Getter for memberPhoneNumber
	 * 
	 * @return the phone number of the Member object
	 */
	public String getMemberPhoneNumber() {
		return this.memberPhoneNumber;
	}

	/**
	 * Getter for dateJoined
	 * 
	 * @return The Calendar object representing member join date
	 */
	public Calendar getDateJoined() {
		return this.dateJoined;
	}

	/**
	 * Getter for feePaid
	 * 
	 * @return Amount paid in membership fees
	 */
	public double getFeePaid() {
		return this.feePaid;
	}

	/**
	 * toString method for Member
	 * 
	 * @return string representation of Member
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
	 * @return unique hash code for each instance
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
	 * @return true if member are equal, false if not
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

	/**
	 * Serializes the Member object using an ObjectOutputStream
	 * 
	 * @param output Stream used to serialize Member object
	 * @throws IOException
	 */
	public static void save(ObjectOutputStream output) throws IOException {
		output.writeObject(idCounter);
	}

	/**
	 * Retrieve serialized Member idCounter and assign to current member count to ensure unique ids
	 * for newly created members
	 * 
	 * @param input Input stream used to deserialize the Member static idCounter
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void retrieve(ObjectInputStream input)
			throws IOException, ClassNotFoundException {
		Member.idCounter = (int) input.readObject();
	}
}
