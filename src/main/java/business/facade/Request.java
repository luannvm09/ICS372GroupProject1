package business.facade;

import java.util.Calendar;

public class Request extends DataTransfer {
    private static Request request;
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
        if (request == null) {
            request = new Request();
        }
        return request;
    }

    public int getStockOnHand() {
        return stockOnHand;
    }

    public void setStockOnHand(int stockOnHand) {
        this.stockOnHand = stockOnHand;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}