package business.facade;

import java.util.Calendar;

public class Request extends DataTransfer {
    private static Request request = null;
    private int stockOnHand;
    private Calendar date;

    /**
     * This is a singleton class. Hence the private constructor.
     */
    private Request() {

    }

    /**
     * Returns the only instance of the class.
     * 
     * @return the only instance
     */
    public static Request instance() {
        if (Request.request == null) {
            Request.request = new Request();
        }
        return Request.request;
    }

    public int getStockOnHand() {
        return this.stockOnHand;
    }

    public void setStockOnHand(int stockOnHand) {
        this.stockOnHand = stockOnHand;
    }

    public Calendar getDate() {
        return this.date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}