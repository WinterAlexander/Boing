package me.winter.boing.physics;

import java.util.Iterator;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-25.
 */
public interface ReusableIterator<T> extends Iterator<T>, Iterable<T>
{
	void reset();

	@Override
	default Iterator<T> iterator()
	{
		return this;
	}
}
