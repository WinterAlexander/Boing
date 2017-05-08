package me.winter.boing.test.detection;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.Collision;
import me.winter.boing.SimpleWorld;
import me.winter.boing.resolver.CollisionResolver;
import me.winter.boing.shapes.Limit;
import me.winter.boing.impl.DynamicBodyImpl;
import me.winter.boing.test.util.MutableInt;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
public class LimitLimitDetectionTest
{
	@Test
	public void simpleLimitLimitBothMoving()
	{
		MutableInt collisionCount = new MutableInt(0);

		CollisionResolver resolver = c -> collisionCount.value++;
		SimpleWorld world = new SimpleWorld(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Limit(solidImpl, 0, 0, new Vector2(1, 0), 20));
		solidImpl.getVelocity().set(40, 0);
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(100, 0);
		solidImpl2.addCollider(new Limit(solidImpl2, 0, 0, new Vector2(-1, 0), 20));
		solidImpl2.getVelocity().set(-40, 0);
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
	}

	@Test
	public void limitMissingLimit()
	{
		MutableInt collisionCount = new MutableInt(0);
		MutableInt contactCount = new MutableInt(0);

		CollisionResolver resolver = c -> collisionCount.value++;
		SimpleWorld world = new SimpleWorld(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f) {
			@Override
			public void notifyContact(Collision contact)
			{
				contactCount.value++;
			}
		};
		solidImpl.getPosition().set(0, 20);
		solidImpl.addCollider(new Limit(solidImpl, 0, 0, new Vector2(1, 0), 20));
		solidImpl.getVelocity().set(40, 0);
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(100, 0);
		solidImpl2.addCollider(new Limit(solidImpl2, 0, 0, new Vector2(-1, 0), 20));
		solidImpl2.getVelocity().set(-40, 0);
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);
		assertEquals(0, contactCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);
		assertEquals(0, contactCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);
		assertEquals(0, contactCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);
		assertEquals(0, contactCount.value);
	}

	@Test
	public void limitCatchupLimit()
	{
		MutableInt collisionCount = new MutableInt(0);

		CollisionResolver resolver = c -> collisionCount.value++;
		SimpleWorld world = new SimpleWorld(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Limit(solidImpl, 0, 0, new Vector2(1, 0), 20));
		solidImpl.getVelocity().set(-10, 0);
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(50, 0);
		solidImpl2.addCollider(new Limit(solidImpl2, 0, 0, new Vector2(-1, 0), 20));
		solidImpl2.getVelocity().set(-40, 0);
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
	}

	@Test
	public void limitTouchingLimit()
	{
		MutableInt collisionCount = new MutableInt(0);
		MutableInt contactCount = new MutableInt(0);

		CollisionResolver resolver = c -> collisionCount.value++;
		SimpleWorld world = new SimpleWorld(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f) {
			@Override
			public void notifyContact(Collision contact)
			{
				contactCount.value++;
			}
		};
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Limit(solidImpl, 0, 0, new Vector2(1, 0), 20));
		solidImpl.getVelocity().set(50, 0);
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(100, 0);
		solidImpl2.addCollider(new Limit(solidImpl2, 0, 0, new Vector2(-1, 0), 20));
		solidImpl2.getVelocity().set(-50, 0);
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);
		assertEquals(0, contactCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);
		assertEquals(1, contactCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
		assertEquals(1, contactCount.value);
	}
}
