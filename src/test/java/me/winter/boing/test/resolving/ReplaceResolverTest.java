package me.winter.boing.test.resolving;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.impl.WorldImpl;
import me.winter.boing.impl.BodyImpl;
import me.winter.boing.impl.DynamicBodyImpl;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.colliders.Box;
import me.winter.boing.colliders.Limit;
import org.junit.Test;

import static me.winter.boing.util.VectorUtil.LEFT;
import static me.winter.boing.util.VectorUtil.UP;
import static org.junit.Assert.assertEquals;
import static me.winter.boing.util.VectorAssert.assertEquals;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-08.
 */
public class ReplaceResolverTest
{
	@Test
	public void simpleBoxBoxReplaceTest()
	{
		WorldImpl world = new WorldImpl(new ReplaceResolver());

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		solidImpl.getVelocity().set(15, 0);
		world.add(solidImpl);

		BodyImpl solidImpl2 = new BodyImpl();
		solidImpl2.getPosition().set(30, 0);
		solidImpl2.addCollider(new Box(solidImpl2, 0, 0, 20, 20));
		world.add(solidImpl2);

		assertEquals(new Vector2(0, 0), solidImpl.getPosition());
		assertEquals(new Vector2(30, 0), solidImpl2.getPosition());

		world.step(1f);

		assertEquals(new Vector2(10, 0), solidImpl.getPosition());
		assertEquals(new Vector2(30, 0), solidImpl2.getPosition());

		world.step(1f);

		assertEquals(new Vector2(10, 0), solidImpl.getPosition());
		assertEquals(new Vector2(30, 0), solidImpl2.getPosition());

		world.step(1f);

		assertEquals(new Vector2(10, 0), solidImpl.getPosition());
		assertEquals(new Vector2(30, 0), solidImpl2.getPosition());
	}

	@Test
	public void inaccurateBoxBoxReplaceTest()
	{
		WorldImpl world = new WorldImpl(new ReplaceResolver());

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		solidImpl.getVelocity().set(15 * 60f, 0);
		world.add(solidImpl);

		BodyImpl solidImpl2 = new BodyImpl();
		solidImpl2.getPosition().set(30, 0);
		solidImpl2.addCollider(new Box(solidImpl2, 0, 0, 20, 20));
		world.add(solidImpl2);

		assertEquals(new Vector2(0, 0), solidImpl.getPosition());
		assertEquals(new Vector2(30, 0), solidImpl2.getPosition());

		world.step(1f / 60f);

		assertEquals(new Vector2(10, 0), solidImpl.getPosition());
		assertEquals(new Vector2(30, 0), solidImpl2.getPosition());

		world.step(1f / 60f);

		assertEquals(new Vector2(10, 0), solidImpl.getPosition());
		assertEquals(new Vector2(30, 0), solidImpl2.getPosition());


		for(int i = 0; i < 1000; i++)
		{
			world.step(1f / 60f);

			assertEquals(new Vector2(10, 0), solidImpl.getPosition());
			assertEquals(new Vector2(30, 0), solidImpl2.getPosition());
		}
	}

	@Test
	public void simpleBoxLimitReplaceTest()
	{
		WorldImpl world = new WorldImpl(new ReplaceResolver());

		DynamicBodyImpl solidImpl = new DynamicBodyImpl(1f);
		solidImpl.getPosition().set(0, 0);
		solidImpl.addCollider(new Box(solidImpl, 0, 0, 20, 20));
		solidImpl.getVelocity().set(15, 0);
		world.add(solidImpl);

		BodyImpl solidImpl2 = new BodyImpl();
		solidImpl2.getPosition().set(30, 0);
		solidImpl2.addCollider(new Limit(solidImpl2, -10, 0, LEFT, 20));
		world.add(solidImpl2);

		assertEquals(new Vector2(0, 0), solidImpl.getPosition());
		assertEquals(new Vector2(30, 0), solidImpl2.getPosition());

		world.step(1f);

		assertEquals(new Vector2(10, 0), solidImpl.getPosition());
		assertEquals(new Vector2(30, 0), solidImpl2.getPosition());

		world.step(1f);

		assertEquals(new Vector2(10, 0), solidImpl.getPosition());
		assertEquals(new Vector2(30, 0), solidImpl2.getPosition());

		world.step(1f);

		assertEquals(new Vector2(10, 0), solidImpl.getPosition());
		assertEquals(new Vector2(30, 0), solidImpl2.getPosition());
	}

	@Test
	public void boxStackTest()
	{
		WorldImpl world = new WorldImpl(new ReplaceResolver());

		DynamicBodyImpl boxOver = new DynamicBodyImpl(1f);
		boxOver.getPosition().set(0, 40);
		boxOver.addCollider(new Box(boxOver, 0, 0, 20, 20));
		boxOver.getVelocity().set(0, -4);
		world.add(boxOver);

		DynamicBodyImpl boxUnder = new DynamicBodyImpl(1f);
		boxUnder.getPosition().set(0, 10);
		boxUnder.addCollider(new Box(boxUnder, 0, 0, 20, 20));
		world.add(boxUnder);

		BodyImpl ground = new BodyImpl();
		ground.getPosition().set(0, 0);
		ground.addCollider(new Limit(ground, 0, 0, UP, 100));
		world.add(ground);

		assertEquals(new Vector2(0, 10), boxUnder.getPosition());
		assertEquals(new Vector2(0, 40), boxOver.getPosition());

		world.step(1f);
		world.step(1f);

		assertEquals(new Vector2(0, 10), boxUnder.getPosition());
		assertEquals(new Vector2(0, 32), boxOver.getPosition());

		world.step(1f);

		assertEquals(new Vector2(0, 10), boxUnder.getPosition());
		assertEquals(new Vector2(0, 30), boxOver.getPosition());

		world.step(1f);

		assertEquals(new Vector2(0, 10), boxUnder.getPosition());
		assertEquals(new Vector2(0, 30), boxOver.getPosition());
	}

}
