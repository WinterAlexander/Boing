package me.winter.boing.test.physics.simulation;

import me.winter.boing.physics.World;
import me.winter.boing.physics.resolver.ReplaceResolver;
import me.winter.boing.physics.shapes.AABB;
import me.winter.boing.physics.shapes.Circle;
import me.winter.boing.test.physics.BouncingBallImpl;
import me.winter.boing.test.physics.SolidImpl;
import org.junit.Test;

import static me.winter.boing.test.physics.simulation.WorldSimulationUtil.simulate;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class ReplaceResolverSimulation
{
	@Test
	public void testCirclesInBox()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl(world, Float.POSITIVE_INFINITY);

		solid.getPosition().set(50, 50);
		solid.getColliders().add(new Circle(solid, 0, 0, 25));
		solid.getVelocity().set(179, 150);

		world.getSolids().add(solid);

		BouncingBallImpl solid2 = new BouncingBallImpl(world);

		solid2.getPosition().set(500, 50);
		solid2.getColliders().add(new Circle(solid2, 0, 0, 20));
		solid2.getVelocity().set(-150, 150);

		world.getSolids().add(solid2);

		BouncingBallImpl solid3 = new BouncingBallImpl(world);

		solid3.getPosition().set(300, 500);
		solid3.getColliders().add(new Circle(solid3, 0, 0, 25));
		solid3.getVelocity().set(0, -100);

		world.getSolids().add(solid3);

		BouncingBallImpl solid4 = new BouncingBallImpl(world);

		solid4.getPosition().set(500, 500);
		solid4.getColliders().add(new Circle(solid4, 0, 0, 25));
		solid4.getVelocity().set(-100, -100);

		world.getSolids().add(solid4);

		for(int i = 20; i < 800; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl(world);

			wall1.getPosition().set(i, 0);
			wall1.getColliders().add(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);

			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 800; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl(world);

			wall1.getPosition().set(i, 600);
			wall1.getColliders().add(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);

			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl(world);

			wall1.getPosition().set(0, i);
			wall1.getColliders().add(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);

			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl(world);

			wall1.getPosition().set(800, i);
			wall1.getColliders().add(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);

			world.getSolids().add(wall1);
		}
		simulate(world);
	}

	@Test
	public void testBug()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl(world, Float.POSITIVE_INFINITY);

		solid.getPosition().set(50, 50);
		solid.getColliders().add(new Circle(solid, 0, 0, 25));
		solid.getVelocity().set(179, 150);

		world.getSolids().add(solid);

		BouncingBallImpl solid2 = new BouncingBallImpl(world);

		solid2.getPosition().set(500, 50);
		solid2.getColliders().add(new Circle(solid2, 0, 0, 20));
		solid2.getVelocity().set(-150, 150);

		world.getSolids().add(solid2);

		BouncingBallImpl solid3 = new BouncingBallImpl(world);

		solid3.getPosition().set(300, 500);
		solid3.getColliders().add(new Circle(solid3, 0, 0, 25));
		solid3.getVelocity().set(0, -100);

		world.getSolids().add(solid3);

		BouncingBallImpl solid4 = new BouncingBallImpl(world);

		solid4.getPosition().set(500, 500);
		solid4.getColliders().add(new Circle(solid4, 0, 0, 25));
		solid4.getVelocity().set(-100, -100);

		world.getSolids().add(solid4);

		simulate(world);
	}
	
	@Test
	public void testFuckFest()
	{
		World world = new World(new ReplaceResolver());
		
		for(int i = 200; i < 600; i += 20)
		{
			for(int j = 200; j < 400; j += 20)
			{
				BouncingBallImpl object = new BouncingBallImpl(world);

				object.getPosition().set(i, j);
				object.getColliders().add(new Circle(object, 0, 0, 10));
				object.getVelocity().set(0, 0);

				world.getSolids().add(object);
			}
		}

		BouncingBallImpl initier = new BouncingBallImpl(world, Float.POSITIVE_INFINITY);

		initier.getPosition().set(400, 0);
		initier.getColliders().add(new Circle(initier, 0, 0, 10));
		initier.getVelocity().set(10, 200);

		world.getSolids().add(initier);

		for(int i = 20; i < 800; i+= 20)
		{
			SolidImpl wall1 = new SolidImpl(world);

			wall1.getPosition().set(i, 0);
			wall1.getColliders().add(new Circle(wall1, 0, 0, 100));

			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 800; i+= 20)
		{
			SolidImpl wall1 = new SolidImpl(world);

			wall1.getPosition().set(i, 600);
			wall1.getColliders().add(new Circle(wall1, 0, 0, 100));

			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			SolidImpl wall1 = new SolidImpl(world);

			wall1.getPosition().set(0, i);
			wall1.getColliders().add(new Circle(wall1, 0, 0, 100));

			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			SolidImpl wall1 = new SolidImpl(world);

			wall1.getPosition().set(800, i);
			wall1.getColliders().add(new Circle(wall1, 0, 0, 100));

			world.getSolids().add(wall1);
		}
		
		simulate(world);
	}

	@Test
	public void testBoxBouncingOnBoxX()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl(world);
		solid.getPosition().set(400, 400);
		solid.getColliders().add(new AABB(solid, 0, 0, 40, 25));
		solid.getVelocity().set(-50, 0);
		world.getSolids().add(solid);


		BouncingBallImpl solid2 = new BouncingBallImpl(world);
		solid2.getPosition().set(650, 400);
		solid2.getColliders().add(new AABB(solid2, 0, 0, 80, 100));
		solid2.getVelocity().set(-60, 0);
		world.getSolids().add(solid2);


		BouncingBallImpl solid3 = new BouncingBallImpl(world);
		solid3.getPosition().set(280, 400);
		solid3.getColliders().add(new AABB(solid3, 0, 0, 50, 50));
		solid3.getVelocity().set(-10, 0);
		world.getSolids().add(solid3);

		simulate(world);
	}

	@Test
	public void testBoxBouncingOnBoxY()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl(world);
		solid.getPosition().set(400, 400);
		solid.getColliders().add(new AABB(solid, 0, 0, 40, 25));
		solid.getVelocity().set(0, -50);
		world.getSolids().add(solid);


		BouncingBallImpl solid2 = new BouncingBallImpl(world);
		solid2.getPosition().set(400, 650);
		solid2.getColliders().add(new AABB(solid2, 0, 0, 80, 100));
		solid2.getVelocity().set(0, -60);
		world.getSolids().add(solid2);


		BouncingBallImpl solid3 = new BouncingBallImpl(world);
		solid3.getPosition().set(400, 280);
		solid3.getColliders().add(new AABB(solid3, 0, 0, 50, 50));
		solid3.getVelocity().set(0, -10);
		world.getSolids().add(solid3);

		simulate(world);
	}

	@Test
	public void testBoxWideBounce()
	{
		World world = new World(new ReplaceResolver());

		SolidImpl ground = new SolidImpl(world);
		ground.getPosition().set(400, 0);
		ground.getColliders().add(new AABB(ground, 0, 0, 800, 100));
		world.getSolids().add(ground);

		BouncingBallImpl box = new BouncingBallImpl(world);
		box.getPosition().set(100, 125);
		box.getColliders().add(new AABB(box, 0, 0, 50, 50));
		box.getVelocity().set(150, -20);
		world.getSolids().add(box);

		simulate(world);
	}

	@Test
	public void testBoxesInBallCage()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl(world);
		solid.getPosition().set(400, 400);
		solid.getColliders().add(new AABB(solid, 0, 0, 40, 25));
		solid.getVelocity().set(-50, -20);
		world.getSolids().add(solid);

		BouncingBallImpl solid2 = new BouncingBallImpl(world);
		solid2.getPosition().set(650, 400);
		solid2.getColliders().add(new AABB(solid2, 0, 0, 80, 100));
		solid2.getVelocity().set(-60, 30);
		world.getSolids().add(solid2);

		BouncingBallImpl solid3 = new BouncingBallImpl(world);
		solid3.getPosition().set(280, 400);
		solid3.getColliders().add(new AABB(solid3, 0, 0, 50, 50));
		solid3.getVelocity().set(-10, 25);
		world.getSolids().add(solid3);

		for(int i = 20; i < 800; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl(world);
			wall1.getPosition().set(i, 0);
			wall1.getColliders().add(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);
			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 800; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl(world);
			wall1.getPosition().set(i, 600);
			wall1.getColliders().add(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);
			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl(world);
			wall1.getPosition().set(0, i);
			wall1.getColliders().add(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);
			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			BouncingBallImpl wall1 = new BouncingBallImpl(world);
			wall1.getPosition().set(800, i);
			wall1.getColliders().add(new Circle(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);
			world.getSolids().add(wall1);
		}

		simulate(world);
	}

	@Test
	public void testCircleBouncingBetween2Circles()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl(world);
		solid.getPosition().set(400, 400);
		solid.getColliders().add(new Circle(solid, 0, 0, 25));
		solid.getVelocity().set(50, 0);
		world.getSolids().add(solid);

		BouncingBallImpl solid2 = new BouncingBallImpl(world);
		solid2.getPosition().set(550, 400);
		solid2.getColliders().add(new Circle(solid2, 0, 0, 50));
		solid2.getVelocity().set(0, 0);
		world.getSolids().add(solid2);

		BouncingBallImpl solid3 = new BouncingBallImpl(world);
		solid3.getPosition().set(250, 400);
		solid3.getColliders().add(new Circle(solid3, 0, 0, 50));
		solid3.getVelocity().set(0, 0);
		world.getSolids().add(solid3);

		simulate(world);
	}

	@Test
	public void testCircleColling2Circles()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl(world);
		solid.getPosition().set(400, 400);
		solid.getColliders().add(new Circle(solid, 0, 0, 25));
		solid.getVelocity().set(0, -50);
		world.getSolids().add(solid);

		BouncingBallImpl solid2 = new BouncingBallImpl(world);
		solid2.getPosition().set(450, 100);
		solid2.getColliders().add(new Circle(solid2, 0, 0, 50));
		solid2.getVelocity().set(0, 0);
		world.getSolids().add(solid2);

		BouncingBallImpl solid3 = new BouncingBallImpl(world);
		solid3.getPosition().set(350, 100);
		solid3.getColliders().add(new Circle(solid3, 0, 0, 50));
		solid3.getVelocity().set(0, 0);
		world.getSolids().add(solid3);

		simulate(world);
	}



	@Test
	public void testCircleBoxCollision()
	{
		World world = new World(new ReplaceResolver());

		BouncingBallImpl solid = new BouncingBallImpl(world);
		solid.getPosition().set(400, 400);
		solid.getColliders().add(new Circle(solid, 0, 0, 25));
		solid.getVelocity().set(0, -50);
		world.getSolids().add(solid);

		BouncingBallImpl solid2 = new BouncingBallImpl(world);
		solid2.getPosition().set(400, 100);
		solid2.getColliders().add(new AABB(solid2, 0, 0, 50, 50));
		solid2.getVelocity().set(0, 70);
		world.getSolids().add(solid2);

		simulate(world);
	}
}
