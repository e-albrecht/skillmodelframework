package de.unigoe.informatik.swe.utils;

/**
 * Represents a tuple
 * @author Ella Albrecht
 *
 * @param <T> Type of the contained data
 */
public class Pair<T> {
	
	private T x1;
	private T x2;
	
	/**
	 * Creates a new pair
	 * @param x1 First value of tuple
	 * @param x2 Second value of tuple
	 */
	public Pair(T x1, T x2) {
		this.setX1(x1);
		this.setX2(x2);
	}

	public T getX1() {
		return x1;
	}

	public void setX1(T x1) {
		this.x1 = x1;
	}

	public T getX2() {
		return x2;
	}

	public void setX2(T x2) {
		this.x2 = x2;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Pair<?>) {
			Pair<?> pair = (Pair<?>) o;
			return (x1.equals(pair.getX1()) && x2.equals(pair.getX2()));
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return x1.hashCode() + x2.hashCode();
	}
	
}
