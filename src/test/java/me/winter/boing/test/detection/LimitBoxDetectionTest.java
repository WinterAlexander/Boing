package me.winter.boing.test.detection;

import me.winter.boing.impl.WorldImpl;
import me.winter.boing.resolver.CollisionResolver;
import me.winter.boing.shapes.Box;
import me.winter.boing.shapes.Limit;
import me.winter.boing.impl.DynamicBodyImpl;
import me.winter.boing.util.MutableInt;
import org.junit.Test;

import static me.winter.boing.util.VectorUtil.UP;
import static org.junit.Assert.assertEquals;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
public class LimitBoxDetectionTest
{
	@Test
	public void testLimitGoingThroughBox()
	{
		MutableInt collisionCount = new MutableInt(0);

		CollisionResolver resolver = c -> collisionCount.value++;
		WorldImpl world = new WorldImpl(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(0, -50);
		solidImpl2.addCollider(new Limit(solidImpl2, 0, 0, UP, 20));
		solidImpl2.getVelocity().set(0, 100);
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
	}

	@Test
	public void testLimitGoingThroughBox2()
	{
		MutableInt collisionCount = new MutableInt(0);

		CollisionResolver resolver = c -> collisionCount.value++;
		WorldImpl world = new WorldImpl(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(0, -50);
		solidImpl2.addCollider(new Limit(solidImpl2, 0, 0, UP, 20));
		solidImpl2.getVelocity().set(0, 40);
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(2, collisionCount.value);
	}

	@Test
	public void testLimitGoingThroughBox3()
	{
		MutableInt collisionCount = new MutableInt(0);

		CollisionResolver resolver = c -> collisionCount.value++;
		WorldImpl world = new WorldImpl(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(0, -50);
		solidImpl2.addCollider(new Limit(solidImpl2, 0, 0, UP, 20));
		solidImpl2.getVelocity().set(0, 50);
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
	}

	@Test
	public void testLimitTouchingBox()
	{
		MutableInt collisionCount = new MutableInt(0);

		CollisionResolver resolver = c -> collisionCount.value++;
		WorldImpl world = new WorldImpl(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl();
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl();
		solidImpl2.getPosition().set(0, -10);
		solidImpl2.addCollider(new Limit(solidImpl2, 0, 0, UP, 20));
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(2, collisionCount.value);

	}
}
