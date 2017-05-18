package me.winter.boing.test;

import me.winter.boing.util.FloatUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-18.
 */
public class TestFloatUtil
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
}
