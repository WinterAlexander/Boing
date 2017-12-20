package me.winter.boing.simulation;

import me.winter.boing.colliders.Bound;
import me.winter.boing.impl.BodyImpl;
import me.winter.boing.impl.DynamicBodyImpl;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.testimpl.FlyingPlayerImpl;
import me.winter.boing.testimpl.TestWorldImpl;
import me.winter.boing.simulation.simulator.BoingSimulator;
import org.junit.Ignore;
import org.junit.Test;

import static me.winter.boing.util.VectorUtil.DOWN;
import static me.winter.boing.util.VectorUtil.LEFT;
import static me.winter.boing.util.VectorUtil.RIGHT;
import static me.winter.boing.util.VectorUtil.UP;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-22.
 */
@Ignore
public class FlyingPushableBoxSimulation
{
//	@Test
//	public void simpleBox()
//	{
//		TestWorldImpl testWorld = new TestWorldImpl(new ReplaceResolver());
//
//		FlyingPlayerImpl player = new FlyingPlayerImpl();
//		player.getPosition().set(400, 400);
//		player.addCollider(new Box(player, 0, 0, 50, 50));
//		testWorld.add(player);
//
//		DynamicBodyImpl solid = new DynamicBodyImpl();
//		solid.getPosition().set(200, 400);
//		solid.addCollider(new Box(solid, 0, 0, 50, 50));
//		testWorld.add(solid);
//
//		new BoingSimulator(testWorld, 60f).start();
//	}

	@Test
	public void simpleBoundBox()
	{
		TestWorldImpl testWorld = new TestWorldImpl(new ReplaceResolver());

		FlyingPlayerImpl player = new FlyingPlayerImpl();
		player.getPosition().set(400, 400);
		player.addCollider(new Bound(player, 0, 25, UP, 50));
		player.addCollider(new Bound(player, 0, -25, DOWN, 50));
		player.addCollider(new Bound(player, 25, 0, RIGHT, 50));
		player.addCollider(new Bound(player, -25, 0, LEFT, 50));
		testWorld.add(player);

		DynamicBodyImpl solid = new DynamicBodyImpl();
		solid.getPosition().set(200, 400);
		solid.addCollider(new Bound(solid, 0, 25, UP, 50));
		solid.addCollider(new Bound(solid, 0, -25, DOWN, 50));
		solid.addCollider(new Bound(solid, 25, 0, RIGHT, 50));
		solid.addCollider(new Bound(solid, -25, 0, LEFT, 50));
		testWorld.add(solid);

		new BoingSimulator(testWorld, 60f).start();
	}

	@Test
	public void uniDimensionnalCollisionShiftTest()
	{
		TestWorldImpl testWorld = new TestWorldImpl(new ReplaceResolver());

		FlyingPlayerImpl player = new FlyingPlayerImpl() {

			@Override
			public float getWeight()
			{
				return 1f;
			}
		};
		player.getPosition().set(400, 400);
		player.addCollider(new Bound(player, 25, 0, RIGHT, 50));
		testWorld.add(player);

		DynamicBodyImpl solid = new DynamicBodyImpl();
		solid.getPosition().set(500, 400);
		solid.addCollider(new Bound(solid, 25, 0, RIGHT, 50));
		solid.addCollider(new Bound(solid, -25, 0, LEFT, 50));
		testWorld.add(solid);

		DynamicBodyImpl solid2 = new DynamicBodyImpl();
		solid2.getPosition().set(600, 400);
		Bound toTest = new Bound(solid2, -25, 0, LEFT, 50);
		toTest.setTag("test");
		solid2.addCollider(toTest);
		testWorld.add(solid2);

		new BoingSimulator(testWorld, 60f).start();
	}

//	@Test
//	public void boxWithBounds()
//	{
//		TestWorldImpl testWorld = new TestWorldImpl(new ReplaceResolver());
//
//		FlyingPlayerImpl player = new FlyingPlayerImpl();
//		player.getPosition().set(400, 400);
//		player.addCollider(new Box(player, 0, 0, 50, 50));
//		testWorld.add(player);
//
//		DynamicBodyImpl solid = new DynamicBodyImpl();
//		solid.getPosition().set(200, 400);
//		solid.addCollider(new Box(solid, 0, 0, 50, 50));
//		testWorld.add(solid);
//
//		BodyImpl bound1 = new BodyImpl();
//		bound1.getPosition().set(600, 400);
//		bound1.addCollider(new Bound(bound1, 0, 0, LEFT, 40));
//		testWorld.add(bound1);
//
//		BodyImpl bound2 = new BodyImpl();
//		bound2.getPosition().set(400, 200);
//		bound2.addCollider(new Bound(bound2, 0, 0, UP, 50));
//		testWorld.add(bound2);
//
//		new BoingSimulator(testWorld, 60f).start();
//	}
//
//	@Test
//	public void lotsOfBoxesWithBounds()
//	{
//		TestWorldImpl testWorld = new TestWorldImpl(new ReplaceResolver());
//
//		FlyingPlayerImpl player = new FlyingPlayerImpl();
//		player.getPosition().set(500, 400);
//		player.addCollider(new Box(player, 0, 0, 50, 50));
//		testWorld.add(player);
//
//		for(int i = 0; i < 10; i++)
//		{
//			for(int j = 0; j < 5; j++)
//			{
//				DynamicBodyImpl solid = new DynamicBodyImpl();
//				solid.getPosition().set(i * 50 + 25, j * 50 + 25);
//				solid.addCollider(new Box(solid, 0, 0, 20, 20));
//				testWorld.add(solid);
//			}
//		}
//
//		BodyImpl bound1 = new BodyImpl();
//		bound1.getPosition().set(600, 400);
//		bound1.addCollider(new Bound(bound1, 0, 0, LEFT, 40));
//		testWorld.add(bound1);
//
//		BodyImpl bound2 = new BodyImpl();
//		bound2.getPosition().set(400, 200);
//		bound2.addCollider(new Bound(bound2, 0, 0, UP, 50));
//		testWorld.add(bound2);
//
//		BodyImpl solid = new BodyImpl();
//		solid.getPosition().set(600, 600);
//		solid.addCollider(new Box(solid, 0, 0, 50, 50));
//		testWorld.add(solid);
//
//		new BoingSimulator(testWorld, 60f).start();
//	}

	@Test
	public void lotsOfBoundBoxes()
	{
		TestWorldImpl testWorld = new TestWorldImpl(new ReplaceResolver());

		FlyingPlayerImpl player = new FlyingPlayerImpl()
		/*{
			@Override
			public float getWeight(DynamicBody other) {
				return Float.POSITIVE_INFINITY;
			}
		}//*/
		;
		player.getPosition().set(500, 400);
		player.addCollider(new Bound(player, 0, 25, UP, 50));
		player.addCollider(new Bound(player, 0, -25, DOWN, 50));
		player.addCollider(new Bound(player, 25, 0, RIGHT, 50));
		player.addCollider(new Bound(player, -25, 0, LEFT, 50));
		testWorld.add(player);

		for(int i = 0; i < 10; i++)
		{
			for(int j = 0; j < 5; j++)
			{
				DynamicBodyImpl solid = new DynamicBodyImpl();
				solid.getPosition().set(i * 50 + 25, j * 50 + 25);
				solid.addCollider(new Bound(solid, 0, 10, UP, 20));
				solid.addCollider(new Bound(solid, 0, -10, DOWN, 20));
				solid.addCollider(new Bound(solid, 10, 0, RIGHT, 20));
				solid.addCollider(new Bound(solid, -10, 0, LEFT, 20));
				testWorld.add(solid);
			}
		}

		BodyImpl bound1 = new BodyImpl();
		bound1.getPosition().set(600, 400);
		bound1.addCollider(new Bound(bound1, 0, 0, LEFT, 40));
		testWorld.add(bound1);

		BodyImpl bound2 = new BodyImpl();
		bound2.getPosition().set(400, 200);
		bound2.addCollider(new Bound(bound2, 0, 0, UP, 50));
		testWorld.add(bound2);

		new BoingSimulator(testWorld, 60f).start();
	}
}
