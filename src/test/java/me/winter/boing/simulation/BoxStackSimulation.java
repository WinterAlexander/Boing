package me.winter.boing.simulation;

import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;
import me.winter.boing.impl.BodyImpl;
import me.winter.boing.impl.DynamicBodyImpl;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.shapes.Box;
import me.winter.boing.shapes.Limit;
import me.winter.boing.testimpl.GravityAffected;
import me.winter.boing.testimpl.PlayerImpl;
import me.winter.boing.testimpl.TestWorldImpl;
import me.winter.boing.util.WorldSimulationUtil;
import org.junit.Ignore;
import org.junit.Test;

import static me.winter.boing.testimpl.PlayerImpl.IsKeyPressed.aPressed;
import static me.winter.boing.testimpl.PlayerImpl.IsKeyPressed.dPressed;
import static me.winter.boing.testimpl.PlayerImpl.IsKeyPressed.jumpPressed;
import static me.winter.boing.util.VectorUtil.DOWN;
import static me.winter.boing.util.VectorUtil.LEFT;
import static me.winter.boing.util.VectorUtil.RIGHT;
import static me.winter.boing.util.VectorUtil.UP;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-08.
 */
@Ignore
public class BoxStackSimulation
{
	@Test
	public void simpleBoxStack()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();

		player.getPosition().set(400, 400);
		player.addCollider(new Box(player, 0, 0, 20, 45));
		world.add(player);

		GravityAffected test = new GravityAffected();
		test.getPosition().set(400, 200);
		test.addCollider(new Box(test, 0, 0, 50, 50));
		world.add(test);

		BodyImpl ground = new BodyImpl();

		ground.getPosition().set(400, -100);
		ground.addCollider(new Box(ground, 0, 0, 800, 400));
		world.add(ground);

		WorldSimulationUtil.simulate(world);
	}

	@Test
	public void shortBoxStack()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();

		player.getPosition().set(400, 200);
		player.addCollider(new Box(player, 0, 0, 20, 45));
		world.add(player);

		GravityAffected test = new GravityAffected();
		test.getPosition().set(400, 150);
		test.addCollider(new Box(test, 0, 0, 50, 50));
		world.add(test);

		BodyImpl ground = new BodyImpl();

		ground.getPosition().set(400, -100);
		ground.addCollider(new Box(ground, 0, 0, 800, 400));
		world.add(ground);

		WorldSimulationUtil.simulate(world);
	}

	@Test
	public void simpleBoxStackWithSolidBlock()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 400);
		player.addCollider(new Box(player, 0, 0, 20, 45));
		world.add(player);

		GravityAffected test = new GravityAffected();
		test.getPosition().set(400, 200);
		test.addCollider(new Box(test, 0, 0, 50, 50));
		world.add(test);

		BodyImpl solidBlock = new BodyImpl();
		solidBlock.getPosition().set(600, 110);
		solidBlock.addCollider(new Box(solidBlock, 0, 0, 100, 100));
		world.add(solidBlock);

		BodyImpl solidBlock2 = new BodyImpl();
		solidBlock2.getPosition().set(200, 110);
		solidBlock2.addCollider(new Box(solidBlock2, 0, 0, 100, 100));
		world.add(solidBlock2);

		BodyImpl ground = new BodyImpl();
		ground.getPosition().set(400, -100);
		ground.addCollider(new Box(ground, 0, 0, 800, 400));
		world.add(ground);

		WorldSimulationUtil.simulate(world);
	}

	@Test
	public void bigBoxStack()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		GravityAffected test = new GravityAffected();
		test.getPosition().set(400, 500);
		test.addCollider(new Box(test, 0, 0, 30, 30));
		world.add(test);

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 600);
		player.addCollider(new Box(player, 0, 0, 20, 45));
		world.add(player);

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

	@Test
	public void bigBoxStackWithLazyFloatyBox()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		GravityAffected test = new GravityAffected() {
			@Override
			public void update(float delta)
			{
				super.update(delta);
				getVelocity().y += 4;
			}
		};
		test.getPosition().set(400, 500);
		test.addCollider(new Box(test, 0, 0, 30, 30));
		world.add(test);

		PlayerImpl player = new PlayerImpl() {
			@Override
			public void notifyCollision(Collision collision)
			{
				if(collision.normalA.dot(DOWN) > 0.7 && collision.impactVelA.dot(DOWN) > 0.7 && collision.colliderB.getBody() != test)
					onGround = true;
			}
		};

		player.getPosition().set(400, 600);
		player.addCollider(new Box(player, 0, 0, 20, 45));
		world.add(player);

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

	@Test
	public void towerTest()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 800);
		player.addCollider(new Box(player, 0, 0, 20, 45));
		world.add(player);

		for(int i = 0; i < 10; i++)
		{
			GravityAffected test = new GravityAffected();
			test.getPosition().set(400, 750 - i * 50);
			test.addCollider(new Box(test, 0, 0, 30, 30));
			world.add(test);
		}

		BodyImpl solidBlock = new BodyImpl();
		solidBlock.getPosition().set(600, 110);
		solidBlock.addCollider(new Box(solidBlock, 0, 0, 100, 100));
		world.add(solidBlock);

		BodyImpl ground = new BodyImpl();
		ground.getPosition().set(400, -100);
		ground.addCollider(new Box(ground, 0, 0, 800, 400));
		world.add(ground);

		WorldSimulationUtil.simulate(world);
	}

	@Test
	public void towerLimitTest()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();
		player.getPosition().set(400, 800);
		player.addCollider(new Limit(player, -10, 25, LEFT, 50));
		player.addCollider(new Limit(player, 10, 25, RIGHT, 50));
		player.addCollider(new Limit(player, 0, 50, UP, 20));
		player.addCollider(new Limit(player, 0, 0, DOWN, 20));
		world.add(player);

		for(int i = 0; i < 10; i++)
		{
			GravityAffected test = new GravityAffected();
			test.getPosition().set(400, 750 - i * 50);
			test.addCollider(new Limit(test, -15, 0, LEFT, 30));
			test.addCollider(new Limit(test, 15, 0, RIGHT, 30));
			test.addCollider(new Limit(test, 0, 15, UP, 30));
			test.addCollider(new Limit(test, 0, -15, DOWN, 30));
			world.add(test);
		}

		BodyImpl solidBlock = new BodyImpl();
		solidBlock.getPosition().set(600, 110);
		solidBlock.addCollider(new Limit(solidBlock, 0, 0, LEFT, 100));
		world.add(solidBlock);

		BodyImpl ground = new BodyImpl();
		ground.getPosition().set(400, 100);
		ground.addCollider(new Limit(ground, 0, 0, UP, 800));
		world.add(ground);

		WorldSimulationUtil.simulate(world);
	}

	@Test
	public void flyingBoxesStack()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();

		player.getPosition().set(400, 600);
		player.addCollider(new Box(player, 0, 0, 20, 45));
		world.add(player);

		DynamicBodyImpl test = new DynamicBodyImpl();
		test.getPosition().set(400, 500);
		test.addCollider(new Box(test, 0, 0, 30, 30));
		world.add(test);

		DynamicBodyImpl test2 = new DynamicBodyImpl();
		test2.getPosition().set(400, 400);
		test2.addCollider(new Box(test2, 0, 0, 30, 30));
		world.add(test2);

		DynamicBodyImpl test3 = new DynamicBodyImpl();
		test3.getPosition().set(400, 300);
		test3.addCollider(new Box(test3, 0, 0, 50, 50));
		world.add(test3);

		BodyImpl ground = new BodyImpl();

		ground.getPosition().set(400, 0);
		ground.addCollider(new Box(ground, 0, 0, 800, 400));
		world.add(ground);

		WorldSimulationUtil.simulate(world);
	}

	@Test
	public void pickyBoxStack()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		PlayerImpl player = new PlayerImpl();

		player.getPosition().set(400, 600);
		player.addCollider(new Box(player, 0, 0, 20, 45));
		world.add(player);

		GravityAffected test = new GravityAffected(){
			@Override
			public float getWeight(DynamicBody against)
			{
				return against == player ? 0f : 1f;
			}
		};
		test.getPosition().set(400, 500);
		test.addCollider(new Box(test, 0, 0, 30, 30));
		world.add(test);

		GravityAffected test2 = new GravityAffected() {
			@Override
			public float getWeight(DynamicBody against)
			{
				return 0f;
			}
		};
		test2.getPosition().set(400, 400);
		test2.addCollider(new Box(test2, 0, 0, 50, 50));
		world.add(test2);

		BodyImpl ground = new BodyImpl();

		ground.getPosition().set(400, 0);
		ground.addCollider(new Box(ground, 0, 0, 800, 400));
		world.add(ground);

		WorldSimulationUtil.simulate(world);
	}
}
