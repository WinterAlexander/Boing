package me.winter.boing.test.physics;

import me.winter.boing.physics.shapes.Circle;
import me.winter.boing.test.physics.testimpl.DynamicBodyImpl;
import me.winter.boing.test.physics.testimpl.WorldImpl;
import me.winter.boing.test.util.MutableInt;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
public class CircleCircleDetectionTest
{
	@Test
	public void simpleStaticCircleCircle()
	{
		MutableInt collisionCount = new MutableInt(0);

		WorldImpl world = new WorldImpl(collision -> collisionCount.value++);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.getColliders().add(new Circle(solidImpl, 0, 0, 12.5f));
		world.getSolids().add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(0, 0);
		solidImpl2.getColliders().add(new Circle(solidImpl2, 0, 0, 12.5f));
		world.getSolids().add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(2, collisionCount.value);

		solidImpl2.getPosition().set(100, 100);
		world.step(1f);
		assertEquals(2, collisionCount.value);

		solidImpl2.getPosition().set(30, 0);
		world.step(1f);
		assertEquals(2, collisionCount.value);

		solidImpl2.getPosition().set(0, 30);
		world.step(1f);
		assertEquals(2, collisionCount.value);

		solidImpl2.getPosition().set(16, 16);
		world.step(1f);
		assertEquals(3, collisionCount.value);

		solidImpl2.getPosition().set(-16, -16);
		world.step(1f);
		assertEquals(4, collisionCount.value);
	}

	@Test
	public void circleCrashingIntoCircle()
	{
		MutableInt collisionCount = new MutableInt(0);

		WorldImpl world = new WorldImpl(collision -> collisionCount.value++);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.getColliders().add(new Circle(solidImpl, 0, 0, 10));
		world.getSolids().add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(50, 0);
		solidImpl2.getColliders().add(new Circle(solidImpl2, 0, 0, 10));
		solidImpl2.getVelocity().set(-16, 0);
		world.getSolids().add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
	}

	@Test
	public void circleTouchingCircleNoCollision()
	{
		MutableInt collisionCount = new MutableInt(0);

		WorldImpl world = new WorldImpl(collision -> collisionCount.value++);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.getColliders().add(new Circle(solidImpl, 0, 0, 10));
		world.getSolids().add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(20, 0);
		solidImpl2.getColliders().add(new Circle(solidImpl2, 0, 0, 10));
		world.getSolids().add(solidImpl2);

		DynamicBodyImpl solidImpl3 = new DynamicBodyImpl(1f);
		solidImpl3.getPosition().set(10, 17.32050807568877f);
		solidImpl3.getColliders().add(new Circle(solidImpl3, 0, 0, 10));
		world.getSolids().add(solidImpl3);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);
	}

	@Test
	public void circlesCrashing()
	{
		MutableInt collisionCount = new MutableInt(0);

		WorldImpl world = new WorldImpl(collision -> collisionCount.value++);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.getColliders().add(new Circle(solidImpl, 0, 0, 10));
		solidImpl.getVelocity().set(8, 0);
		world.getSolids().add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(50, 0);
		solidImpl2.getColliders().add(new Circle(solidImpl2, 0, 0, 10));
		solidImpl2.getVelocity().set(-8, 0);
		world.getSolids().add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
	}
}
