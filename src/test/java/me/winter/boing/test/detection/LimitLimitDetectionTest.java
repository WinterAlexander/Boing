package me.winter.boing.test.detection;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.colliders.Limit;
import me.winter.boing.impl.DynamicBodyImpl;
import me.winter.boing.impl.PhysicsWorldImpl;
import me.winter.boing.resolver.CollisionResolver;
import me.winter.boing.testimpl.BouncingBallImpl;
import me.winter.boing.util.Counter;
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
		Counter collisionCount = new Counter(0);

		CollisionResolver resolver = (w, c) -> {
			collisionCount.value++;
			return true;
		};
		PhysicsWorldImpl world = new PhysicsWorldImpl(resolver);

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
		Counter collisionCount = new Counter(0);

		CollisionResolver resolver = (w, c) -> {
			collisionCount.value++;
			return true;
		};
		PhysicsWorldImpl world = new PhysicsWorldImpl(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
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

		world.step(1f);
		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);
	}

	@Test
	public void limitCatchupLimit()
	{
		Counter collisionCount = new Counter(0);

		CollisionResolver resolver = (w, c) -> {
			collisionCount.value++;
			return true;
		};
		PhysicsWorldImpl world = new PhysicsWorldImpl(resolver);

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
		Counter collisionCount = new Counter(0);

		CollisionResolver resolver = (w, c) -> {
			collisionCount.value++;
			return true;
		};
		PhysicsWorldImpl world = new PhysicsWorldImpl(resolver);

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
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

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(2, collisionCount.value);
	}

	@Test
	public void cornerGlitchTest()
	{
		Counter collisionCount = new Counter(0);

		CollisionResolver resolver = (w, c) -> {
			collisionCount.value++;
			return true;
		};
		PhysicsWorldImpl world = new PhysicsWorldImpl(resolver);

		BouncingBallImpl ballImpl = new BouncingBallImpl();

		ballImpl.getPosition().set(50, 50);
		ballImpl.addCollider(new Limit(ballImpl, 25, 0, new Vector2(1, 0), 50));
		ballImpl.addCollider(new Limit(ballImpl, 0, 25, new Vector2(0, 1), 50));
		ballImpl.getVelocity().set(50, 50);

		world.add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl();

		ballImpl2.getPosition().set(100, 100);
		ballImpl2.addCollider(new Limit(ballImpl2, -25, 0, new Vector2(-1, 0), 50));
		ballImpl2.addCollider(new Limit(ballImpl2, 0, -25, new Vector2(0, -1), 50));
		ballImpl2.getVelocity().set(-100, -100);

		world.add(ballImpl2);


		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(2, collisionCount.value);

	}
}
