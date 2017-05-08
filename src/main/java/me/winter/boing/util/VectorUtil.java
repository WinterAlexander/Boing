package me.winter.boing.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static java.lang.Math.signum;

/**
 * Offers useful methods and constants for LibGDX vectors
 * <p>
 * Created by Alexander Winter on 2017-04-13.
 */
public class VectorUtil
{
	public static final Vector2 UP = new Vector2(0, 1);
	public static final Vector2 DOWN = new Vector2(0, -1);
	public static final Vector2 LEFT = new Vector2(-1, 0);
	public static final Vector2 RIGHT = new Vector2(1, 0);

	private VectorUtil() {}

	public static Vector2 divide(Vector2 vec, float scalar)
	{
		vec.x /= scalar;
		vec.y /= scalar;
		return vec;
	}

	public static Vector2 divide(Vector2 vec, float x, float y)
	{
		vec.x /= x;
		vec.y /= y;
		return vec;
	}

	public static Vector2 divide(Vector2 vec, Vector2 divider)
	{
		vec.x /= divider.x;
		vec.y /= divider.y;
		return vec;
	}

	public static Vector2 append(Vector2 toAppend, Vector2 other)
	{
		if(signum(toAppend.x) == signum(other.x))
			toAppend.x += other.x;

		if(signum(toAppend.y) == signum(other.y))
			toAppend.y += other.y;

		return toAppend;
	}
}
