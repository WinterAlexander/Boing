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
		return ulp((a >= b) ? a : b);
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
		return (a >= b) ? a : b;
	}

	public static float max(float a, float b, float c)
	{
		float a1 = (a >= b) ? a : b;
		return (a1 >= c) ? a1 : c;
	}

	public static float max(float a, float b, float c, float d)
	{
		float a1 = (a >= b) ? a : b;
		float a2 = (a1 >= c) ? a1 : c;
		return (a2 >= d) ? a2 : d;
	}

	public static float max(float a, float b, float c, float d, float e)
	{
		float a1 = (a >= b) ? a : b;
		float a2 = (a1 >= c) ? a1 : c;
		float a3 = (a2 >= d) ? a2 : d;
		return (a3 >= e) ? a3 : e;
	}

	public static float max(float a, float b, float c, float d, float e, float f)
	{
		float a1 = (a >= b) ? a : b;
		float a2 = (a1 >= c) ? a1 : c;
		float a3 = (a2 >= d) ? a2 : d;
		float a4 = (a3 >= e) ? a3 : e;
		return (a4 >= f) ? a4 : f;
	}

	public static float max(float a, float b, float c, float d, float e, float f, float g)
	{
		float a1 = (a >= b) ? a : b;
		float a2 = (a1 >= c) ? a1 : c;
		float a3 = (a2 >= d) ? a2 : d;
		float a4 = (a3 >= e) ? a3 : e;
		float a5 = (a4 >= f) ? a4 : f;
		return (a5 >= g) ? a5 : g;
	}

	public static float max(float a, float b, float c, float d, float e, float f, float g, float h)
	{
		float a1 = (a >= b) ? a : b;
		float a2 = (a1 >= c) ? a1 : c;
		float a3 = (a2 >= d) ? a2 : d;
		float a4 = (a3 >= e) ? a3 : e;
		float a5 = (a4 >= f) ? a4 : f;
		float a6 = (a5 >= g) ? a5 : g;
		return (a6 >= h) ? a6 : h;
	}

	public static float max(float a, float b, float c, float d, float e, float f, float g, float h, float i)
	{
		float a1 = (a >= b) ? a : b;
		float a2 = (a1 >= c) ? a1 : c;
		float a3 = (a2 >= d) ? a2 : d;
		float a4 = (a3 >= e) ? a3 : e;
		float a5 = (a4 >= f) ? a4 : f;
		float a6 = (a5 >= g) ? a5 : g;
		float a7 = (a6 >= h) ? a6 : h;
		return (a7 >= i) ? a7 : i;
	}

	public static float max(float a, float b, float c, float d, float e, float f, float g, float h, float i, float j)
	{
		float a1 = (a >= b) ? a : b;
		float a2 = (a1 >= c) ? a1 : c;
		float a3 = (a2 >= d) ? a2 : d;
		float a4 = (a3 >= e) ? a3 : e;
		float a5 = (a4 >= f) ? a4 : f;
		float a6 = (a5 >= g) ? a5 : g;
		float a7 = (a6 >= h) ? a6 : h;
		float a8 = (a7 >= i) ? a7 : i;
		return (a8 >= j) ? a8 : j;
	}

	public static float max(float a, float b, float c, float d, float e, float f, float g, float h, float i, float j, float k)
	{
		float a1 = (a >= b) ? a : b;
		float a2 = (a1 >= c) ? a1 : c;
		float a3 = (a2 >= d) ? a2 : d;
		float a4 = (a3 >= e) ? a3 : e;
		float a5 = (a4 >= f) ? a4 : f;
		float a6 = (a5 >= g) ? a5 : g;
		float a7 = (a6 >= h) ? a6 : h;
		float a8 = (a7 >= i) ? a7 : i;
		float a9 = (a8 >= j) ? a8 : j;
		return (a9 >= k) ? a9 : k;
	}

	public static float max(float a, float b, float c, float d, float e, float f, float g, float h, float i, float j, float k, float l)
	{
		float a1 = (a >= b) ? a : b;
		float a2 = (a1 >= c) ? a1 : c;
		float a3 = (a2 >= d) ? a2 : d;
		float a4 = (a3 >= e) ? a3 : e;
		float a5 = (a4 >= f) ? a4 : f;
		float a6 = (a5 >= g) ? a5 : g;
		float a7 = (a6 >= h) ? a6 : h;
		float a8 = (a7 >= i) ? a7 : i;
		float a9 = (a8 >= j) ? a8 : j;
		float a10 = (a9 >= k) ? a9 : k;
		return (a10 >= l) ? a10 : l;
	}
}
