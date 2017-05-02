package me.winter.boing.test.physics.simulation;

import me.winter.boing.physics.Body;
import me.winter.boing.physics.SimpleWorld;
import org.junit.Ignore;

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
@Ignore
public class WorldSimulationUtil
{
	public static void simulate(SimpleWorld world)
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

				for(Body body : world)
					if(body instanceof SimulationElement)
						((SimulationElement)body).draw(g);
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
