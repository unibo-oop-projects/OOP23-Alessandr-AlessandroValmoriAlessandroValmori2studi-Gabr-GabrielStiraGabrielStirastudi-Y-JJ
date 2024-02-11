package it.unibo.jetpackjoyride.utilities;

import java.util.Objects;

/*
 * A standard generic Pair<X,Y>, with getters, hashCode, equals, and toString well implemented. 
 */

public class Pair<X, Y> {

	private final X x;
	private final Y y;

	public Pair(X x, Y y) {
		super();
		this.x = x;
		this.y = y;
	}

	public X get1() {
		return x;
	}

	public Y get2() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

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
		Pair<X, Y> other = (Pair<X, Y>) obj;
		return Objects.equals(x, other.x) && Objects.equals(y, other.y);
	}

	@Override
	public String toString() {
		return "Pair [X=" + x + ", Y=" + y + "]";
	}
}
