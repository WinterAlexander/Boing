package me.winter.boing.test.physics;

import me.winter.boing.physics.Collision;
import me.winter.boing.physics.SimpleWorld;
import me.winter.boing.physics.resolver.CollisionResolver;
import me.winter.boing.physics.shapes.Box;
import me.winter.boing.test.physics.testimpl.DynamicBodyImpl;
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

		CollisionResolver resolver = collision -> collisionCount.value++;
		SimpleWorld world = new SimpleWorld(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(0, 0);
		solidImpl2.addCollider(new Box(solidImpl2, 0, 0, 20, 20));
		world.add(solidImpl2);

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

		me.winter.boing.physics.resolver.CollisionResolver resolver = collision -> collisionCount.value++;
		SimpleWorld world = new SimpleWorld(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(50, 0);
		solidImpl2.addCollider(new Box(solidImpl2, 0, 0, 20, 20));
		solidImpl2.getVelocity().set(-16, 0);
		world.add(solidImpl2);

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
		MutableInt contactCount = new MutableInt(0);

		CollisionResolver resolver = collision -> collisionCount.value++;
		SimpleWorld world = new SimpleWorld(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f) {
			@Override
			public void notifyContact(Collision contact)
			{
				contactCount.value++;
			}
		};
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(20, 0);
		solidImpl2.addCollider(new Box(solidImpl2, 0, 0, 20, 20));
		world.add(solidImpl2);

		DynamicBodyImpl solidImpl3 = new DynamicBodyImpl(1f);
		solidImpl3.getPosition().set(10, 20);
		solidImpl3.addCollider(new Box(solidImpl3, 0, 0, 20, 20));
		world.add(solidImpl3);

		assertEquals(0, collisionCount.value);
		assertEquals(0, contactCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);
		assertEquals(2, contactCount.value);
	}

	@Test
	public void boxesCrashing()
	{
		MutableInt collisionCount = new MutableInt(0);

		me.winter.boing.physics.resolver.CollisionResolver resolver = collision -> collisionCount.value++;
		SimpleWorld world = new SimpleWorld(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		solidImpl.getVelocity().set(8, 0);
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(50, 0);
		solidImpl2.addCollider(new Box(solidImpl2, 0, 0, 20, 20));
		solidImpl2.getVelocity().set(-8, 0);
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
	}
}
