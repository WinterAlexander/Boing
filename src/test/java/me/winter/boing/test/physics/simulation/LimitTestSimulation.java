package me.winter.boing.test.physics.simulation;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.World;
import me.winter.boing.physics.resolver.ReplaceResolver;
import me.winter.boing.physics.shapes.Limit;
import me.winter.boing.test.physics.testimpl.BouncingBallImpl;
import org.junit.Test;

import static me.winter.boing.test.physics.simulation.WorldSimulationUtil.simulate;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-13.
 */
public class LimitTestSimulation
{
	@Test
	public void simpleLimitLimitY()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl ballImpl = new BouncingBallImpl(world);
		ballImpl.getPosition().set(425, 200);
		ballImpl.getColliders().add(new Limit(ballImpl, 0, 0, new Vector2(0, 1), 50));
		ballImpl.getVelocity().set(0, 50);
		world.getSolids().add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl(world);
		ballImpl2.getPosition().set(400, 500);
		ballImpl2.getColliders().add(new Limit(ballImpl2, 0, 0, new Vector2(0, -1), 50));
		ballImpl2.getVelocity().set(0, -100);
		world.getSolids().add(ballImpl2);

		simulate(world);
	}

	@Test
	public void simpleLimitLimitX()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl ballImpl = new BouncingBallImpl(world);
		ballImpl.getPosition().set(200, 425);
		ballImpl.getColliders().add(new Limit(ballImpl, 0, 0, new Vector2(1, 0), 50));
		ballImpl.getVelocity().set(50, 0);
		world.getSolids().add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl(world);
		ballImpl2.getPosition().set(500, 400);
		ballImpl2.getColliders().add(new Limit(ballImpl2, 0, 0, new Vector2(-1, 0), 50));
		ballImpl2.getVelocity().set(-100, 0);
		world.getSolids().add(ballImpl2);

		simulate(world);
	}

	@Test
	public void angle45LimitLimit()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl ballImpl = new BouncingBallImpl(world);
		ballImpl.getPosition().set(200, 200);
		ballImpl.getColliders().add(new Limit(ballImpl, 0, 0, new Vector2(1, 1).nor(), 50));
		ballImpl.getVelocity().set(5, 5);
		world.getSolids().add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl(world);
		ballImpl2.getPosition().set(400, 400);
		ballImpl2.getColliders().add(new Limit(ballImpl2, 0, 0, new Vector2(-1, -1).nor(), 50));
		ballImpl2.getVelocity().set(-150, -150);
		world.getSolids().add(ballImpl2);

		simulate(world);
	}

	@Test
	public void angleAnyLimitLimit()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl ballImpl = new BouncingBallImpl(world);
		ballImpl.getPosition().set(200, 200);
		ballImpl.getColliders().add(new Limit(ballImpl, 0, 0, new Vector2(1, 0.8f).nor(), 50));
		ballImpl.getVelocity().set(5, 5);
		world.getSolids().add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl(world);
		ballImpl2.getPosition().set(400, 400);
		ballImpl2.getColliders().add(new Limit(ballImpl2, 0, 0, new Vector2(-0.9f, -1).nor(), 50));
		ballImpl2.getVelocity().set(-150, -150);
		world.getSolids().add(ballImpl2);

		simulate(world);
	}

	@Test
	public void straightVsAngled()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl ballImpl = new BouncingBallImpl(world);
		ballImpl.getPosition().set(400, 200);
		ballImpl.getColliders().add(new Limit(ballImpl, 0, 0, new Vector2(0, 1).nor(), 50));
		ballImpl.getVelocity().set(1, 60);
		world.getSolids().add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl(world);
		ballImpl2.getPosition().set(400, 400);
		ballImpl2.getColliders().add(new Limit(ballImpl2, 0, 0, new Vector2(-0.75f, -1).nor(), 50));
		ballImpl2.getVelocity().set(0, -150);
		world.getSolids().add(ballImpl2);

		simulate(world);
	}
}
