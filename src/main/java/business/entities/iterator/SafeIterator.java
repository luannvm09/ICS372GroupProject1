package business.entities.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import business.entities.Product;
import business.entities.Transaction;
import business.entities.Member;
import business.entities.Order;
import business.entities.iterator.SafeIterator.Type.SafeProduct;
import business.entities.iterator.SafeIterator.Type.SafeMember;
import business.entities.iterator.SafeIterator.Type.SafeOrder;
import business.entities.iterator.SafeIterator.Type.SafeTransaction;
import business.facade.Result;

/**
 * This Iterator implementation is tailor-made to supply "read-only" versions of Product and Member
 * objects. It is generic. If the user chooses Product for the generic parameter, they should also
 * supply SafeIterator.PRODUCT as the second parameter to the constructor. Similarly, if they choose
 * Member instead, they should also choose SafeIterator.MEMBER as the second parameter of
 * constructor.
 *
 * @param <T> Either Product or Member
 */
public class SafeIterator<T> implements Iterator<Result> {
    private Iterator<T> iterator;
    private Type type;
    private Result result = new Result();
    public static final Type PRODUCT = new SafeProduct();
    public static final Type MEMBER = new SafeMember();
    public static final Type ORDER = new SafeOrder();
    public static final Type TRANSACTION = new SafeTransaction();

    /**
     * This class is designed to ensure that the appropriate object is used to copy to the Result
     * object.
     * 
     **/
    public abstract static class Type {
        /**
         * The copy method is used to copy the object to Result. Object is Product or Member at
         * present.
         * 
         * @param result the Result object
         * @param object the Product or Member object
         */
        public abstract void copy(Result result, Object object);

        public static class SafeProduct extends Type {
            @Override
            public void copy(Result result, Object object) {
                Product product = (Product) object;
                result.setProductFields(product);
            }
        }

        public static class SafeMember extends Type {
            @Override
            public void copy(Result result, Object object) {
                Member member = (Member) object;
                result.setMemberFields(member);
            }
        }

        public static class SafeOrder extends Type {
            @Override
            public void copy(Result result, Object object) {
                Order order = (Order) object;
                result.setOrderFields(order);
            }
        }

        public static class SafeTransaction extends Type {
            @Override
            public void copy(Result result, Object object) {
                Transaction transaction = (Transaction) object;
                result.setTransactionFields(transaction);
            }
        }
    }

    /**
     * The user of SafeIterator must supply an Iterator to Product or Member. If Iterator<Product>
     * is passed as the first parameter, SafeItearator.PRODUCT should be passed as the second
     * parameter. If Iterator<Member> is passed as the first parameter, SafeItearator.MEMBER should
     * be the second parameter.
     * 
     * @param iterator Iterator<Product> or Iterator<Member>
     * @param type     SafeItearator.PRODUCT or SafeItearator.MEMBER
     */
    public SafeIterator(Iterator<T> iterator, Type type) {
        this.iterator = iterator;
        this.type = type;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Result next() {
        if (iterator.hasNext()) {
            type.copy(result, iterator.next());
        } else {
            throw new NoSuchElementException("No such element");
        }
        return result;
    }

}
