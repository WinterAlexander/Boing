package me.winter.boing.test.physics.simulation;

import me.winter.boing.test.physics.testimpl.WorldImpl;
import me.winter.boing.physics.resolver.ReplaceResolver;
import me.winter.boing.physics.shapes.Box;
import me.winter.boing.test.physics.testimpl.PlayerImpl;
import me.winter.boing.test.physics.testimpl.SolidImpl;
import me.winter.boing.test.physics.testimpl.SpringImpl;
import org.junit.Ignore;
import org.junit.Test;

import static me.winter.boing.test.physics.simulation.WorldSimulationUtil.simulate;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-13.
 */
@Ignore
public class PlayerSimulation
{
	@Test
	public void simpleSimul()
	{
		WorldImpl world = new WorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();

		player.getPosition().set(400, 400);
		player.getColliders().add(new Box(player, 0, 0, 20, 45));
		world.getSolids().add(player);

		SolidImpl ground = new SolidImpl();

		ground.getPosition().set(400, 0);
		ground.getColliders().add(new Box(ground, 0, 0, 800, 100));
		world.getSolids().add(ground);

		simulate(world);
	}

	@Test
	public void springSimul()
	{
		WorldImpl world = new WorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();

		player.getPosition().set(400, 400);
		player.getColliders().add(new Box(player, 0, 0, 20, 45));
		world.getSolids().add(player);

		SolidImpl ground = new SolidImpl();

		ground.getPosition().set(400, 0);
		ground.getColliders().add(new Box(ground, 0, 0, 800, 100));
		world.getSolids().add(ground);

		SpringImpl spring = new SpringImpl();
		spring.getPosition().set(600, 50);
		spring.getColliders().add(new Box(spring, 0, 0, 100, 100));
		world.getSolids().add(spring);

		simulate(world);
	}
}
