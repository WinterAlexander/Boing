package me.winter.boing.util;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * A pool of wrapper objects that should not be created multiple times
 *
 * Not thread safe
 * <p>
 * Created by Alexander Winter on 2017-08-03.
 */
public abstract class WrapSharer<W, T> implements Wrapper<W, T>
{
	private ObjectMap<T, W> wrapMap = new ObjectMap<>();

	@Override
	public W wrap(T content)
	{
		if(wrapMap.containsKey(content))
			return wrapMap.get(content);

		W object = newObject(content);
		wrapMap.put(content, object);
		return object;
	}

	public abstract W newObject(T content);
}
