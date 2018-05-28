package me.winter.boing.simulation.simulator;

import com.badlogic.gdx.utils.Array;
import me.winter.boing.Body;
import me.winter.boing.DynamicBody;
import me.winter.boing.colliders.Box;
import me.winter.boing.colliders.Circle;
import me.winter.boing.colliders.Collider;
import me.winter.boing.colliders.Limit;
import me.winter.boing.testimpl.TestPhysicsWorldImpl;
import org.junit.Ignore;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
@Ignore
public class BoingSimulator extends JFrame implements KeyListener, MouseListener
{
	public static final Array<Consumer<Graphics>> __freeShapes = new Array<>();
	public static BoingSimulator __last = null;

	private TestPhysicsWorldImpl world;
	private float frameRate;

	private long frames = 0, framesRemaining = -1;
	private boolean deletePressed, velsEnabled;

	private JPanel panel;

	public BoingSimulator(TestPhysicsWorldImpl world, float frameRate)
	{
		this.world = world;
		this.frameRate = frameRate;

		setSize(800, 600);

		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g)
			{
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 800, 600);

				g.setColor(Color.BLACK);
				g.drawString("Collisions: " + world.collisionCount(), 650, 20);
				g.drawString("Frames: " + world.getFrame(), 650, 40);

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

					if(velsEnabled && body instanceof DynamicBody)
					{
						g.setColor(g.getColor().darker());
						DynamicBody dyn = (DynamicBody)body;
						g.drawLine((int)dyn.getPosition().x,
								600 - (int)dyn.getPosition().y,
								(int)(dyn.getPosition().x + dyn.getVelocity().x),
								600 - (int)(dyn.getPosition().y + dyn.getVelocity().y));
					}

					g.setColor(body.getColliders().length > 0 && body.getColliders()[0] instanceof Limit ? Color.BLACK : Color.WHITE);
					g.drawString("" + n++, (int)body.getPosition().x, (int)(600 - body.getPosition().y));
				}

				for(Consumer<Graphics> shape : __freeShapes)
					shape.accept(g);
			}
		};

		panel.addKeyListener(this);
		panel.addMouseListener(this);

		setContentPane(panel);

		setVisible(true);
		panel.requestFocusInWindow();
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		__last = this;
	}

	public void forceRepaint()
	{
		panel.paintImmediately(0, 0, 800, 600);
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
					SwingUtilities.invokeAndWait(() -> {
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

				synchronized(this)
				{
					try
					{
						wait();
					}
					catch(InterruptedException ex)
					{
						ex.printStackTrace();
					}
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

			synchronized(this)
			{
				notify();
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			if(framesRemaining != -1)
				framesRemaining++;

			synchronized(this)
			{
				notify();
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_R)
		{
			deletePressed = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_V)
		{
			velsEnabled = !velsEnabled;
			forceRepaint();
		}
		else if(e.getKeyCode() == KeyEvent.VK_S)
		{
			world.splittedStep = !world.splittedStep;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_R)
		{
			deletePressed = true;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(!deletePressed || framesRemaining != 0)
			return;

		int x = e.getX();
		int y = 600 - e.getY();

		Body toRemove = null;

		bodyLoop:
		for(Body body : world.getBodies())
		{
			for(Collider collider : body.getColliders())
				if(collider instanceof Circle)
				{
					Circle circle = (Circle)collider;
					float r = ((Circle)collider).radius;

					float dx = circle.getAbsX() - x;
					float dy = circle.getAbsY() - y;

					if(dx * dx + dy * dy < r * r)
					{
						toRemove = body;
						break bodyLoop;
					}
				}
				else if(collider instanceof Box)
				{
					Box box = ((Box)collider);
					float dx = box.getAbsX() - x;
					float dy = box.getAbsY() - y;
					if(dx * dx + dy * dy < box.width * box.height) //lazy
					{
						toRemove = body;
						break bodyLoop;
					}
				}
				else if(collider instanceof Limit)
				{
					Limit limit = (Limit)collider;
					float dx = limit.getAbsX() - x;
					float dy = limit.getAbsY() - y;
					if(dx * dx + dy * dy < limit.size * limit.size)
					{
						toRemove = body;
						break bodyLoop;
					}
				}
		}

		final Body lambdaParam = toRemove;
		if(toRemove != null)
		{
			world.remove(lambdaParam);
			try
			{
				this.repaint();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
