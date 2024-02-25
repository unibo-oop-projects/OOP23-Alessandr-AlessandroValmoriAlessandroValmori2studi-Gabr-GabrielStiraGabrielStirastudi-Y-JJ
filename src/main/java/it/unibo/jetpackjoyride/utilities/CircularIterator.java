package it.unibo.jetpackjoyride.utilities;

import java.util.Iterator;
import java.util.List;

public class CircularIterator<T> implements Iterator<T> {
    private List<T> list;
    private int currentIndex;

    public CircularIterator(List<T> list) {
        this.list = list;
        this.currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return true; // Always return true since the iteration is infinite
    }

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

