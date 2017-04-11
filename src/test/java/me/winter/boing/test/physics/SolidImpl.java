package me.winter.boing.test.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.physics.Collider;
import me.winter.boing.physics.Solid;
import me.winter.boing.physics.World;
import me.winter.boing.physics.shapes.AABB;
import me.winter.boing.physics.shapes.Circle;
import me.winter.boing.physics.shapes.Shape;

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
		Shape shape = getColliders().get(0).getShape();

		if(shape instanceof Circle)
		{
			float r = ((Circle)shape).radius;
			g.drawOval((int)(getPosition().x - r), (int)(600 - getPosition().y - r), (int)r * 2, (int)r * 2);
		}
		else if(shape instanceof AABB)
		{
			float w = ((AABB)shape).width;
			float h = ((AABB)shape).height;
			g.drawRect((int)(getPosition().x - w / 2), (int)(600 - getPosition().y - h / 2), (int)w, (int)h);
		}
	}
}
