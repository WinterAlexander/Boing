package me.winter.boing.test.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.junit.Assert;

import static com.badlogic.gdx.math.MathUtils.FLOAT_ROUNDING_ERROR;

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
		assertEquals(vec1, vec2, FLOAT_ROUNDING_ERROR);
	}

	public static void assertEquals(Vector2 vec1, Vector2 vec2, float delta)
	{
		Assert.assertEquals(vec1.x, vec2.x, delta);
		Assert.assertEquals(vec1.y, vec2.y, delta);
	}

	public static void assertEquals(Vector3 vec1, Vector3 vec2)
	{
		assertEquals(vec1, vec2, FLOAT_ROUNDING_ERROR);
	}

	public static void assertEquals(Vector3 vec1, Vector3 vec2, float delta)
	{
		Assert.assertEquals(vec1.x, vec2.x, delta);
		Assert.assertEquals(vec1.y, vec2.y, delta);
		Assert.assertEquals(vec1.z, vec2.z, delta);
	}
}
