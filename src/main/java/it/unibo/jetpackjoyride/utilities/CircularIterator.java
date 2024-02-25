package it.unibo.jetpackjoyride.utilities;

import java.util.Iterator;
import java.util.List;

/**
 * @author alessandro.valmori2@studio.unibo.it
 */

/**
 * A circular iterator for iterating through a list in a circular manner.
 *
 * @param <T> The type of elements in the list.
 */
public class CircularIterator<T> implements Iterator<T> {
    private List<T> list;
    private int currentIndex;

    /**
     * Constructs a CircularIterator with the specified list.
     *
     * @param list The list to iterate through.
     */
    public CircularIterator(List<T> list) {
        this.list = list;
        this.currentIndex = 0;
    }

    /**
     * Checks if there are more elements in the iteration.
     *
     * @return true since the iteration is infinite.
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return The next element in the iteration.
     * @throws IllegalStateException if the list is empty.
     */
    @Override
    public T next() {
        if (list.isEmpty()) {
            throw new IllegalStateException("List is empty");
        }

        T nextItem = list.get(currentIndex);
        currentIndex = (currentIndex + 1) % list.size(); // Wrap around to the beginning if end is reached
        return nextItem;
    }
}
