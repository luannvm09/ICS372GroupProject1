package business.facade;

/**
 * Subclass of DataTransfer that represents a response from the Grocery instance back to the UI
 */
public class Result extends DataTransfer {
    // Result codes used by UI to determine results of commands
    public static final int PRODUCT_NOT_FOUND = 1;
    public static final int OPERATION_COMPLETED = 2;
    public static final int OPERATION_FAILED = 3;
    public static final int NO_SUCH_MEMBER = 4;
    public static final int TRANSACTION_NOT_FOUND = 5;
    public static final int ORDER_NOT_FOUND = 6;
    private int resultCode;

    /**
     * Getter for resultCode
     * 
     * @return result code for command executed by Grocery
     */
    public int getResultCode() {
        return resultCode;
    }

    /**
     * Setter for resultCode
     * 
     * @param resultCode result code for command executed by Grocery
     */
    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

}
