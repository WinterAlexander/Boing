package me.winter.boing.util;

/**
 * Used to share a integer between a lambda and it's scope
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
public class MutableInt
{
	public int value;

	public MutableInt(int value)
	{
		this.value = value;
	}
}
