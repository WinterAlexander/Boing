package me.winter.boing.physics;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-25.
 */
public interface IndexIterator<T> extends ReusableIterator<T>
{
	int size();
	T objectAt(int index);
}
