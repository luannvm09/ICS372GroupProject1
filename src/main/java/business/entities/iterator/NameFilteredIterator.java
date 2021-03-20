package business.entities.iterator;




import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Filters an iterator of type T and ensures that nameCheck is true for all elements in iterator.	
 * 
 * @param <T> type can be member or product, those who implement NameCheck.
 */

public class NameFilteredIterator<T extends NameCheck> implements Iterator<T>{
	

	    private T item;
	    private String name;
	    private Predicate<T> predicate;
	    
	    private Iterator<T> iterator;
	   

	    /**
	     * Sets the iterator and predicate fields and positions to the first item that
	     * satisfies the predicate.
	     * 
	     * @param iterator  the iterator to the list
	     * @param predicate specifies the test
	     */
	    public NameFilteredIterator(Iterator<T> iterator, String name) {
	    	this.name = name;
	        this.iterator = iterator;
	        getNextItem();
	       
	        
	    }

	   

	    /*
	     * This method searches for the next item that satisfies the predicate. If none
	     * is found, the item field is set to null.
	     */
	    private void getNextItem() {
	        while (iterator.hasNext()) {
	            item = iterator.next();
	            predicate = item -> item.nameCheck(name);
	            if (predicate.test(item)) {
	                return;
	            }
	        }
	        item = null;
	    }

	

	

	@Override
	public boolean hasNext() {
		return item != null;
	}

	@Override
	public T next() {
		 if (!hasNext()) {
	            throw new NoSuchElementException("No such element");
	        }
	        T returnValue = item;
	        getNextItem();
	        return returnValue;
	}

	}


