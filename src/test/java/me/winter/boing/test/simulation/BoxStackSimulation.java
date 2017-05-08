package me.winter.boing.test.simulation;

import me.winter.boing.impl.WorldImpl;
import me.winter.boing.impl.BodyImpl;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.shapes.Box;
import me.winter.boing.test.testimpl.GravityAffected;
import me.winter.boing.test.testimpl.PlayerImpl;
import org.junit.Test;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-08.
 */
public class BoxStackSimulation
{
	@Test
	public void simpleBoxStack()
	{
		WorldImpl world = new WorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();

		player.getPosition().set(400, 400);
		player.addCollider(new Box(player, 0, 0, 20, 45));
		world.add(player);

		GravityAffected test = new GravityAffected();
		test.getPosition().set(400, 100);
		test.addCollider(new Box(test, 0, 0, 50, 50));
		world.add(test);

		BodyImpl ground = new BodyImpl();

		ground.getPosition().set(400, 0);
		ground.addCollider(new Box(ground, 0, 0, 800, 400));
		world.add(ground);

		WorldSimulationUtil.simulate(world);
	}

	@Test
	public void bigBoxStack()
	{
		WorldImpl world = new WorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();

		player.getPosition().set(400, 600);
		player.addCollider(new Box(player, 0, 0, 20, 45));
		world.add(player);

		GravityAffected test = new GravityAffected();
		test.getPosition().set(400, 500);
		test.addCollider(new Box(test, 0, 0, 30, 30));
		world.add(test);

		GravityAffected test2 = new GravityAffected();
		test2.getPosition().set(400, 400);
		test2.addCollider(new Box(test2, 0, 0, 30, 30));
		world.add(test2);

		GravityAffected test3 = new GravityAffected();
		test3.getPosition().set(400, 300);
		test3.addCollider(new Box(test3, 0, 0, 50, 50));
		world.add(test3);

		BodyImpl ground = new BodyImpl();

		ground.getPosition().set(400, 0);
		ground.addCollider(new Box(ground, 0, 0, 800, 400));
		world.add(ground);

		WorldSimulationUtil.simulate(world);
	}
}
