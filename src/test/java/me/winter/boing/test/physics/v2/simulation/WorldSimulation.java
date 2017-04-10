package me.winter.boing.test.physics.v2.simulation;

import me.winter.boing.physics.v2.World;
import me.winter.boing.physics.v2.colliders.CircleCollider;
import me.winter.boing.test.physics.v2.SolidImpl;
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

		SolidImpl solid = new SolidImpl();

		solid.getPosition().set(50, 50);
		solid.getColliders().add(new CircleCollider(solid, 0, 0, 25));
		solid.getVelocity().set(79, 50);

		world.getSolids().add(solid);

		SolidImpl solid2 = new SolidImpl();

		solid2.getPosition().set(500, 50);
		solid2.getColliders().add(new CircleCollider(solid2, 0, 0, 20));
		solid2.getVelocity().set(-50, 50);

		world.getSolids().add(solid2);

		SolidImpl solid3 = new SolidImpl();

		solid3.getPosition().set(300, 500);
		solid3.getColliders().add(new CircleCollider(solid3, 0, 0, 25));
		solid3.getVelocity().set(0, -100);

		world.getSolids().add(solid3);

		SolidImpl solid4 = new SolidImpl();

		solid4.getPosition().set(500, 500);
		solid4.getColliders().add(new CircleCollider(solid4, 0, 0, 25));
		solid4.getVelocity().set(-100, -100);

		world.getSolids().add(solid4);

		for(int i = 20; i < 800; i+= 20)
		{
			SolidImpl wall1 = new SolidImpl();

			wall1.getPosition().set(i, 0);
			wall1.getColliders().add(new CircleCollider(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);

			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 800; i+= 20)
		{
			SolidImpl wall1 = new SolidImpl();

			wall1.getPosition().set(i, 600);
			wall1.getColliders().add(new CircleCollider(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);

			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			SolidImpl wall1 = new SolidImpl();

			wall1.getPosition().set(0, i);
			wall1.getColliders().add(new CircleCollider(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);

			world.getSolids().add(wall1);
		}

		for(int i = 20; i < 600; i+= 20)
		{
			SolidImpl wall1 = new SolidImpl();

			wall1.getPosition().set(800, i);
			wall1.getColliders().add(new CircleCollider(wall1, 0, 0, 10));
			wall1.getVelocity().set(0, 0);

			world.getSolids().add(wall1);
		}
		simulate(world);
	}

	@Test
	public void testCircleBouncingBetween2Circles()
	{
		World world = new World();

		SolidImpl solid = new SolidImpl();

		solid.getPosition().set(400, 400);
		solid.getColliders().add(new CircleCollider(solid, 0, 0, 25));
		solid.getVelocity().set(50, 0);

		world.getSolids().add(solid);

		SolidImpl solid2 = new SolidImpl();

		solid2.getPosition().set(550, 400);
		solid2.getColliders().add(new CircleCollider(solid2, 0, 0, 50));
		solid2.getVelocity().set(0, 0);

		world.getSolids().add(solid2);

		SolidImpl solid3 = new SolidImpl();

		solid3.getPosition().set(250, 400);
		solid3.getColliders().add(new CircleCollider(solid3, 0, 0, 50));
		solid3.getVelocity().set(0, 0);

		world.getSolids().add(solid3);

		simulate(world);
	}

	@Test
	public void testCircleColling2Circles()
	{
		World world = new World();

		SolidImpl solid = new SolidImpl();

		solid.getPosition().set(400, 400);
		solid.getColliders().add(new CircleCollider(solid, 0, 0, 25));
		solid.getVelocity().set(0, -50);

		world.getSolids().add(solid);

		SolidImpl solid2 = new SolidImpl();

		solid2.getPosition().set(450, 100);
		solid2.getColliders().add(new CircleCollider(solid2, 0, 0, 50));
		solid2.getVelocity().set(0, 0);

		world.getSolids().add(solid2);

		SolidImpl solid3 = new SolidImpl();

		solid3.getPosition().set(350, 100);
		solid3.getColliders().add(new CircleCollider(solid3, 0, 0, 50));
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
			world.step(1 / 60f);
			frame.repaint();
			try
			{
				Thread.sleep(1000 / 60);
			}
			catch(Exception ex)
			{

			}
		}
	}
}
