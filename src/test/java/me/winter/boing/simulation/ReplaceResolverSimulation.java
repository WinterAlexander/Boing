package me.winter.boing.simulation;

import me.winter.boing.colliders.Box;
import me.winter.boing.colliders.Circle;
import me.winter.boing.impl.BodyImpl;
import me.winter.boing.resolver.ReplaceResolver;
import me.winter.boing.testimpl.BouncingBallImpl;
import me.winter.boing.testimpl.TestWorldImpl;
import me.winter.boing.simulation.simulator.BoingSimulator;
import org.junit.Ignore;
import org.junit.Test;

import static java.lang.Float.POSITIVE_INFINITY;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
@Ignore
public class ReplaceResolverSimulation
{
	@Test
	public void testCirclesInBox()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl(POSITIVE_INFINITY);

		solid.getPosition().set(50, 50);
		solid.addCollider(new Circle(solid, 0, 0, 25));
		solid.getVelocity().set(179, 150);

		world.add(solid);

		BouncingBallImpl solid2 = new BouncingBallImpl();

		solid2.getPosition().set(500, 50);
		solid2.addCollider(new Circle(solid2, 0, 0, 20));
		solid2.getVelocity().set(-150, 150);

		world.add(solid2);

		BouncingBallImpl solid3 = new BouncingBallImpl();

		solid3.getPosition().set(300, 500);
		solid3.addCollider(new Circle(solid3, 0, 0, 25));
		solid3.getVelocity().set(0, -100);

		world.add(solid3);

		BouncingBallImpl solid4 = new BouncingBallImpl();

		solid4.getPosition().set(500, 500);
		solid4.addCollider(new Circle(solid4, 0, 0, 25));
		solid4.getVelocity().set(-100, -100);

		world.add(solid4);

		for(int i = 20; i < 800; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl();

			wall1.getPosition().set(i, 0);
			wall1.addCollider(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);

			world.add(wall1);
		}

		for(int i = 20; i < 800; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl();

			wall1.getPosition().set(i, 600);
			wall1.addCollider(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);

			world.add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl();

			wall1.getPosition().set(0, i);
			wall1.addCollider(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);

			world.add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl();

			wall1.getPosition().set(800, i);
			wall1.addCollider(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);

			world.add(wall1);
		}
		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void testBug()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl(POSITIVE_INFINITY);

		solid.getPosition().set(50, 50);
		solid.addCollider(new Circle(solid, 0, 0, 25));
		solid.getVelocity().set(179, 150);

		world.add(solid);

		BouncingBallImpl solid2 = new BouncingBallImpl();

		solid2.getPosition().set(500, 50);
		solid2.addCollider(new Circle(solid2, 0, 0, 20));
		solid2.getVelocity().set(-150, 150);

		world.add(solid2);

		BouncingBallImpl solid3 = new BouncingBallImpl();

		solid3.getPosition().set(300, 500);
		solid3.addCollider(new Circle(solid3, 0, 0, 25));
		solid3.getVelocity().set(0, -100);

		world.add(solid3);

		BouncingBallImpl solid4 = new BouncingBallImpl();

		solid4.getPosition().set(500, 500);
		solid4.addCollider(new Circle(solid4, 0, 0, 25));
		solid4.getVelocity().set(-100, -100);

		world.add(solid4);

		new BoingSimulator(world, 60f).start();
	}
	
	@Test
	public void testFuckFest()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());
		
		for(int i = 200; i < 600; i += 20)
		{
			for(int j = 200; j < 400; j += 20)
			{
				BouncingBallImpl object = new BouncingBallImpl();

				object.getPosition().set(i, j);
				object.addCollider(new Circle(object, 0, 0, 10));
				object.getVelocity().set(0, 0);

				world.add(object);
			}
		}

		BouncingBallImpl initier = new BouncingBallImpl(POSITIVE_INFINITY);

		initier.getPosition().set(400, 0);
		initier.addCollider(new Circle(initier, 0, 0, 10));
		initier.getVelocity().set(10, 200);

		world.add(initier);

		for(int i = 20; i < 800; i+= 20)
		{
			BodyImpl wall1 = new BodyImpl();

			wall1.getPosition().set(i, 0);
			wall1.addCollider(new Circle(wall1, 0, 0, 100));

			world.add(wall1);
		}

		for(int i = 20; i < 800; i+= 20)
		{
			BodyImpl wall1 = new BodyImpl();

			wall1.getPosition().set(i, 600);
			wall1.addCollider(new Circle(wall1, 0, 0, 100));

			world.add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			BodyImpl wall1 = new BodyImpl();

			wall1.getPosition().set(0, i);
			wall1.addCollider(new Circle(wall1, 0, 0, 100));

			world.add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			BodyImpl wall1 = new BodyImpl();

			wall1.getPosition().set(800, i);
			wall1.addCollider(new Circle(wall1, 0, 0, 100));

			world.add(wall1);
		}

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void testBoxBouncingOnBoxX()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl();
		solid.getPosition().set(400, 400);
		solid.addCollider(new Box(solid, 0, 0, 40, 25));
		solid.getVelocity().set(-50, 0);
		world.add(solid);


		BouncingBallImpl solid2 = new BouncingBallImpl();
		solid2.getPosition().set(650, 400);
		solid2.addCollider(new Box(solid2, 0, 0, 80, 100));
		solid2.getVelocity().set(-60, 0);
		world.add(solid2);


		BouncingBallImpl solid3 = new BouncingBallImpl();
		solid3.getPosition().set(280, 400);
		solid3.addCollider(new Box(solid3, 0, 0, 50, 50));
		solid3.getVelocity().set(-10, 0);
		world.add(solid3);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void testBoxBouncingOnBoxY()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl();
		solid.getPosition().set(400, 400);
		solid.addCollider(new Box(solid, 0, 0, 40, 25));
		solid.getVelocity().set(0, -50);
		world.add(solid);


		BouncingBallImpl solid2 = new BouncingBallImpl();
		solid2.getPosition().set(400, 650);
		solid2.addCollider(new Box(solid2, 0, 0, 80, 100));
		solid2.getVelocity().set(0, -60);
		world.add(solid2);


		BouncingBallImpl solid3 = new BouncingBallImpl();
		solid3.getPosition().set(400, 280);
		solid3.addCollider(new Box(solid3, 0, 0, 50, 50));
		solid3.getVelocity().set(0, -10);
		world.add(solid3);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void testBoxWideBounce()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		BodyImpl ground = new BodyImpl();
		ground.getPosition().set(400, 0);
		ground.addCollider(new Box(ground, 0, 0, 800, 100));
		world.add(ground);

		BouncingBallImpl box = new BouncingBallImpl();
		box.getPosition().set(100, 125);
		box.addCollider(new Box(box, 0, 0, 50, 50));
		box.getVelocity().set(150, -20);
		world.add(box);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void testBoxesInBallCage()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl();
		solid.getPosition().set(400, 400);
		solid.addCollider(new Box(solid, 0, 0, 40, 25));
		solid.getVelocity().set(-50, -20);
		world.add(solid);

		BouncingBallImpl solid2 = new BouncingBallImpl();
		solid2.getPosition().set(650, 400);
		solid2.addCollider(new Box(solid2, 0, 0, 80, 100));
		solid2.getVelocity().set(-60, 30);
		world.add(solid2);

		BouncingBallImpl solid3 = new BouncingBallImpl();
		solid3.getPosition().set(280, 400);
		solid3.addCollider(new Box(solid3, 0, 0, 50, 50));
		solid3.getVelocity().set(-10, 25);
		world.add(solid3);

		for(int i = 20; i < 800; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl();
			wall1.getPosition().set(i, 0);
			wall1.addCollider(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);
			world.add(wall1);
		}

		for(int i = 20; i < 800; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl();
			wall1.getPosition().set(i, 600);
			wall1.addCollider(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);
			world.add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl();
			wall1.getPosition().set(0, i);
			wall1.addCollider(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);
			world.add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl();
			wall1.getPosition().set(800, i);
			wall1.addCollider(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);
			world.add(wall1);
		}

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void testCircleBouncingBetween2Circles()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl();
		solid.getPosition().set(400, 400);
		solid.addCollider(new Circle(solid, 0, 0, 25));
		solid.getVelocity().set(50, 0);
		world.add(solid);

		BouncingBallImpl solid2 = new BouncingBallImpl();
		solid2.getPosition().set(550, 400);
		solid2.addCollider(new Circle(solid2, 0, 0, 50));
		solid2.getVelocity().set(0, 0);
		world.add(solid2);

		BouncingBallImpl solid3 = new BouncingBallImpl();
		solid3.getPosition().set(250, 400);
		solid3.addCollider(new Circle(solid3, 0, 0, 50));
		solid3.getVelocity().set(0, 0);
		world.add(solid3);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void testCircleColling2Circles()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl();
		solid.getPosition().set(400, 400);
		solid.addCollider(new Circle(solid, 0, 0, 25));
		solid.getVelocity().set(0, -50);
		world.add(solid);

		BouncingBallImpl solid2 = new BouncingBallImpl();
		solid2.getPosition().set(450, 100);
		solid2.addCollider(new Circle(solid2, 0, 0, 50));
		solid2.getVelocity().set(0, 0);
		world.add(solid2);

		BouncingBallImpl solid3 = new BouncingBallImpl();
		solid3.getPosition().set(350, 100);
		solid3.addCollider(new Circle(solid3, 0, 0, 50));
		solid3.getVelocity().set(0, 0);
		world.add(solid3);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void testCircleBoxCollision()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl();
		solid.getPosition().set(400, 400);
		solid.addCollider(new Circle(solid, 0, 0, 25));
		solid.getVelocity().set(0, -50);
		world.add(solid);

		BouncingBallImpl solid2 = new BouncingBallImpl();
		solid2.getPosition().set(400, 100);
		solid2.addCollider(new Box(solid2, 0, 0, 50, 50));
		solid2.getVelocity().set(0, 70);
		world.add(solid2);

		new BoingSimulator(world, 60f).start();
	}

	@Test
	public void boxGoingNextToBoxX()
	{
		TestWorldImpl world = new TestWorldImpl(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl();
		solid.getPosition().set(400, 400);
		solid.addCollider(new Box(solid, 0, 0, 50, 50));
		solid.getVelocity().set(-50, 0);
		world.add(solid);

		BouncingBallImpl solid2 = new BouncingBallImpl();
		solid2.getPosition().set(100, 350);
		solid2.addCollider(new Box(solid2, 0, 0, 50, 50));
		solid2.getVelocity().set(70, 0);
		world.add(solid2);

		new BoingSimulator(world, 60f).start();
	}
}
