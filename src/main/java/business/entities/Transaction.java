package business.entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Transaction class that represents a transaction made by a coop member. Each transaction is
 * automatically assigned a unique id.
 */
public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
	// Id of the transaction
	private String transactionId;
	// Date of the transaction
	private Calendar date;
	// list of lineItems (purchases) associated with the transaction
	private LinkedList<LineItem> lineItems;
	private static final String TRANSACTION_ID_PREFIX = "T";
	// Idcounter used to generate unique transaction ids
	private static int idCounter = 0;

	/**
	 * Creates the transaction with a given type and product name. The date is the current date.
	 * Generates and assigns a unique transaction id.
	 * 
	 * @param type The type of transaction
	 * @param name The name of product
	 */
	public Transaction() {
		this.transactionId = Transaction.TRANSACTION_ID_PREFIX + ++Transaction.idCounter;
		this.date = Calendar.getInstance();
		this.lineItems = new LinkedList<LineItem>();
	}

	/**
	 * Getter for transactionId
	 * 
	 * @return id associated with transaction
	 */
	public String getTransactionId() {
		return this.transactionId;
	}

	/**
	 * Getter for lineItems
	 * 
	 * @return line items of the transaction
	 */
	public LinkedList<LineItem> getLineItems() {
		return this.lineItems;
	}

	/**
	 * Getter for date
	 * 
	 * @return date of the transaction
	 */
	public Calendar getDate() {
		return this.date;
	}

	/**
	 * Add a new LineItem to lineItems. Compute the total line total by multiplying product cost
	 * with quantity
	 * 
	 * @param product
	 * @param quantity
	 */
	public void addLineItem(Product product, int quantity) {
		double productCost = product.getCurrentPrice();
		double lineTotal = productCost * quantity;
		LineItem newLineItem = new LineItem(product, quantity, lineTotal);
		this.lineItems.add(newLineItem);
	}

	/**
	 * Calculates total cost of the transaction by adding the line totals for every line item in the
	 * transaction.
	 * 
	 * @return total cost of the transaction
	 */
	public double getTotalCost() {
		double totalCost = 0;
		Iterator<LineItem> lineItemsIterator = this.lineItems.iterator();
		while (lineItemsIterator.hasNext()) {
			LineItem lineItem = lineItemsIterator.next();
			totalCost += lineItem.getLineTotal();
		}
		return totalCost;
	}

	/**
	 * Checks whether this transaction is on the given date
	 * 
	 * @param date The date for which transactions are being sought
	 * @return true iff the dates match
	 */
	public boolean onDate(Calendar date) {
		return ((date.get(Calendar.YEAR) == this.date.get(Calendar.YEAR))
				&& (date.get(Calendar.MONTH) == this.date.get(Calendar.MONTH))
				&& (date.get(Calendar.DATE) == this.date.get(Calendar.DATE)));
	}

	/**
	 * Checks if transaction was between two dates specified
	 * 
	 * @param startDate start date of period to check
	 * @param endDate   end date of period to check
	 * @return true if the transaction was between the two dates
	 */
	public boolean betweenDates(Calendar startDate, Calendar endDate) {
		return ((this.date.after(startDate) && this.date.before(endDate))
				|| this.date.equals(startDate) || this.date.equals(endDate));
	}

	/**
	 * Returns the date as a String
	 * 
	 * @return date with month, date, and year
	 */
	public String getFormattedDate() {
		return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/"
				+ date.get(Calendar.YEAR);
	}

	/**
	 * Transaction toString method
	 * 
	 * @return string representation of the Transaction object
	 */
	@Override
	public String toString() {
		return (this.transactionId + ": " + this.lineItems.size() + " Items in transaction");
	}

	/**
	 * Method for deserializing idCounter data file. Assigns the deserialized variable to idCounter.
	 * 
	 * @param input input stream used to read the data file
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void retrieve(ObjectInputStream input)
			throws IOException, ClassNotFoundException {
		Transaction.idCounter = (int) input.readObject();
	}

	/**
	 * Serializes idCounter
	 * 
	 * @param output output stream used to serialize the idCounter
	 * @throws IOException
	 */
	public static void save(ObjectOutputStream output) throws IOException {
		output.writeObject(idCounter);
	}
}
