package me.winter.boing.test.physics.simulation;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.World;
import me.winter.boing.physics.resolver.ReplaceResolver;
import me.winter.boing.physics.shapes.Limit;
import me.winter.boing.test.physics.testimpl.BouncingBallImpl;
import me.winter.boing.test.physics.testimpl.PlayerImpl;
import me.winter.boing.test.physics.testimpl.SolidImpl;
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
	public void angle45ButStraightVelLimitLimit()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl ballImpl = new BouncingBallImpl(world);
		ballImpl.getPosition().set(400, 200);
		ballImpl.getColliders().add(new Limit(ballImpl, 0, 0, new Vector2(1, 0.5f).nor(), 50));
		ballImpl.getVelocity().set(0, 50);
		world.getSolids().add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl(world);
		ballImpl2.getPosition().set(400, 500);
		ballImpl2.getColliders().add(new Limit(ballImpl2, 0, 0, new Vector2(-1, -0.1f).nor(), 50));
		ballImpl2.getVelocity().set(0, -100);
		world.getSolids().add(ballImpl2);

		BouncingBallImpl ballImpl3 = new BouncingBallImpl(world);
		ballImpl3.getPosition().set(100, 200);
		ballImpl3.getColliders().add(new Limit(ballImpl3, 0, 0, new Vector2(1, 10).nor(), 50));
		ballImpl3.getVelocity().set(0, 50);
		world.getSolids().add(ballImpl3);

		BouncingBallImpl ballImpl4 = new BouncingBallImpl(world);
		ballImpl4.getPosition().set(105, 500);
		ballImpl4.getColliders().add(new Limit(ballImpl4, 0, 0, new Vector2(-0.2f, -1).nor(), 50));
		ballImpl4.getVelocity().set(0, -100);
		world.getSolids().add(ballImpl4);

		BouncingBallImpl ballImpl5 = new BouncingBallImpl(world);
		ballImpl5.getPosition().set(600, 200);
		ballImpl5.getColliders().add(new Limit(ballImpl5, 0, 0, new Vector2(1, 1).nor(), 50));
		ballImpl5.getVelocity().set(0, 50);
		world.getSolids().add(ballImpl5);

		BouncingBallImpl ballImpl6 = new BouncingBallImpl(world);
		ballImpl6.getPosition().set(620, 500);
		ballImpl6.getColliders().add(new Limit(ballImpl6, 0, 0, new Vector2(-0.9f, -1).nor(), 50));
		ballImpl6.getVelocity().set(0, -100);
		world.getSolids().add(ballImpl6);

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

	@Test
	public void sloppeTerrain()
	{
		World world = new World(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl(world);

		player.getPosition().set(400, 400);
		player.getColliders().add(new Limit(player, 0, -25, new Vector2(0, -1).nor(), 50));
		player.getColliders().add(new Limit(player, 25, 0, new Vector2(1, 0).nor(), 50));
		player.getColliders().add(new Limit(player, -25, 0, new Vector2(-1, 0).nor(), 50));
		world.getSolids().add(player);

		SolidImpl ground = new SolidImpl(world);

		ground.getPosition().set(400, 100);
		ground.getColliders().add(new Limit(ground, 0, 0, new Vector2(0, 1), 800));
		world.getSolids().add(ground);

		simulate(world);
	}
}
