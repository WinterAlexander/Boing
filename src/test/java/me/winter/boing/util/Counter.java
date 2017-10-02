package me.winter.boing.util;

/**
 * Used to share a integer between a lambda and it's scope
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
public class Counter
{
	public int value;

	public Counter(int value)
	{
		this.value = value;
	}

	public void increment()
	{
		value++;
	}

	public int getValue()
	{
		return value;
	}
}
