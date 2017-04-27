package me.winter.boing.test.physics;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.shapes.Limit;
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
public class LimitLimitDetectionTest
{
	@Test
	public void simpleLimitLimitBothMoving()
	{
		MutableInt collisionCount = new MutableInt(0);

		WorldImpl world = new WorldImpl(collision -> collisionCount.value++);

		DynamicSolidImpl solidImpl = new DynamicSolidImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.getColliders().add(new Limit(solidImpl, 0, 0, new Vector2(1, 0), 20));
		solidImpl.getVelocity().set(40, 0);
		world.getSolids().add(solidImpl);

		DynamicSolidImpl solidImpl2 = new DynamicSolidImpl(1f);
		solidImpl2.getPosition().set(100, 0);
		solidImpl2.getColliders().add(new Limit(solidImpl2, 0, 0, new Vector2(-1, 0), 20));
		solidImpl2.getVelocity().set(-40, 0);
		world.getSolids().add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
	}

	@Test
	public void limitCatchupLimit()
	{
		MutableInt collisionCount = new MutableInt(0);

		WorldImpl world = new WorldImpl(collision -> collisionCount.value++);

		DynamicSolidImpl solidImpl = new DynamicSolidImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.getColliders().add(new Limit(solidImpl, 0, 0, new Vector2(1, 0), 20));
		solidImpl.getVelocity().set(-10, 0);
		world.getSolids().add(solidImpl);

		DynamicSolidImpl solidImpl2 = new DynamicSolidImpl(1f);
		solidImpl2.getPosition().set(50, 0);
		solidImpl2.getColliders().add(new Limit(solidImpl2, 0, 0, new Vector2(-1, 0), 20));
		solidImpl2.getVelocity().set(-40, 0);
		world.getSolids().add(solidImpl2);

		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(0, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);

		world.step(1f);
		assertEquals(1, collisionCount.value);
	}
}
