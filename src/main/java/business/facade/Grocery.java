package business.facade;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.entities.Member;
import business.entities.Product;
import business.entities.Transaction;
import business.entities.iterator.ReadOnlyIterator;

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
		
		/**
		 * gives you  a list of products whose name starts with specified string
		 * @param name  string of beginning of product name you want
		 * @return  a list with products whose name starts with name
		 */
		public List<Product> retrieveProducts(String name){
			List<Product> names = new LinkedList<Product>();
			Iterator<Product> iterator = stock.iterator();
			while (iterator.hasNext()) {
				Product product = iterator.next();
				if(product.getProductName().startsWith(name)) {
					names.add(product);
				}
			}
			return names;
			
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
		
		/**
		 * gives you  a list of members whose name starts with specified string
		 * @param name  string of beginning of member name you want
		 * @return  a list with members whose name starts with name
		 */
		public List<Member> retrieveMembers(String name){
			List<Member> names = new LinkedList<Member>();
			Iterator<Member> iterator = members.iterator();
			while (iterator.hasNext()) {
				Member member = iterator.next();
				if(member.getMemberName().startsWith(name)) {
					names.add(member);
				}
			}
			return names;
			
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

	/**
	 * 
	 * Member Functions
	 * 
	 */

	/**
	 * 
	 * @param request
	 * @return
	 */
	public Result addMember(Request request) {
		Result result = new Result();
		Member member = new Member(request.getMemberName(), request.getMemberAddress(), request.getMemberPhoneNumber(),
				request.getDateJoined(), request.getFeePaid());
		if (members.insertMember(member)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setMemberFields(member);
			return result;
		}

		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	public Iterator<Member> getMembers() {
		return this.members.safeIterator();
	}
	
	/**
	 * gets an iterator of members whose name start with specified string
	 * @param name the start of the member name you want 
	 * @return an iterator of members whose name start with specified string
	 */
	public Iterator<Member> retreiveMembers(Request request) {
		return new ReadOnlyIterator<Member>(this.members.retrieveMembers(request.getMemberName()));
	}

	/**
	 * Gets transactions of a member
	 * 
	 * @param memberId  memberId of member you want transactions of
	 * @param startDate start date you want transactions from(inclusive)
	 * @param endDate   end date of period you want transactions(inclusive)
	 * @return iterator of transaction items of member requested if memberId and
	 *         dates are valid
	 */
	public Iterator<Transaction> getTransactions(Request request) {
		Member member = members.search(request.getMemberId());
		if (member == null || request.getStartDate().after(request.getEndDate())) {
			return new LinkedList<Transaction>().iterator();
		}
		return member.getTransactionsOnDate(request.getStartDate(), request.getEndDate());
	}

	/**
	 * 
	 * Product Functions
	 * 
	 */
	
	
	/**
	 * gets an iterator of products whose name start with specified string
	 * @param name the start of the product name you want 
	 * @return an iterator of products whose name start with specified string
	 */
	public Iterator<Product> retreiveProducts(Request request) {
		return new ReadOnlyIterator<Product>(this.stock.retrieveProducts(request.getProductName()));
	}
}