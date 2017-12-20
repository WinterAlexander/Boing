package me.winter.boing.simulation;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.colliders.Bound;
import me.winter.boing.impl.DynamicBodyImpl;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.simulation.simulator.BoingSimulator;
import me.winter.boing.testimpl.BouncingBallImpl;
import me.winter.boing.testimpl.TestWorldImpl;
import org.junit.Ignore;
import org.junit.Test;

import static me.winter.boing.util.VectorUtil.DOWN;
import static me.winter.boing.util.VectorUtil.UP;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-13.
 */
@Ignore
public class BoundTestSimulation
{
	@Test
	public void simpleBoundBoundY()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		BouncingBallImpl ballImpl = new BouncingBallImpl();
		ballImpl.getPosition().set(425, 200);
		ballImpl.addCollider(new Bound(ballImpl, 0, 0, new Vector2(0, 1), 50));
		ballImpl.getVelocity().set(0, 50);
		world.add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl();
		ballImpl2.getPosition().set(400, 500);
		ballImpl2.addCollider(new Bound(ballImpl2, 0, 0, new Vector2(0, -1), 50));
		ballImpl2.getVelocity().set(0, -100);
		world.add(ballImpl2);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void simpleBoundBoundX()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		BouncingBallImpl ballImpl = new BouncingBallImpl();
		ballImpl.getPosition().set(200, 425);
		ballImpl.addCollider(new Bound(ballImpl, 0, 0, new Vector2(1, 0), 50));
		ballImpl.getVelocity().set(50, 0);
		world.add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl();
		ballImpl2.getPosition().set(500, 400);
		ballImpl2.addCollider(new Bound(ballImpl2, 0, 0, new Vector2(-1, 0), 50));
		ballImpl2.getVelocity().set(-100, 0);
		world.add(ballImpl2);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void comingAtAAngleBoundBoundX()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		BouncingBallImpl ballImpl = new BouncingBallImpl();
		ballImpl.getPosition().set(200, 200);
		ballImpl.addCollider(new Bound(ballImpl, 0, 0, new Vector2(1, 0), 50));
		ballImpl.getVelocity().set(50, 10);
		world.add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl();
		ballImpl2.getPosition().set(500, 100);
		ballImpl2.addCollider(new Bound(ballImpl2, 0, 0, new Vector2(-1, 0), 50));
		ballImpl2.getVelocity().set(-100, 50);
		world.add(ballImpl2);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void boundFallingOnBound()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		DynamicBodyImpl ballImpl = new DynamicBodyImpl(4f);
		ballImpl.getPosition().set(400, 100);
		ballImpl.addCollider(new Bound(ballImpl, 0, 0, UP, 800));
		world.add(ballImpl);

		DynamicBodyImpl ballImpl2 = new DynamicBodyImpl(1f);
		ballImpl2.getPosition().set(500, 150);
		ballImpl2.addCollider(new Bound(ballImpl2, 0, 0, DOWN, 25));
		ballImpl2.getVelocity().set(0, -100);
		world.add(ballImpl2);

		new BoingSimulator(world, 60f).start();
	}
}
