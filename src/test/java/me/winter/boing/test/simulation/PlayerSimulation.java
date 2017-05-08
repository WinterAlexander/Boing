package me.winter.boing.test.simulation;

import me.winter.boing.impl.WorldImpl;
import me.winter.boing.resolver.CollisionResolver;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.shapes.Box;
import me.winter.boing.test.testimpl.PlayerImpl;
import me.winter.boing.impl.BodyImpl;
import me.winter.boing.test.testimpl.SpringImpl;
import org.junit.Ignore;
import org.junit.Test;

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
		CollisionResolver resolver = new ReplaceResolver();
		WorldImpl world = new WorldImpl(resolver);

		PlayerImpl player = new PlayerImpl();

		player.getPosition().set(400, 400);
		player.addCollider(new Box(player, 0, 0, 20, 45));
		world.add(player);

		BodyImpl ground = new BodyImpl();

		ground.getPosition().set(400, 0);
		ground.addCollider(new Box(ground, 0, 0, 800, 100));
		world.add(ground);

		WorldSimulationUtil.simulate(world);
	}

	@Test
	public void springSimul()
	{
		CollisionResolver resolver = new ReplaceResolver();
		WorldImpl world = new WorldImpl(resolver);

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

		WorldSimulationUtil.simulate(world);
	}
}
