package it.unibo.jetpackjoyride.utilities;

import java.util.Objects;

/**
 * A standard generic Pair<X,Y>, with getters, hashCode, equals, and toString well implemented.
 * 
 * @param <X> The type of the first element in the pair.
 * @param <Y> The type of the second element in the pair.
 */
public final class Pair<X, Y> {

    private final X x;
    private final Y y;

    /**
     * Constructor to create an instance of the Pair class.
     * 
     * @param x The first element.
     * @param y The second element.
     */
    public Pair(final X x, final Y y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the first element.
     * 
     * @return The first element.
     */
    public X get1() {
        return x;
    }

    /**
     * Gets the second element.
     * 
     * @return The second element.
     */
    public Y get2() {
        return y;
    }

    /**
     * Computes the hash code for this Pair.
     * 
     * @return The hash code value for this Pair.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Indicates whether some other object is "equal to" this Pair.
     * 
     * @param obj The reference object with which to compare.
     * @return {@code true} if this Pair is equal to the obj argument; {@code false} otherwise.
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<X, Y> other = (Pair<X, Y>) obj;
        return Objects.equals(x, other.x) && Objects.equals(y, other.y);
    }

    /**
     * Returns a string representation of this Pair.
     * 
     * @return A string representation of this Pair.
     */
    @Override
    public String toString() {
        return "Pair [X=" + x + ", Y=" + y + "]";
    }
}
