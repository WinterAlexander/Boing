package me.winter.boing.physics.util.iterator;

import com.badlogic.gdx.utils.Array;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-25.
 */
public class GDXArrayFilterIterator<T> implements ReusableIterator<T>
{
	private int index;
	private Array<?> array;
	private Class<T> type;

	public GDXArrayFilterIterator(Array<?> array, Class<T> type)
	{
		this.array = array;
		this.type = type;
	}

	@Override
	public void reset()
	{
		index = 0;
	}

	@Override
	public boolean hasNext()
	{
		return array.size > index;
	}

	@Override
	public T next()
	{
		T value = (T)array.get(index++);

		while(hasNext() && !type.isInstance(array.get(index)))
			index++;

		return value;
	}
}
