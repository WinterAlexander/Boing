package me.winter.boing.simulation;

import me.winter.boing.colliders.Box;
import me.winter.boing.colliders.Limit;
import me.winter.boing.impl.BodyImpl;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.testimpl.GravityAffected;
import me.winter.boing.testimpl.PlayerImpl;
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
 * Created by Alexander Winter on 23/06/17.
 */
@Ignore
public class TiledTerrainSimulation
{
	@Test
	public void simpleGroundTest()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

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


		new WorldSimulation(world, 60f).start();
	}

	@Test
	public void tiledGroundAndWalls()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

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


		new WorldSimulation(world, 60f).start();
	}
}
