package me.winter.boing.test.physics;

import me.winter.boing.physics.shapes.Box;
import me.winter.boing.test.physics.testimpl.DynamicSolidImpl;
import me.winter.boing.test.physics.testimpl.WorldImpl;
import me.winter.boing.test.util.MutableInt;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
public class BoxBoxDetectionTest
{
	@Test
	public void simpleStaticBoxBox()
	{
		MutableInt collisionCount = new MutableInt(0);

		WorldImpl world = new WorldImpl(collision -> collisionCount.value++);

		DynamicSolidImpl solidImpl = new DynamicSolidImpl(world, 1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.getColliders().add(new Box(solidImpl, 0, 0, 20, 20));
		world.getSolids().add(solidImpl);

		DynamicSolidImpl solidImpl2 = new DynamicSolidImpl(world, 1f);
		solidImpl2.getPosition().set(0, 0);
		solidImpl2.getColliders().add(new Box(solidImpl2, 0, 0, 20, 20));
		world.getSolids().add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(2, collisionCount.value);

		solidImpl2.getPosition().set(100, 100);
		world.step(1f);
		assertEquals(2, collisionCount.value);

		solidImpl2.getPosition().set(25, 0);
		world.step(1f);
		assertEquals(2, collisionCount.value);

		solidImpl2.getPosition().set(0, 25);
		world.step(1f);
		assertEquals(2, collisionCount.value);

		solidImpl2.getPosition().set(19, 19);
		world.step(1f);
		assertEquals(3, collisionCount.value);

		solidImpl2.getPosition().set(-19, -19);
		world.step(1f);
		assertEquals(4, collisionCount.value);
	}

	@Test
	public void boxCrashingIntoBox()
	{
		MutableInt collisionCount = new MutableInt(0);

		WorldImpl world = new WorldImpl(collision -> collisionCount.value++);

		DynamicSolidImpl solidImpl = new DynamicSolidImpl(world, 1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.getColliders().add(new Box(solidImpl, 0, 0, 20, 20));
		world.getSolids().add(solidImpl);

		DynamicSolidImpl solidImpl2 = new DynamicSolidImpl(world, 1f);
		solidImpl2.getPosition().set(50, 0);
		solidImpl2.getColliders().add(new Box(solidImpl2, 0, 0, 20, 20));
		solidImpl2.getVelocity().set(-16, 0);
		world.getSolids().add(solidImpl2);

		assertEquals(0, collisionCount.value); //-10 - 10, 40 - 60

		world.step(1f);
		assertEquals(0, collisionCount.value); //-10 - 10, 24 - 44

		world.step(1f);
		assertEquals(1, collisionCount.value); //-10 - 10, 8 - 28
	}

	@Test
	public void boxTouchingBoxNoCollision()
	{
		MutableInt collisionCount = new MutableInt(0);

		WorldImpl world = new WorldImpl(collision -> collisionCount.value++);

		DynamicSolidImpl solidImpl = new DynamicSolidImpl(world, 1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.getColliders().add(new Box(solidImpl, 0, 0, 20, 20));
		world.getSolids().add(solidImpl);

		DynamicSolidImpl solidImpl2 = new DynamicSolidImpl(world, 1f);
		solidImpl2.getPosition().set(20, 0);
		solidImpl2.getColliders().add(new Box(solidImpl2, 0, 0, 20, 20));
		world.getSolids().add(solidImpl2);

		DynamicSolidImpl solidImpl3 = new DynamicSolidImpl(world, 1f);
		solidImpl3.getPosition().set(10, 20);
		solidImpl3.getColliders().add(new Box(solidImpl3, 0, 0, 20, 20));
		world.getSolids().add(solidImpl3);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);
	}

	@Test
	public void boxesCrashing()
	{
		MutableInt collisionCount = new MutableInt(0);

		WorldImpl world = new WorldImpl(collision -> collisionCount.value++);

		DynamicSolidImpl solidImpl = new DynamicSolidImpl(world, 1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.getColliders().add(new Box(solidImpl, 0, 0, 20, 20));
		solidImpl.getVelocity().set(8, 0);
		world.getSolids().add(solidImpl);

		DynamicSolidImpl solidImpl2 = new DynamicSolidImpl(world, 1f);
		solidImpl2.getPosition().set(50, 0);
		solidImpl2.getColliders().add(new Box(solidImpl2, 0, 0, 20, 20));
		solidImpl2.getVelocity().set(-8, 0);
		world.getSolids().add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
	}
}
