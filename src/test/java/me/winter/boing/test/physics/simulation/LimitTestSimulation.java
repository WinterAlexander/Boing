package me.winter.boing.test.physics.simulation;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.SimpleWorld;
import me.winter.boing.physics.shapes.Box;
import me.winter.boing.physics.shapes.Circle;
import me.winter.boing.test.physics.testimpl.DynamicBodyImpl;
import me.winter.boing.physics.resolver.ReplaceResolver;
import me.winter.boing.physics.shapes.Limit;
import me.winter.boing.test.physics.testimpl.BouncingBallImpl;
import org.junit.Ignore;
import org.junit.Test;

import static me.winter.boing.test.physics.simulation.WorldSimulationUtil.simulate;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-13.
 */
@Ignore
public class LimitTestSimulation
{
	@Test
	public void simpleLimitLimitY()
	{
		me.winter.boing.physics.resolver.CollisionResolver resolver = new ReplaceResolver();
		SimpleWorld world = new SimpleWorld(resolver);

		BouncingBallImpl ballImpl = new BouncingBallImpl();
		ballImpl.getPosition().set(425, 200);
		ballImpl.addCollider(new Limit(ballImpl, 0, 0, new Vector2(0, 1), 50));
		ballImpl.getVelocity().set(0, 50);
		world.add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl();
		ballImpl2.getPosition().set(400, 500);
		ballImpl2.addCollider(new Limit(ballImpl2, 0, 0, new Vector2(0, -1), 50));
		ballImpl2.getVelocity().set(0, -100);
		world.add(ballImpl2);

		simulate(world);
	}

	@Test
	public void simpleLimitLimitX()
	{
		me.winter.boing.physics.resolver.CollisionResolver resolver = new ReplaceResolver();
		SimpleWorld world = new SimpleWorld(resolver);

		BouncingBallImpl ballImpl = new BouncingBallImpl();
		ballImpl.getPosition().set(200, 425);
		ballImpl.addCollider(new Limit(ballImpl, 0, 0, new Vector2(1, 0), 50));
		ballImpl.getVelocity().set(50, 0);
		world.add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl();
		ballImpl2.getPosition().set(500, 400);
		ballImpl2.addCollider(new Limit(ballImpl2, 0, 0, new Vector2(-1, 0), 50));
		ballImpl2.getVelocity().set(-100, 0);
		world.add(ballImpl2);

		simulate(world);
	}

	@Test
	public void comingAtAAngleLimitLimitX()
	{
		me.winter.boing.physics.resolver.CollisionResolver resolver = new ReplaceResolver();
		SimpleWorld world = new SimpleWorld(resolver);

		BouncingBallImpl ballImpl = new BouncingBallImpl();
		ballImpl.getPosition().set(200, 200);
		ballImpl.addCollider(new Limit(ballImpl, 0, 0, new Vector2(1, 0), 50));
		ballImpl.getVelocity().set(50, 10);
		world.add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl();
		ballImpl2.getPosition().set(500, 100);
		ballImpl2.addCollider(new Limit(ballImpl2, 0, 0, new Vector2(-1, 0), 50));
		ballImpl2.getVelocity().set(-100, 50);
		world.add(ballImpl2);

		simulate(world);
	}

	@Test
	public void cornerGlitch()
	{
		me.winter.boing.physics.resolver.CollisionResolver resolver = new ReplaceResolver();
		SimpleWorld world = new SimpleWorld(resolver);

		BouncingBallImpl ballImpl = new BouncingBallImpl();

		ballImpl.getPosition().set(50, 50);
		ballImpl.addCollider(new Limit(ballImpl, 25, 0, new Vector2(1, 0), 50));
		ballImpl.addCollider(new Limit(ballImpl, 0, 25, new Vector2(0, 1), 50));
		ballImpl.getVelocity().set(50, 50);

		world.add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl();

		ballImpl2.getPosition().set(500, 500);
		ballImpl2.addCollider(new Limit(ballImpl2, -25, 0, new Vector2(-1, 0), 50));
		ballImpl2.addCollider(new Limit(ballImpl2, 0, -25, new Vector2(0, -1), 50));
		ballImpl2.getVelocity().set(-100, -100);

		world.add(ballImpl2);

		simulate(world);
	}

	@Test
	public void limitCircle()
	{
		me.winter.boing.physics.resolver.CollisionResolver resolver = new ReplaceResolver();
		SimpleWorld world = new SimpleWorld(resolver);

		BouncingBallImpl ballImpl = new BouncingBallImpl(5f) {
			@Override
			public void notifyCollision(Collision collision) {}
		};

		ballImpl.getPosition().set(400, 0);
		ballImpl.addCollider(new Limit(ballImpl, 0, 50, new Vector2(0, 1), 800));
		world.add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl(0.5f) {
			@Override
			public void notifyCollision(Collision collision) {}
		};

		ballImpl2.getPosition().set(500, 500);
		ballImpl2.addCollider(new Circle(ballImpl2, 0, 0, 25));
		ballImpl2.getVelocity().set(-100, -100);
		world.add(ballImpl2);

		simulate(world);
	}

	@Test
	public void limitBox()
	{
		me.winter.boing.physics.resolver.CollisionResolver resolver = new ReplaceResolver();
		SimpleWorld world = new SimpleWorld(resolver);

		DynamicBodyImpl ballImpl = new DynamicBodyImpl(5f);

		ballImpl.getPosition().set(400, 0);
		ballImpl.addCollider(new Limit(ballImpl, 0, 50, new Vector2(0, 1), 800));
		world.add(ballImpl);

		DynamicBodyImpl ballImpl2 = new DynamicBodyImpl(0.5f);

		ballImpl2.getPosition().set(500, 500);
		ballImpl2.addCollider(new Box(ballImpl2, 0, 0, 25, 25));
		ballImpl2.getVelocity().set(-10, -100);
		world.add(ballImpl2);

		simulate(world);
	}
}
