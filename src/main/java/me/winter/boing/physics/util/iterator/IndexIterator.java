package me.winter.boing.physics.util.iterator;

/**
 * Represents an Iterator that can be accessed using an Index
 * <p>
 * Created by Alexander Winter on 2017-04-25.
 */
public interface IndexIterator<T> extends ReusableIterator<T>
{
	/**
	 * @return size of this iterator, amount of elements
	 */
	int size();

	/**
	 * @param index accessor index
	 * @return object at the specified index
	 */
	T objectAt(int index);
}
