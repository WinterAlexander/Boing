package me.winter.boing.test.simulation;

import me.winter.boing.Body;
import me.winter.boing.SimpleWorld;
import me.winter.boing.shapes.Box;
import me.winter.boing.shapes.Circle;
import me.winter.boing.shapes.Collider;
import me.winter.boing.shapes.Limit;
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

				for(Body body : world)
				{
					g.setColor(new Color(body.hashCode()));

					for(Collider collider : body.getColliders())
						if(collider instanceof Circle)
						{
							float r = ((Circle)collider).radius;
							g.fillOval((int)(body.getPosition().x - r), (int)(600 - body.getPosition().y - r), (int)r * 2, (int)r * 2);
						}
						else if(collider instanceof Box)
						{
							float w = ((Box)collider).width;
							float h = ((Box)collider).height;
							g.fillRect((int)(body.getPosition().x - w / 2), (int)(600 - body.getPosition().y - h / 2), (int)w, (int)h);
						}
						else if(collider instanceof Limit)
						{
							Limit limit = (Limit)collider;
							g.drawLine((int)(limit.getAbsX() - limit.size / 2 * limit.normal.y), (int)(600 - limit.getAbsY() + limit.size / 2 * limit.normal.x), (int)(limit.getAbsX() + limit.size / 2 * limit.normal.y), (int)(600 - limit.getAbsY() - limit.size / 2 * limit.normal.x));
						}
				}
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
				ex.printStackTrace();
			}
		}
	}
}
