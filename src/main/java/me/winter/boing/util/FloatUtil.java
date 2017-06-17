package me.winter.boing.util;

import static java.lang.Math.abs;
import static java.lang.Math.ulp;

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
		return abs(a - b) <= epsilon;
	}

	public static boolean isGreaterOrEqual(float a, float b, float epsilon)
	{
		return b - a <= epsilon;
	}

	public static boolean isSmallerOrEqual(float a, float b, float epsilon)
	{
		return b - a >= -epsilon;
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

	public static float getGreatestULP(float a, float b)
	{
		return ulp(max(a, b));
	}

	public static float getGreatestULP(float a, float b, float c)
	{
		return ulp(max(a, b, c));
	}

	public static float getGreatestULP(float a, float b, float c, float d)
	{
		return ulp(max(a, b, c, d));
	}

	public static float getGreatestULP(float a, float b, float c, float d, float e)
	{
		return ulp(max(a, b, c, d, e));
	}

	public static float getGreatestULP(float a, float b, float c, float d, float e, float f)
	{
		return ulp(max(a, b, c, d, e, f));
	}

	public static float getGreatestULP(float a, float b, float c, float d, float e, float f, float g)
	{
		return ulp(max(a, b, c, d, e, f, g));
	}

	public static float getGreatestULP(float a, float b, float c, float d, float e, float f, float g, float h)
	{
		return ulp(max(a, b, c, d, e, f, g, h));
	}

	public static float getGreatestULP(float a, float b, float c, float d, float e, float f, float g, float h, float i)
	{
		return ulp(max(a, b, c, d, e, f, g, h, i));
	}

	public static float getGreatestULP(float a, float b, float c, float d, float e, float f, float g, float h, float i, float j)
	{
		return ulp(max(a, b, c, d, e, f, g, h, i, j));
	}

	public static float getGreatestULP(float a, float b, float c, float d, float e, float f, float g, float h, float i, float j, float k)
	{
		return ulp(max(a, b, c, d, e, f, g, h, i, j, k));
	}

	public static float getGreatestULP(float a, float b, float c, float d, float e, float f, float g, float h, float i, float j, float k, float l)
	{
		return ulp(max(a, b, c, d, e, f, g, h, i, j, k, l));
	}

	public static float max(float a, float b)
	{
		return Math.max(a, b);
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

	public static float max(float a, float b, float c, float d, float e, float f, float g)
	{
		return Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(a, b), c), d), e), f), g);
	}

	public static float max(float a, float b, float c, float d, float e, float f, float g, float h)
	{
		return Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(a, b), c), d), e), f), g), h);
	}

	public static float max(float a, float b, float c, float d, float e, float f, float g, float h, float i)
	{
		return Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(a, b), c), d), e), f), g), h), i);
	}

	public static float max(float a, float b, float c, float d, float e, float f, float g, float h, float i, float j)
	{
		return Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(a, b), c), d), e), f), g), h), i), j);
	}

	public static float max(float a, float b, float c, float d, float e, float f, float g, float h, float i, float j, float k)
	{
		return Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(a, b), c), d), e), f), g), h), i), j), k);
	}

	public static float max(float a, float b, float c, float d, float e, float f, float g, float h, float i, float j, float k, float l)
	{
		return Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(Math.max(a, b), c), d), e), f), g), h), i), j), k), l);
	}
}
