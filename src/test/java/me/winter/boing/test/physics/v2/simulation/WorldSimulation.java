package me.winter.boing.test.physics.v2.simulation;

import me.winter.boing.physics.v2.Solid;
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
	public static void main(String[] args) throws Throwable
	{
		JFrame frame = new JFrame();

		World world = new World();

		SolidImpl solid = new SolidImpl();

		solid.getPosition().set(50, 50);
		solid.getColliders().add(new CircleCollider(solid, 0, 0, 50));
		solid.getVelocity().set(10, 10);

		world.getSolids().add(solid);

		SolidImpl solid2 = new SolidImpl();

		solid2.getPosition().set(500, 50);
		solid2.getColliders().add(new CircleCollider(solid2, 0, 0, 40));
		solid2.getVelocity().set(-10, 10);

		world.getSolids().add(solid2);

		frame.setSize(800, 600);

		frame.setContentPane(new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g)
			{
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 800, 600);


				g.setColor(Color.BLACK);

				for(Solid solid : world.getSolids())
					((SolidImpl)solid).draw(g);
			}
		});

		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		while(true)
		{
			world.step(1 / 60f);
			frame.repaint();
			Thread.sleep(1000 / 60);
		}
	}

	@Test
	public void t()
	{
		for(int i = 0; i < 10; i++)
		{
			for(int j = i + 1; j < 10; j++)
			{
				System.out.println(i + ", " + j);
			}
		}
	}

	@Test
	public void t2()
	{
		for(int i = 10; i-- >= 0;)
		{
			for(int j = i; j-- > 0;)
			{
				System.out.println(i + ", " + j);
			}
		}
	}
}
