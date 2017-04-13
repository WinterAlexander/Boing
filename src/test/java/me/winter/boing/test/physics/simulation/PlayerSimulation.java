package me.winter.boing.test.physics.simulation;

import me.winter.boing.physics.World;
import me.winter.boing.physics.resolver.ReplaceResolver;
import me.winter.boing.physics.shapes.AABB;
import me.winter.boing.test.physics.testimpl.PlayerImpl;
import me.winter.boing.test.physics.testimpl.SolidImpl;
import me.winter.boing.test.physics.testimpl.SpringImpl;
import org.junit.Test;

import static me.winter.boing.test.physics.simulation.WorldSimulationUtil.simulate;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-13.
 */
public class PlayerSimulation
{
	@Test
	public void simpleSimul()
	{
		World world = new World(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl(world);

		player.getPosition().set(400, 400);
		player.getColliders().add(new AABB(player, 0, 0, 20, 45));
		world.getSolids().add(player);

		SolidImpl ground = new SolidImpl(world);

		ground.getPosition().set(400, 0);
		ground.getColliders().add(new AABB(ground, 0, 0, 800, 100));
		world.getSolids().add(ground);

		simulate(world);
	}

	@Test
	public void springSimul()
	{
		World world = new World(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl(world);

		player.getPosition().set(400, 400);
		player.getColliders().add(new AABB(player, 0, 0, 20, 45));
		world.getSolids().add(player);

		SolidImpl ground = new SolidImpl(world);

		ground.getPosition().set(400, 0);
		ground.getColliders().add(new AABB(ground, 0, 0, 800, 100));
		world.getSolids().add(ground);

		SpringImpl spring = new SpringImpl(world);
		spring.getPosition().set(600, 50);
		spring.getColliders().add(new AABB(spring, 0, 0, 100, 100));
		world.getSolids().add(spring);

		simulate(world);
	}
}
