package me.winter.boing.test.physics.simulation;

import me.winter.boing.test.physics.testimpl.WorldImpl;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class WorldSimulationUtil
{
	public static void simulate(WorldImpl world)
	{
		JFrame frame = new JFrame();

		frame.setSize(800, 600);

		JPanel panel = new JPanel()
		{
			@Override
			protected void paintComponent(Graphics g)
			{
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 800, 600);

				g.setColor(Color.BLACK);

				for(int i = 0; i < world.getSolids().size; i++)
					((SimulationElement)world.getSolids().get(i)).draw(g);
			}
		};

		frame.setContentPane(panel);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		while(true)
		{
			try
			{
				long start = System.nanoTime();
				SwingUtilities.invokeAndWait(() -> {
					world.step(1 / 60f);
					frame.repaint();
				});
				long toWait = 1000 / 60 - (System.nanoTime() - start) / 1_000_000;

				if(toWait <= 0)
					continue;

				Thread.sleep(toWait);
			}
			catch(Exception ex)
			{

			}
		}
	}
}
