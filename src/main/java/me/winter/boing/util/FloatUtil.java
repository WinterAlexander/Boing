package me.winter.boing.util;

import static java.lang.Math.abs;
import static java.lang.Math.ulp;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-16.
 */
public class FloatUtil
{
	public static final int DEFAULT_ULPS = 5;

	private FloatUtil() {}

	public static boolean areEqual(float a, float b, int ulps)
	{
		return abs(a - b) < ulps * ulp(a);
	}

	public static boolean isGreaterOrEqual(float a, float b, int ulps)
	{
		return a - b > ulps * -ulp(a);
	}

	public static boolean isSmallerOrEqual(float a, float b, int ulps)
	{
		return a - b < ulps * ulp(a);
	}

	public static boolean areEqual(float a, float b)
	{
		return areEqual(a, b, DEFAULT_ULPS);
	}

	public static boolean isGreaterOrEqual(float a, float b)
	{
		return isGreaterOrEqual(a, b, DEFAULT_ULPS);
	}

	public static boolean isSmallerOrEqual(float a, float b)
	{
		return isSmallerOrEqual(a, b, DEFAULT_ULPS);
	}
}
