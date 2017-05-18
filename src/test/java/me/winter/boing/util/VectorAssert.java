package me.winter.boing.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.junit.Assert;

import static me.winter.boing.util.FloatUtil.DEFAULT_ULPS;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-08.
 */
public class VectorAssert
{
	private VectorAssert() {}

	public static void assertEquals(Vector2 vec1, Vector2 vec2)
	{
		assertEquals(vec1, vec2, DEFAULT_ULPS);
	}

	public static void assertEquals(Vector2 vec1, Vector2 vec2, int ulps)
	{
		Assert.assertEquals(vec1.x, vec2.x, ulps * Math.ulp(vec1.x));
		Assert.assertEquals(vec1.y, vec2.y, ulps * Math.ulp(vec1.y));
	}

	public static void assertEquals(Vector3 vec1, Vector3 vec2)
	{
		assertEquals(vec1, vec2, DEFAULT_ULPS);
	}

	public static void assertEquals(Vector3 vec1, Vector3 vec2, int ulps)
	{
		Assert.assertEquals(vec1.x, vec2.x, ulps * Math.ulp(vec1.x));
		Assert.assertEquals(vec1.y, vec2.y, ulps * Math.ulp(vec1.y));
		Assert.assertEquals(vec1.z, vec2.z, ulps * Math.ulp(vec1.z));
	}
}
