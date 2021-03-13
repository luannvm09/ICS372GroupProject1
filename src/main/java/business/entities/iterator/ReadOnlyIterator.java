package business.entities.iterator;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Wrapper around a standard iterator that prevents mutation of any of the
 * elements in the collection that is being iterated.
 * 
 * If the remove() function is called on a ReadOnlyIterator, a
 * UnsupportedOperationException will be thrown
 */
public class ReadOnlyIterator<T> implements Iterator<T> {
    private Iterator<T> iterator;

    /**
     * Calls unmodifiableCollection with the passed collection to prevent mutation
     * 
     * @param collection
     */
    public ReadOnlyIterator(Collection<T> collection) {
        this.iterator = Collections.unmodifiableCollection(collection).iterator();
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public T next() {
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            throw new NoSuchElementException("No such element");
        }
    }

}
