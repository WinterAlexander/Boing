package me.winter.boing.test.physics.simulation;

import me.winter.boing.physics.SimpleWorld;
import me.winter.boing.physics.resolver.ReplaceResolver;
import me.winter.boing.physics.shapes.Box;
import me.winter.boing.test.physics.testimpl.PlayerImpl;
import me.winter.boing.test.physics.testimpl.BodyImpl;
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
		me.winter.boing.physics.resolver.CollisionResolver resolver = new ReplaceResolver();
		SimpleWorld world = new SimpleWorld(resolver);

		PlayerImpl player = new PlayerImpl();

		player.getPosition().set(400, 400);
		player.addCollider(new Box(player, 0, 0, 20, 45));
		world.add(player);

		BodyImpl ground = new BodyImpl();

		ground.getPosition().set(400, 0);
		ground.addCollider(new Box(ground, 0, 0, 800, 100));
		world.add(ground);

		simulate(world);
	}

	@Test
	public void springSimul()
	{
		me.winter.boing.physics.resolver.CollisionResolver resolver = new ReplaceResolver();
		SimpleWorld world = new SimpleWorld(resolver);

		PlayerImpl player = new PlayerImpl();

		player.getPosition().set(400, 400);
		player.addCollider(new Box(player, 0, 0, 20, 45));
		world.add(player);

		BodyImpl ground = new BodyImpl();

		ground.getPosition().set(400, 0);
		ground.addCollider(new Box(ground, 0, 0, 800, 100));
		world.add(ground);

		SpringImpl spring = new SpringImpl();
		spring.getPosition().set(600, 50);
		spring.addCollider(new Box(spring, 0, 0, 100, 100));
		world.add(spring);

		simulate(world);
	}
}
