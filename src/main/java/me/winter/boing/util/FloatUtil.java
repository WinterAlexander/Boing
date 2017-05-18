package me.winter.boing.util;

import static java.lang.Math.abs;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-16.
 */
public strictfp class FloatUtil
{
	public static final int DEFAULT_ULPS = 5;

	private FloatUtil() {}

	public static boolean areEqual(float a, float b, float epsilon)
	{
		return abs(a - b) < epsilon;
	}

	public static boolean isGreaterOrEqual(float a, float b, float epsilon)
	{
		return a - b > epsilon;
	}

	public static boolean isSmallerOrEqual(float a, float b, float epsilon)
	{
		return a - b < epsilon;
	}

	public static boolean areEqual(float a, float b)
	{
		return areEqual(a, b, DEFAULT_ULPS * Math.ulp((a + b) / 2));
	}

	public static boolean isGreaterOrEqual(float a, float b)
	{
		return isGreaterOrEqual(a, b, DEFAULT_ULPS * Math.ulp((a + b) / 2));
	}

	public static boolean isSmallerOrEqual(float a, float b)
	{
		return isSmallerOrEqual(a, b, DEFAULT_ULPS * Math.ulp((a + b) / 2));
	}

	public static float max(float a, float b, float c)
	{
		return Math.max(Math.max(a, b), c);
	}

	public static float max(float a, float b, float c, float d)
	{
		return Math.max(Math.max(Math.max(a, b), c), d);
	}

	public static float max(float a, float b, float c, float d, float e)
	{
		return Math.max(Math.max(Math.max(Math.max(a, b), c), d), e);
	}

	public static float max(float a, float b, float c, float d, float e, float f)
	{
		return Math.max(Math.max(Math.max(Math.max(Math.max(a, b), c), d), e), f);
	}

	public static float max(float... floats)
	{
		float max = floats[0];

		for(int i = floats.length - 1; i > 0; i--)
			max = Math.max(max, floats[i]);

		return max;
	}
}
