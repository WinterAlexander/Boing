package me.winter.boing.test.physics.testimpl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.physics.Solid;
import me.winter.boing.physics.World;
import me.winter.boing.physics.shapes.AABB;
import me.winter.boing.physics.shapes.Circle;
import me.winter.boing.physics.shapes.Collider;
import me.winter.boing.physics.shapes.Limit;
import me.winter.boing.test.physics.simulation.SimulationElement;

import java.awt.Graphics;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class SolidImpl implements Solid, SimulationElement
{
	private World world;
	private Vector2 position;

	private Array<Collider> colliders = new Array<>();

	public SolidImpl(World world)
	{
		this.world = world;
		position = new Vector2();
	}

	@Override
	public World getWorld()
	{
		return world;
	}

	@Override
	public Vector2 getPosition()
	{
		return position;
	}

	@Override
	public Array<Collider> getColliders()
	{
		return colliders;
	}

	@Override
	public void draw(Graphics g)
	{
		Collider collider = getColliders().get(0);

		if(collider instanceof Circle)
		{
			float r = ((Circle)collider).radius;
			g.drawOval((int)(getPosition().x - r), (int)(600 - getPosition().y - r), (int)r * 2, (int)r * 2);
		}
		else if(collider instanceof AABB)
		{
			float w = ((AABB)collider).width;
			float h = ((AABB)collider).height;
			g.drawRect((int)(getPosition().x - w / 2), (int)(600 - getPosition().y - h / 2), (int)w, (int)h);
		}
		else if(collider instanceof Limit)
		{
			Limit limit = (Limit)collider;
			g.drawLine((int)limit.getPoint1().x, (int)(600 - limit.getPoint1().y), (int)limit.getPoint2().x, (int)(600 - limit.getPoint2().y));
		}
	}
}
