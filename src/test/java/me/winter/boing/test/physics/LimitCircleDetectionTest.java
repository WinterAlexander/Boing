package me.winter.boing.test.physics;

import me.winter.boing.physics.shapes.Box;
import me.winter.boing.physics.shapes.Circle;
import me.winter.boing.physics.shapes.Limit;
import me.winter.boing.test.physics.testimpl.DynamicSolidImpl;
import me.winter.boing.test.physics.testimpl.WorldImpl;
import me.winter.boing.test.util.MutableInt;
import org.junit.Test;

import static me.winter.boing.physics.util.VectorUtil.UP;
import static org.junit.Assert.assertEquals;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
public class LimitCircleDetectionTest
{
	@Test
	public void testStaticCrossing()
	{
		MutableInt collisionCount = new MutableInt(0);

		WorldImpl world = new WorldImpl(collision -> collisionCount.value++);

		DynamicSolidImpl solidImpl = new DynamicSolidImpl(world, 1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.getColliders().add(new Circle(solidImpl, 0, 0, 10));
		world.getSolids().add(solidImpl);

		DynamicSolidImpl solidImpl2 = new DynamicSolidImpl(world, 1f);
		solidImpl2.getPosition().set(0, 0);
		solidImpl2.getColliders().add(new Limit(solidImpl2, 0, 0, UP, 20));
		world.getSolids().add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(2, collisionCount.value);

		solidImpl2.getPosition().set(100, 100);
		world.step(1f);
		assertEquals(2, collisionCount.value);

		solidImpl2.getPosition().set(-20, 0);
		world.step(1f);
		assertEquals(2, collisionCount.value);

		solidImpl2.getPosition().set(0, 20);
		world.step(1f);
		assertEquals(2, collisionCount.value);

		solidImpl2.getPosition().set(15, 5);
		world.step(1f);
		assertEquals(3, collisionCount.value);

		solidImpl2.getPosition().set(-15, -5);
		world.step(1f);
		assertEquals(4, collisionCount.value);
	}
}
