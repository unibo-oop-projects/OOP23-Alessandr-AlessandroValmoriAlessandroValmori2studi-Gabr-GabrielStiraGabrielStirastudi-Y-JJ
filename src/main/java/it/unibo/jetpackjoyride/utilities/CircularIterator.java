package it.unibo.jetpackjoyride.utilities;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import java.util.NoSuchElementException;

/**
 * A circular iterator for iterating through a list in a circular manner.
 *
 * @param <T> The type of elements in the list.
 * 
 * @author alessandro.valmori2@studio.unibo.it
 */
public class CircularIterator<T> implements Iterator<T> {
    private final List<T> list;
    private int currentIndex;

    /**
     * Constructs a CircularIterator with the specified list.
     *
     * @param list The list to iterate through.
     */
    public CircularIterator(final List<T> list) {
        this.list = new ArrayList<>(list);
        this.currentIndex = 0;
    }

    /**
     * Checks if there are more elements in the iteration.
     *
     * @return true since the iteration is infinite.
     */
    @Override
    public boolean hasNext() {
        return !this.list.isEmpty();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return The next element in the iteration.
     * @throws IllegalStateException if the list is empty.
     */
    @Override
    public T next() {
        if (this.hasNext()) {
            final T nextItem = this.list.get(this.currentIndex);
            this.currentIndex = (this.currentIndex + 1) % this.list.size(); // Wrap around to the beginning if end is reached
            return nextItem;
        }
        throw new NoSuchElementException("List is empty at circular iterator");
    }
}
