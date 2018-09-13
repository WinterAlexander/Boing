package me.winter.boing.simulation;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.colliders.Box;
import me.winter.boing.colliders.Limit;
import me.winter.boing.impl.BodyImpl;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.simulation.simulator.BoingSimulator;
import me.winter.boing.testimpl.BouncingBallImpl;
import me.winter.boing.testimpl.GravityAffected;
import me.winter.boing.testimpl.PlayerImpl;
import me.winter.boing.testimpl.TestPhysicsWorldImpl;
import org.junit.Ignore;
import org.junit.Test;

import static me.winter.boing.util.VectorUtil.LEFT;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-07-15.
 */
@Ignore
public class GlitchSimulation
{
	@Test
	public void cornerGlitch()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());

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

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void boxCornerGlitch()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());

		BouncingBallImpl ballImpl = new BouncingBallImpl();

		ballImpl.getPosition().set(50, 50);
		ballImpl.addCollider(new Box(ballImpl, 0, 0, 50, 50));
		ballImpl.getVelocity().set(50, 50);

		world.add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl();

		ballImpl2.getPosition().set(500, 500);
		ballImpl2.addCollider(new Box(ballImpl2, 0, 0, 50, 50));
		ballImpl2.getVelocity().set(-100, -100);

		world.add(ballImpl2);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void boxLimitCornerGlitch()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());

		BouncingBallImpl ballImpl = new BouncingBallImpl();

		ballImpl.getPosition().set(50, 50);
		ballImpl.addCollider(new Box(ballImpl, 0, 0, 50, 50));
		ballImpl.getVelocity().set(50, 50);

		world.add(ballImpl);

		BouncingBallImpl ballImpl2 = new BouncingBallImpl();

		ballImpl2.getPosition().set(500, 500);
		ballImpl2.addCollider(new Limit(ballImpl2, -25, 0, new Vector2(-1, 0), 50));
		ballImpl2.addCollider(new Limit(ballImpl2, 0, -25, new Vector2(0, -1), 50));
		ballImpl2.getVelocity().set(-100, -100);

		world.add(ballImpl2);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void passingThroughBottomWhilePushingOnWall()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());


		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 200);
		player.addCollider(new Box(player, 0, 25, 40, 50));
		world.add(player);

		BodyImpl ground = new BodyImpl();
		ground.getPosition().set(400, 175);
		ground.addCollider(new Box(ground, 0, 0, 200, 50));
		world.add(ground);

		BodyImpl wall = new BodyImpl();
		wall.getPosition().set(450, 200);
		wall.addCollider(new Box(wall, 0, 40, 60, 80));
		world.add(wall);

		GravityAffected pushable = new GravityAffected();
		pushable.getPosition().set(425, 280);
		pushable.addCollider(new Box(pushable, 0, 15, 30, 30));
		world.add(pushable);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void pushingThroughWall()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 200);
		player.addCollider(new Box(player, 0, 25, 40, 50));
		world.add(player);

		BodyImpl ground = new BodyImpl();
		ground.getPosition().set(400, 175);
		ground.addCollider(new Box(ground, 0, 0, 200, 50));
		world.add(ground);

		BodyImpl wall = new BodyImpl();
		wall.getPosition().set(450, 200);
		wall.addCollider(new Box(wall, 0, 20, 30, 40));
		world.add(wall);

		GravityAffected pushable = new GravityAffected();
		pushable.getPosition().set(450, 240);
		pushable.addCollider(new Box(pushable, 0, 15, 30, 30));
		world.add(pushable);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void pushingThroughLimit()
	{
		TestPhysicsWorldImpl world = new TestPhysicsWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 200);
		player.addCollider(new Box(player, 0, 25, 40, 50));
		world.add(player);

		BodyImpl ground = new BodyImpl();
		ground.getPosition().set(400, 175);
		ground.addCollider(new Box(ground, 0, 0, 200, 50));
		world.add(ground);

		BodyImpl wall = new BodyImpl();
		wall.getPosition().set(450, 200);
		wall.addCollider(new Limit(wall, 0, 20, LEFT, 40));
		world.add(wall);

		GravityAffected pushable = new GravityAffected();
		pushable.getPosition().set(455, 200);
		pushable.addCollider(new Box(pushable, 0, 15, 30, 30));
		world.add(pushable);

		new BoingSimulator(world, 60f).start();
	}
}
