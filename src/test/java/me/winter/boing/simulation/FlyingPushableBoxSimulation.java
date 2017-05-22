package me.winter.boing.simulation;

import me.winter.boing.colliders.Box;
import me.winter.boing.colliders.Limit;
import me.winter.boing.impl.BodyImpl;
import me.winter.boing.impl.DynamicBodyImpl;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.testimpl.FlyingPlayerImpl;
import me.winter.boing.testimpl.TestWorldImpl;
import me.winter.boing.util.WorldSimulationUtil;
import org.junit.Test;

import static me.winter.boing.util.VectorUtil.LEFT;
import static me.winter.boing.util.VectorUtil.UP;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-22.
 */
public class FlyingPushableBoxSimulation
{
	@Test
	public void simpleBox()
	{
		TestWorldImpl testWorld = new TestWorldImpl(new ReplaceResolver());

		FlyingPlayerImpl player = new FlyingPlayerImpl();
		player.getPosition().set(400, 400);
		player.addCollider(new Box(player, 0, 0, 50, 50));
		testWorld.add(player);

		DynamicBodyImpl solid = new DynamicBodyImpl();
		solid.getPosition().set(200, 400);
		solid.addCollider(new Box(solid, 0, 0, 50, 50));
		testWorld.add(solid);

		WorldSimulationUtil.simulate(testWorld);
	}

	@Test
	public void boxWithLimits()
	{
		TestWorldImpl testWorld = new TestWorldImpl(new ReplaceResolver());

		FlyingPlayerImpl player = new FlyingPlayerImpl();
		player.getPosition().set(400, 400);
		player.addCollider(new Box(player, 0, 0, 50, 50));
		testWorld.add(player);

		DynamicBodyImpl solid = new DynamicBodyImpl();
		solid.getPosition().set(200, 400);
		solid.addCollider(new Box(solid, 0, 0, 50, 50));
		testWorld.add(solid);

		BodyImpl limit1 = new BodyImpl();
		limit1.getPosition().set(600, 400);
		limit1.addCollider(new Limit(limit1, 0, 0, LEFT, 40));
		testWorld.add(limit1);

		BodyImpl limit2 = new BodyImpl();
		limit2.getPosition().set(400, 200);
		limit2.addCollider(new Limit(limit2, 0, 0, UP, 50));
		testWorld.add(limit2);

		WorldSimulationUtil.simulate(testWorld);
	}

	@Test
	public void lotsOfBoxesWithLimits()
	{
		TestWorldImpl testWorld = new TestWorldImpl(new ReplaceResolver());

		FlyingPlayerImpl player = new FlyingPlayerImpl();
		player.getPosition().set(500, 400);
		player.addCollider(new Box(player, 0, 0, 50, 50));
		testWorld.add(player);

		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				DynamicBodyImpl solid = new DynamicBodyImpl();
				solid.getPosition().set(i * 50 + 25, j * 50 + 25);
				solid.addCollider(new Box(solid, 0, 0, 20, 20));
				testWorld.add(solid);
			}
		}

		BodyImpl limit1 = new BodyImpl();
		limit1.getPosition().set(600, 400);
		limit1.addCollider(new Limit(limit1, 0, 0, LEFT, 40));
		testWorld.add(limit1);

		BodyImpl limit2 = new BodyImpl();
		limit2.getPosition().set(400, 200);
		limit2.addCollider(new Limit(limit2, 0, 0, UP, 50));
		testWorld.add(limit2);

		WorldSimulationUtil.simulate(testWorld);
	}
}
