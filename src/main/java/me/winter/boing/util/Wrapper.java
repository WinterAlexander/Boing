package me.winter.boing.util;

/**
 * Represents an object able to create wraps for specific types of objects
 * <p>
 * Created by Alexander Winter on 2017-08-03.
 */
@FunctionalInterface
public interface Wrapper<W, T>
{
	W wrap(T content);
}
