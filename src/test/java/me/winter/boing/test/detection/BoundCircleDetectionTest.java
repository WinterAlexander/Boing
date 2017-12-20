package me.winter.boing.test.detection;

import me.winter.boing.colliders.Bound;
import me.winter.boing.impl.DynamicBodyImpl;
import me.winter.boing.impl.WorldImpl;
import me.winter.boing.resolver.CollisionResolver;
import me.winter.boing.colliders.Circle;
import me.winter.boing.util.Counter;
import org.junit.Ignore;
import org.junit.Test;

import static me.winter.boing.util.VectorUtil.UP;
import static org.junit.Assert.assertEquals;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
@Ignore
public class BoundCircleDetectionTest
{
	@Test
	public void testBoundGoingThroughCircle()
	{
		Counter collisionCount = new Counter(0);

		CollisionResolver resolver = (w, c) -> {
			collisionCount.value++;
			return true;
		};
		WorldImpl world = new WorldImpl(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Circle(solidImpl, 0, 0, 10));
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(0, -50);
		solidImpl2.addCollider(new Bound(solidImpl2, 0, 0, UP, 20));
		solidImpl2.getVelocity().set(0, 100);
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
	}

	@Test
	public void testBoundGoingThroughCircle2()
	{
		Counter collisionCount = new Counter(0);

		CollisionResolver resolver = (w, c) -> {
			collisionCount.value++;
			return true;
		};
		WorldImpl world = new WorldImpl(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Circle(solidImpl, 0, 0, 10));
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(0, -50);
		solidImpl2.addCollider(new Bound(solidImpl2, 0, 0, UP, 20));
		solidImpl2.getVelocity().set(0, 40);
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
	}

	@Test
	public void testBoundGoingThroughCircle3()
	{
		Counter collisionCount = new Counter(0);

		CollisionResolver resolver = (w, c) -> {
			collisionCount.value++;
			return true;
		};
		WorldImpl world = new WorldImpl(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Circle(solidImpl, 0, 0, 10));
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl(1f);
		solidImpl2.getPosition().set(0, -50);
		solidImpl2.addCollider(new Bound(solidImpl2, 0, 0, UP, 20));
		solidImpl2.getVelocity().set(0, 50);
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
	}

	@Test
	public void testBoundTouchingCircle()
	{
		Counter collisionCount = new Counter(0);

		CollisionResolver resolver = (w, c) -> {
			collisionCount.value++;
			return true;
		};
		WorldImpl world = new WorldImpl(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl();
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Circle(solidImpl, 0, 0, 10));
		world.add(solidImpl);

		DynamicBodyImpl solidImpl2 = new DynamicBodyImpl();
		solidImpl2.getPosition().set(0, -10);
		solidImpl2.addCollider(new Bound(solidImpl2, 0, 0, UP, 20));
		world.add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(2, collisionCount.value);

	}

}
