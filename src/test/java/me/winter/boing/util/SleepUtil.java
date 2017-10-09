package me.winter.boing.util;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 09/10/17.
 */
public class SleepUtil
{
	public static void sleep(int millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch(InterruptedException ex)
		{
			throw new RuntimeException(ex);
		}
	}
}
