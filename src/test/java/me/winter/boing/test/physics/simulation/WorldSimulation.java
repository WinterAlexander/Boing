package me.winter.boing.test.physics.simulation;

import me.winter.boing.physics.Collider;
import me.winter.boing.physics.World;
import me.winter.boing.physics.resolver.VelocityResolver;
import me.winter.boing.physics.shapes.Circle;
import me.winter.boing.test.physics.SolidImpl;
import org.junit.Test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class WorldSimulation
{
	@Test
	public void testCirclesInBox()
	{
		World world = new World();

		SolidImpl solid = new SolidImpl(world);

		solid.getPosition().set(50, 50);
		solid.getColliders().add(new Collider(solid, new Circle(solid, 0, 0, 25), new VelocityResolver(1f)));
		solid.getVelocity().set(179, 150);

		world.getSolids().add(solid);

		SolidImpl solid2 = new SolidImpl(world);

		solid2.getPosition().set(500, 50);
		solid2.getColliders().add(new Collider(solid2, new Circle(solid2, 0, 0, 20), new VelocityResolver(1f)));
		solid2.getVelocity().set(-150, 150);

		world.getSolids().add(solid2);

		SolidImpl solid3 = new SolidImpl(world);

		solid3.getPosition().set(300, 500);
		solid3.getColliders().add(new Collider(solid3, new Circle(solid3, 0, 0, 25), new VelocityResolver(1f)));
		solid3.getVelocity().set(0, -100);

		world.getSolids().add(solid3);

		SolidImpl solid4 = new SolidImpl(world);

		solid4.getPosition().set(500, 500);
		solid4.getColliders().add(new Collider(solid4, new Circle(solid4, 0, 0, 25), new VelocityResolver(1f)));
		solid4.getVelocity().set(-100, -100);

		world.getSolids().add(solid4);

		for(int i = 20; i < 800; i+= 20)
		{
			SolidImpl wall1 = new SolidImpl(world);

			wall1.getPosition().set(i, 0);
			wall1.getColliders().add(new Collider(wall1, new Circle(wall1, 0, 0, 10), new VelocityResolver(1f)));
			wall1.getVelocity().set(0, 0);

			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 800; i+= 20)
		{
			SolidImpl wall1 = new SolidImpl(world);

			wall1.getPosition().set(i, 600);
			wall1.getColliders().add(new Collider(wall1, new Circle(wall1, 0, 0, 10), new VelocityResolver(1f)));
			wall1.getVelocity().set(0, 0);

			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			SolidImpl wall1 = new SolidImpl(world);

			wall1.getPosition().set(0, i);
			wall1.getColliders().add(new Collider(wall1, new Circle(wall1, 0, 0, 10), new VelocityResolver(1f)));
			wall1.getVelocity().set(0, 0);

			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			SolidImpl wall1 = new SolidImpl(world);

			wall1.getPosition().set(800, i);
			wall1.getColliders().add(new Collider(wall1, new Circle(wall1, 0, 0, 10), new VelocityResolver(1f)));
			wall1.getVelocity().set(0, 0);

			world.getSolids().add(wall1);
		}
		simulate(world);
	}

	@Test
	public void testCircleBouncingBetween2Circles()
	{
		World world = new World();

		SolidImpl solid = new SolidImpl(world);

		solid.getPosition().set(400, 400);
		solid.getColliders().add(new Collider(solid, new Circle(solid, 0, 0, 25), new VelocityResolver(1f)));
		solid.getVelocity().set(50, 0);

		world.getSolids().add(solid);

		SolidImpl solid2 = new SolidImpl(world);

		solid2.getPosition().set(550, 400);
		solid2.getColliders().add(new Collider(solid2, new Circle(solid2, 0, 0, 50), new VelocityResolver(1f)));
		solid2.getVelocity().set(0, 0);

		world.getSolids().add(solid2);

		SolidImpl solid3 = new SolidImpl(world);

		solid3.getPosition().set(250, 400);
		solid3.getColliders().add(new Collider(solid3, new Circle(solid3, 0, 0, 50), new VelocityResolver(1f)));
		solid3.getVelocity().set(0, 0);

		world.getSolids().add(solid3);

		simulate(world);
	}

	@Test
	public void testCircleColling2Circles()
	{
		World world = new World();

		SolidImpl solid = new SolidImpl(world);

		solid.getPosition().set(400, 400);
		solid.getColliders().add(new Collider(solid, new Circle(solid, 0, 0, 25), new VelocityResolver(1f)));
		solid.getVelocity().set(0, -50);

		world.getSolids().add(solid);

		SolidImpl solid2 = new SolidImpl(world);

		solid2.getPosition().set(450, 100);
		solid2.getColliders().add(new Collider(solid2, new Circle(solid2, 0, 0, 50), new VelocityResolver(1f)));
		solid2.getVelocity().set(0, 0);

		world.getSolids().add(solid2);

		SolidImpl solid3 = new SolidImpl(world);

		solid3.getPosition().set(350, 100);
		solid3.getColliders().add(new Collider(solid3, new Circle(solid3, 0, 0, 50), new VelocityResolver(1f)));
		solid3.getVelocity().set(0, 0);

		world.getSolids().add(solid3);

		simulate(world);
	}

	private static void simulate(World world)
	{
		JFrame frame = new JFrame();

		frame.setSize(800, 600);

		frame.setContentPane(new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g)
			{
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 800, 600);

				g.setColor(Color.BLACK);

				for(int i = 0; i < world.getSolids().size; i++)
					((SolidImpl)world.getSolids().get(i)).draw(g);
			}
		});

		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		while(true)
		{
			long start = System.nanoTime();
			world.step(1 / 60f);
			frame.repaint();
			long toWait = 1000 / 60 - (System.nanoTime() - start) / 1_000_000;

			if(toWait <= 0)
				continue;
			try
			{
				Thread.sleep(toWait);
			}
			catch(Exception ex)
			{

			}
		}
	}
}
