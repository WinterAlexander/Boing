package me.winter.boing.util;

import me.winter.boing.Body;
import me.winter.boing.colliders.Box;
import me.winter.boing.colliders.Circle;
import me.winter.boing.colliders.Collider;
import me.winter.boing.colliders.Limit;
import me.winter.boing.testimpl.TestWorldImpl;
import org.junit.Ignore;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
@Ignore
public class WorldSimulation extends JFrame implements KeyListener
{
	private TestWorldImpl world;
	private float frameRate;

	private long frames = 0, framesRemaining = -1;

	public WorldSimulation(TestWorldImpl world, float frameRate)
	{
		this.world = world;
		this.frameRate = frameRate;

		setSize(800, 600);

		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g)
			{
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 800, 600);

				g.setColor(Color.BLACK);
				g.drawString("Collisions: " + world.collisionCount(), 650, 20);
				g.drawString("Frames: " + frames, 650, 40);

				int n = 0;

				for(Body body : world.getBodies())
				{
					g.setColor(new Color(body.hashCode()));

					for(Collider collider : body.getColliders())
						if(collider instanceof Circle)
						{
							Circle circle = (Circle)collider;
							float r = ((Circle)collider).radius;
							g.fillOval((int)(circle.getAbsX() - r), (int)(600 - circle.getAbsY() - r), (int)r * 2, (int)r * 2);
						}
						else if(collider instanceof Box)
						{
							Box box = ((Box)collider);
							float w = box.width;
							float h = box.height;
							g.fillRect((int)(box.getAbsX() - w / 2), (int)(600 - box.getAbsY() - h / 2), (int)w, (int)h);
						}
						else if(collider instanceof Limit)
						{
							Limit limit = (Limit)collider;
							g.drawLine((int)(limit.getAbsX() - limit.size / 2 * limit.normal.y), (int)(600 - limit.getAbsY() + limit.size / 2 * limit.normal.x), (int)(limit.getAbsX() + limit.size / 2 * limit.normal.y), (int)(600 - limit.getAbsY() - limit.size / 2 * limit.normal.x));
						}

					g.setColor(body.getColliders().length > 0 && body.getColliders()[0] instanceof Limit ? Color.BLACK : Color.WHITE);
					g.drawString("" + n++, (int)body.getPosition().x, (int)(600 - body.getPosition().y));
				}
			}
		};

		panel.addKeyListener(this);

		setContentPane(panel);

		setVisible(true);
		panel.requestFocusInWindow();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public void start()
	{
		start(false);
	}

	public void start(boolean paused)
	{
		if(paused)
			framesRemaining = 0;

		while(true)
		{
			if(framesRemaining != 0)
			{
				try
				{
					long start = System.nanoTime();
					SwingUtilities.invokeAndWait(() ->
					{
						world.step(1f / frameRate);
						if(framesRemaining > 0)
							framesRemaining--;
						frames++;
						repaint();
					});
					long toWait = Math.round(1000 / frameRate) - (System.nanoTime() - start) / 1_000_000;

					if(toWait <= 0)
						continue;

					Thread.sleep(toWait);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
			else
			{
				try
				{
					SwingUtilities.invokeAndWait(this::repaint);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_P)
		{
			if(framesRemaining == -1)
				framesRemaining = 0;
			else
				framesRemaining = -1;
		}

		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			if(framesRemaining != -1)
				framesRemaining++;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}
}
