package me.winter.boing.test;

import me.winter.boing.util.FloatUtil;
import org.junit.Test;

import static me.winter.boing.util.FloatUtil.isGreaterOrEqual;
import static me.winter.boing.util.FloatUtil.isSmallerOrEqual;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-18.
 */
public class FloatUtilTest
{
	@Test(timeout = 1000L)
	public void testMax3Params()
	{
		assertEquals(5f, FloatUtil.max(4.5f, 5f, 2f), 0f);
		assertEquals(4.5f, FloatUtil.max(4.5f, -5f, 2f), 0f);
		assertEquals(1 / 3f, FloatUtil.max(-4.5f, 1 / 3f, -2f), 0f);
		assertEquals(-2f, FloatUtil.max(-4.5f, -5f, -2f), 0f);
		assertEquals(0.001f, FloatUtil.max(-10f, -18.99f, 0.001f), 0f);
		assertEquals(Float.POSITIVE_INFINITY, FloatUtil.max(Float.POSITIVE_INFINITY, Float.MAX_VALUE, Float.NEGATIVE_INFINITY), 0f);
	}

	@Test(timeout = 1000L)
	public void testGreaterOrEqual()
	{
		float epsilon = 0.01f;

		assertTrue(isGreaterOrEqual(5f, 3f, epsilon));
		assertTrue(isGreaterOrEqual(100f, 0f, epsilon));
		assertTrue(isGreaterOrEqual(3f, -5f, epsilon));
		assertTrue(isGreaterOrEqual(2f, 2f, epsilon));
		assertTrue(isGreaterOrEqual(1.99f, 2f, epsilon));
		assertTrue(isGreaterOrEqual(2f, 1.99f, epsilon));
		assertTrue(isGreaterOrEqual(-2.01f, -2f, epsilon));

		assertFalse(isGreaterOrEqual(3f, 5f, epsilon));
		assertFalse(isGreaterOrEqual(0f, 100f, epsilon));
		assertFalse(isGreaterOrEqual(-5f, 3f, epsilon));
		assertFalse(isGreaterOrEqual(1.989f, 2f, epsilon));
	}

	@Test(timeout = 1000L)
	public void testSmallerOrEqual()
	{
		float epsilon = 0.01f;

		assertTrue(isSmallerOrEqual(3f, 5f, epsilon));
		assertTrue(isSmallerOrEqual(0f, 100f, epsilon));
		assertTrue(isSmallerOrEqual(-5f, 3f, epsilon));
		assertTrue(isSmallerOrEqual(2f, 2f, epsilon));
		assertTrue(isSmallerOrEqual(1.99f, 2f, epsilon));
		assertTrue(isSmallerOrEqual(2f, 1.99f, epsilon));
		assertTrue(isSmallerOrEqual(-2f, -2.01f, epsilon));

		assertFalse(isSmallerOrEqual(5f, 3f, epsilon));
		assertFalse(isSmallerOrEqual(100f, 0f, epsilon));
		assertFalse(isSmallerOrEqual(3f, -5f, epsilon));
		assertFalse(isSmallerOrEqual(2f, 1.989f, epsilon));

	}
}
