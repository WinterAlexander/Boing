package me.winter.boing.simulation;

import me.winter.boing.colliders.Box;
import me.winter.boing.impl.BodyImpl;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.testimpl.GravityAffected;
import me.winter.boing.testimpl.PlayerImpl;
import me.winter.boing.testimpl.TestPhysicsWorldImpl;
import me.winter.boing.simulation.simulator.BoingSimulator;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 23/06/17.
 */
@Ignore
public class TiledTerrainSimulation
{
	@Test
	public void simpleGroundTest()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 200);
		player.addCollider(new Box(player, 0, 0, 40, 50));
		world.add(player);

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile = new BodyImpl();
			tile.getPosition().set(i * 40, 100);
			tile.addCollider(new Box(tile, 0, 0, 40, 40));
			world.add(tile);
		}


		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void tiledGroundAndWalls()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 200);
		player.addCollider(new Box(player, 0, 0, 40, 50));
		world.add(player);

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile = new BodyImpl();
			tile.getPosition().set(i * 40, 100);
			tile.addCollider(new Box(tile, 0, 0, 40, 40));
			world.add(tile);
		}

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile = new BodyImpl();
			tile.getPosition().set(20, i * 40);
			tile.addCollider(new Box(tile, 0, 0, 40, 40));
			world.add(tile);

			BodyImpl tile2 = new BodyImpl();
			tile2.getPosition().set(780, i * 40);
			tile2.addCollider(new Box(tile2, 0, 0, 40, 40));
			world.add(tile2);
		}


		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void tiledGroundAndWallsWithPushables()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 200);
		player.addCollider(new Box(player, 0, 0, 40, 50));
		world.add(player);

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile = new BodyImpl();
			tile.getPosition().set(i * 40, 100);
			tile.addCollider(new Box(tile, 0, 0, 40, 40));
			world.add(tile);
		}

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile = new BodyImpl();
			tile.getPosition().set(20, i * 40);
			tile.addCollider(new Box(tile, 0, 0, 40, 40));
			world.add(tile);

			BodyImpl tile2 = new BodyImpl();
			tile2.getPosition().set(780, i * 40);
			tile2.addCollider(new Box(tile2, 0, 0, 40, 40));
			world.add(tile2);
		}

		for(int j = 0; j < 8; j++)
		{

			for(int i = 0; i < 10; i++)
			{
				GravityAffected test = new GravityAffected();
				test.getPosition().set(200 + j * 50, 750 - i * 50);
				test.addCollider(new Box(test, 0, 0, 30, 30));
				world.add(test);
			}
		}

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void tiledGroundAndWallsWithLessPushables()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 200);
		player.addCollider(new Box(player, 0, 0, 40, 50));
		world.add(player);

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile = new BodyImpl();
			tile.getPosition().set(i * 40, 100);
			tile.addCollider(new Box(tile, 0, 0, 40, 40));
			world.add(tile);
		}

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile2 = new BodyImpl();
			tile2.getPosition().set(780, i * 40);
			tile2.addCollider(new Box(tile2, 0, 0, 40, 40));
			world.add(tile2);
		}

		for(int i = 0; i < 4; i++)
		{
			GravityAffected test = new GravityAffected();
			test.getPosition().set(720, 400 - i * 50);
			test.addCollider(new Box(test, 0, 0, 30, 30));
			world.add(test);
		}

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void tiledGroundAndWallsWalkingOnPushables()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 200);
		player.addCollider(new Box(player, 0, 0, 40, 50));
		world.add(player);

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile = new BodyImpl();
			tile.getPosition().set(i * 40, 100);
			tile.addCollider(new Box(tile, 0, 0, 40, 40));
			world.add(tile);
		}

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile2 = new BodyImpl();
			tile2.getPosition().set(780, i * 40);
			tile2.addCollider(new Box(tile2, 0, 0, 40, 40));
			world.add(tile2);
		}

		for(int i = 0; i < 4; i++)
		{
			GravityAffected test = new GravityAffected();
			test.getPosition().set(700 - i * 50, 200);
			test.addCollider(new Box(test, 0, 0, 30, 30));
			world.add(test);
		}

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void tiledGroundAndWallsAnd2Boxes()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 200);
		player.addCollider(new Box(player, 0, 0, 40, 50));
		world.add(player);

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile = new BodyImpl();
			tile.getPosition().set(i * 40, 100);
			tile.addCollider(new Box(tile, 0, 0, 40, 40));
			world.add(tile);
		}

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile2 = new BodyImpl();
			tile2.getPosition().set(780, i * 40);
			tile2.addCollider(new Box(tile2, 0, 0, 40, 40));
			world.add(tile2);
		}

		GravityAffected test = new GravityAffected();
		test.getPosition().set(700, 200);
		test.addCollider(new Box(test, 0, 0, 30, 30));
		test.getColliders()[0].setTag("DABOX");
		world.add(test);

		GravityAffected test2 = new GravityAffected();
		test2.getPosition().set(600, 200);
		test2.addCollider(new Box(test2, 0, 0, 30, 30));
		world.add(test2);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void pushingBoxOnPushables()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 250);
		player.addCollider(new Box(player, 0, 0, 40, 50));
		world.add(player);

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile = new BodyImpl();
			tile.getPosition().set(i * 40, 100);
			tile.addCollider(new Box(tile, 0, 0, 40, 40));
			world.add(tile);
		}

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile2 = new BodyImpl();
			tile2.getPosition().set(780, i * 40);
			tile2.addCollider(new Box(tile2, 0, 0, 40, 40));
			world.add(tile2);
		}

		for(int i = 0; i < 10; i++)
		{
			GravityAffected pushableGround = new GravityAffected();
			pushableGround.getPosition().set(700 - i * 30, 135);
			pushableGround.addCollider(new Box(pushableGround, 0, 0, 30, 30));
			world.add(pushableGround);
		}

		GravityAffected test = new GravityAffected();
		test.getPosition().set(500, 250);
		test.addCollider(new Box(test, 0, 0, 30, 30));
		test.getColliders()[0].setTag("DABOX");
		world.add(test);

		BodyImpl bound = new BodyImpl();
		bound.getPosition().set(400, 150);
		bound.addCollider(new Box(bound, 0, 0, 30, 60));
		world.add(bound);

		BodyImpl bound2 = new BodyImpl();
		bound2.getPosition().set(730, 150);
		bound2.addCollider(new Box(bound2, 0, 0, 30, 60));
		world.add(bound2);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void pushingBoxOnPushablesAndSolids()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 250);
		player.addCollider(new Box(player, 0, 0, 40, 50));
		world.add(player);

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile = new BodyImpl();
			tile.getPosition().set(i * 40, 100);
			tile.addCollider(new Box(tile, 0, 0, 40, 40));
			world.add(tile);
		}

		for(int i = 0; i < 20; i++)
		{
			BodyImpl tile2 = new BodyImpl();
			tile2.getPosition().set(780, i * 40);
			tile2.addCollider(new Box(tile2, 0, 0, 40, 40));
			world.add(tile2);
		}

		for(int i = 0; i < 10; i++)
		{
			if(i % 2 == 0)
			{
				GravityAffected pushableGround = new GravityAffected();
				pushableGround.getPosition().set(700 - i * 30, 135);
				pushableGround.addCollider(new Box(pushableGround, 0, 0, 30, 30));
				world.add(pushableGround);
			}
			else
			{
				BodyImpl solidGround = new BodyImpl();
				solidGround.getPosition().set(700 - i * 30, 135);
				solidGround.addCollider(new Box(solidGround, 0, 0, 30, 30));
				world.add(solidGround);
			}
		}

		GravityAffected test = new GravityAffected();
		test.getPosition().set(500, 250);
		test.addCollider(new Box(test, 0, 0, 30, 30));
		test.getColliders()[0].setTag("DABOX");
		world.add(test);

		BodyImpl bound = new BodyImpl();
		bound.getPosition().set(400, 150);
		bound.addCollider(new Box(bound, 0, 0, 30, 60));
		world.add(bound);

		BodyImpl bound2 = new BodyImpl();
		bound2.getPosition().set(730, 150);
		bound2.addCollider(new Box(bound2, 0, 0, 30, 60));
		world.add(bound2);

		new BoingSimulator(world, 1f).start();
	}
}
