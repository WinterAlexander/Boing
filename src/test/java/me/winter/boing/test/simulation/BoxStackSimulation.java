package me.winter.boing.test.simulation;

import me.winter.boing.impl.BodyImpl;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.shapes.Box;
import me.winter.boing.test.testimpl.GravityAffected;
import me.winter.boing.test.testimpl.PlayerImpl;
import me.winter.boing.test.testimpl.TestWorldImpl;
import org.junit.Ignore;
import org.junit.Test;

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
		test.getPosition().set(400, 100);
		test.addCollider(new Box(test, 0, 0, 50, 50));
		world.add(test);

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
	public void bigBoxStack()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

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
}
