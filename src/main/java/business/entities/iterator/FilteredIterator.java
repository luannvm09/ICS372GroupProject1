package business.entities.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilteredIterator<T> implements Iterator<T> {
    private T item;
    private Predicate<T> predicate;
    private Iterator<T> iterator;

    public FilteredIterator(Iterator<T> iterator, Predicate<T> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
        getNextItem();
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

    public void getNextItem() {
        while (iterator.hasNext()) {
            item = iterator.next();
            if (predicate.test(item)) {
                return;
            }
        }
        item = null;
    }


}
