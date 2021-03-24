package business.facade;

/**
 * This class is used for returning many of the results of the grocery system's business logic to
 * user interface.
 *
 * the Result object returns an int code, plus values of selected fields of Product and Member. They
 * are the product name, id, stockOnHand member name, phone, id, address.
 */

public class Result extends DataTransfer {
    public static final int PRODUCT_NOT_FOUND = 1;
    public static final int PRODUCT_NOT_PURCHASED = 2;
    public static final int PRODUCT_PURCHASED = 3;
    public static final int PRODUCT_EMPTY = 4;
    public static final int OPERATION_COMPLETED = 5;
    public static final int OPERATION_FAILED = 6;
    public static final int NO_SUCH_MEMBER = 7;
    public static final int TRANSACTION_NOT_FOUND = 8;
    public static final int ORDER_NOT_FOUND = 9;

    private int resultCode;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

}
