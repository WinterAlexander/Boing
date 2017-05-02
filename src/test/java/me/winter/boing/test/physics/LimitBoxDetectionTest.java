package me.winter.boing.test.physics;

import me.winter.boing.physics.Collision;
import me.winter.boing.physics.SimpleWorld;
import me.winter.boing.physics.resolver.CollisionResolver;
import me.winter.boing.physics.shapes.Box;
import me.winter.boing.physics.shapes.Limit;
import me.winter.boing.test.physics.testimpl.DynamicBodyImpl;
import me.winter.boing.test.util.MutableInt;
import org.junit.Test;

import static me.winter.boing.physics.util.VectorUtil.UP;
import static org.junit.Assert.assertEquals;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
public class LimitBoxDetectionTest
{
	@Test
	public void testStaticCrossing()
	{
		MutableInt collisionCount = new MutableInt(0);

		me.winter.boing.physics.resolver.CollisionResolver resolver = collision -> collisionCount.value++;
		SimpleWorld world = new SimpleWorld(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(0, 0);
		solidImpl2.addCollider(new Limit(solidImpl2, 0, 0, UP, 20));
		world.add(solidImpl2);

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

		solidImpl2.getPosition().set(19, 9);
		world.step(1f);
		assertEquals(3, collisionCount.value);

		solidImpl2.getPosition().set(-19, -9);
		world.step(1f);
		assertEquals(4, collisionCount.value);
	}

	@Test
	public void testLimitTouchingBox()
	{
		MutableInt collisionCount = new MutableInt(0);
		MutableInt contactCount = new MutableInt(0);

		CollisionResolver resolver = collision -> collisionCount.value++;
		SimpleWorld world = new SimpleWorld(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl() {
			@Override
			public void notifyContact(Collision contact)
			{
				contactCount.value++;
			}
		};
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl();
		solidImpl2.getPosition().set(0, -10);
		solidImpl2.addCollider(new Limit(solidImpl2, 0, 0, UP, 20));
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);
		assertEquals(0, contactCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);
		assertEquals(1, contactCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);
		assertEquals(2, contactCount.value);

	}
}
