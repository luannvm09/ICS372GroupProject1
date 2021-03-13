package business.facade;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.entities.Product;
import business.entities.iterator.ReadOnlyIterator;
import business.entities.iterator.SafeIterator;
import business.entities.Member;

public class Grocery implements Serializable {
    private static final long serialVersionUID = 1L;
    private Stock stock = new Stock();
    private MemberList members = new MemberList();
    private static Grocery grocery;

    private class Stock implements Iterable<Product>, Serializable {
        private static final long serialVersionUID = 1L;
        private List<Product> products = new LinkedList<Product>();

        /**
         * Checks whether a product with a given product id exists.
         * 
         * @param productId the id of the product
         * @return true iff the book exists
         * 
         */
        public Product search(String productId) {
            for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
                Product product = (Product) iterator.next();
                if (product.getProductId().equals(productId)) {
                    return product;
                }
            }
            return null;
        }

        /**
         * Removes a product from the stock
         * 
         * @param productId product id
         * @return true if product could be removed
         */
        public boolean removeProduct(String productId) {
            Product product = search(productId);
            if (product == null) {
                return false;
            } else {
                return products.remove(product);
            }
        }

        /**
         * Adding a product into the stock
         * 
         * @param product the product to be added
         * @return true iff the product could be added. Currently always true
         */
        public boolean addProduct(Product product) {
            products.add(product);
            return true;
        }

        /**
         * Returns an iterator to all products
         * 
         * @return iterator to the stock
         */
        public Iterator<Product> iterator() {
            return products.iterator();
        }

        public Iterator<Product> safeIterator() {
            return new ReadOnlyIterator<Product>(products);
        }

        /**
         * String form of the stock collection
         * 
         */
        public String toString() {
            return products.toString();
        }
    }

    private class MemberList implements Iterable<Member>, Serializable {
        private static final long serialVersionUID = 1L;
        private List<Member> members = new LinkedList<Member>();

        /**
         * Checks whether a member with a given member id exists.
         * 
         * @param memberId the id of the member
         * @return true iff member exists
         * 
         */
        public Member search(String memberId) {
            for (Iterator<Member> iterator = members.iterator(); iterator.hasNext();) {
                Member member = iterator.next();
                if (member.getMemberId().equals(memberId)) {
                    return member;
                }
            }
            return null;
        }

        /**
         * Inserts a member into the collection
         * 
         * @param member the member to be inserted
         * @return true iff the member could be inserted. Currently always true
         */
        public boolean insertMember(Member member) {
            members.add(member);
            return true;
        }

        public Iterator<Member> iterator() {
            return members.iterator();
        }

        public Iterator<Member> safeIterator() {
            return new ReadOnlyIterator<Member>(this.members);
        }

        /**
         * String form of the collection
         * 
         */
        @Override
        public String toString() {
            return members.toString();
        }
    }

    /**
     * Private for the singleton pattern creates the stock and member collection
     * objects
     */
    private Grocery() {

    }

    /**
     * Supports the singleton pattern
     * 
     * @return the singleton object
     */
    public static Grocery instance() {
        if (grocery == null) {
            return grocery = new Grocery();
        } else {
            return grocery;
        }
    }

}