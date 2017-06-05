package me.winter.boing.simulation;

import me.winter.boing.colliders.Limit;
import me.winter.boing.impl.BodyImpl;
import me.winter.boing.impl.DynamicBodyImpl;
import me.winter.boing.impl.WorldImpl;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.testimpl.TestWorldImpl;
import me.winter.boing.util.WorldSimulation;
import org.junit.Ignore;
import org.junit.Test;

import static me.winter.boing.util.VectorUtil.DOWN;
import static me.winter.boing.util.VectorUtil.LEFT;
import static me.winter.boing.util.VectorUtil.RIGHT;
import static me.winter.boing.util.VectorUtil.UP;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-06-05.
 */
@Ignore
public class StepByStepSimulation
{
	@Test
	public void fourFrameStackStabilizationTest()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		DynamicBodyImpl pusher = new DynamicBodyImpl();
		pusher.getPosition().set(0, 100);
		pusher.getVelocity().set(100, 0);
		pusher.addCollider(new Limit(pusher, 0, 0, RIGHT, 100));
		world.add(pusher);

		DynamicBodyImpl pushed = new DynamicBodyImpl();

		pushed.getPosition().set(100, 100);
		pushed.addCollider(new Limit(pushed, -50, 0, LEFT, 100));
		pushed.addCollider(new Limit(pushed, 50, 0, RIGHT, 100));

		pushed.addCollider(new Limit(pushed, 0, -50, DOWN, 100));
		pushed.addCollider(new Limit(pushed, 0, 50, UP, 100));

		world.add(pushed);

		BodyImpl wall = new BodyImpl();
		wall.getPosition().set(220, 100);
		wall.addCollider(new Limit(wall, 0, 0, LEFT, 100));
		wall.getColliders()[0].setTag("WALL");
		world.add(wall);


		new WorldSimulation(world, 1f).start(true);
	}

	@Test
	public void pushChain()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		DynamicBodyImpl pusher = new DynamicBodyImpl(5f);
		pusher.getPosition().set(0, 100);
		pusher.getVelocity().set(30, 0);
		pusher.addCollider(new Limit(pusher, 0, 0, RIGHT, 100));
		pusher.getColliders()[0].setTag("PUSHER");
		world.add(pusher);

		DynamicBodyImpl pushed = new DynamicBodyImpl();

		pushed.getPosition().set(100, 100);
		pushed.addCollider(new Limit(pushed, -50, 0, LEFT, 100));
		pushed.addCollider(new Limit(pushed, 50, 0, RIGHT, 100));

		pushed.addCollider(new Limit(pushed, 0, -50, DOWN, 100));
		pushed.addCollider(new Limit(pushed, 0, 50, UP, 100));

		world.add(pushed);

		DynamicBodyImpl pushed2 = new DynamicBodyImpl();
		pushed2.getPosition().set(220, 100);
		pushed2.addCollider(new Limit(pushed2, 0, 0, LEFT, 100));
		pushed2.getColliders()[0].setTag("PUSHED2");
		world.add(pushed2);


		new WorldSimulation(world, 1f).start(true);
	}
}