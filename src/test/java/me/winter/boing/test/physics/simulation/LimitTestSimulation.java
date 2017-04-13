package me.winter.boing.test.physics.simulation;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.World;
import me.winter.boing.physics.resolver.ReplaceResolver;
import me.winter.boing.physics.shapes.Limit;
import me.winter.boing.test.physics.testimpl.BouncingBallImpl;
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
	public void simpleLimitLimit()
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
}
