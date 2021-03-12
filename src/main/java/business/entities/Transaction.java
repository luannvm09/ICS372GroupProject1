package business.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String type;
    private String name;
    private Calendar date;

    /**
     * Creates the transaction with a given type and product name. The date is the
     * current date.
     * 
     * @param type The type of transaction
     * @param name The name of product
     * 
     */
    public Transaction(String type, String name) {
        this.type = type;
        this.name = name;
        date = new GregorianCalendar();
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
     * Returns the type field
     * 
     * @return type field
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the name field
     * 
     * @return name field
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the date as a String
     * 
     * @return date with month, date, and year
     */
    public String getDate() {
        return date.get(Calendar.MONTH) + "/" + date.get(Calendar.DATE) + "/" + date.get(Calendar.YEAR);
    }

    /**
     * String form of the transaction
     * 
     */
    @Override
    public String toString() {
        return (type + "   " + name);
    }
}