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
 * Adapter around an iterator that copies items in the adaptee to another object. This makes the
 * iterator yield read only items.
 *
 * @param <T> Product, Member, Order, or Transaction
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
         * The copy method is used to copy the object to Result. Object is Product, Member,
         * Transaction, or Order
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
     * The user of SafeIterator must supply an Iterator to Product, Member, Order, or Transaction.
     * If Iterator<Product> is passed as the first parameter, SafeItearator.PRODUCT should be passed
     * as the second parameter. Use the other safe types for the other supported object types.
     * 
     * @param iterator Iterator<Member>, Iterator<Product>, Iterator<Order> or Iterator<Transaction>
     * @param type     SafeItearator.PRODUCT, SafeItearator.MEMBER, SafeIterator.Order or
     *                 SafeIterator.Transaction
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
