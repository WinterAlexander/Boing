package me.winter.boing.test.physics.testimpl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.physics.Body;
import me.winter.boing.physics.shapes.Box;
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
public class BodyImpl implements Body, SimulationElement
{
	private Vector2 position = new Vector2();

	private Array<Collider> colliders = new Array<>();

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
		for(Collider collider : colliders)

			if(collider instanceof Circle)
			{
				float r = ((Circle)collider).radius;
				g.drawOval((int)(getPosition().x - r), (int)(600 - getPosition().y - r), (int)r * 2, (int)r * 2);
			}
			else if(collider instanceof Box)
			{
				float w = ((Box)collider).width;
				float h = ((Box)collider).height;
				g.drawRect((int)(getPosition().x - w / 2), (int)(600 - getPosition().y - h / 2), (int)w, (int)h);
			}
			else if(collider instanceof Limit)
			{
				Limit limit = (Limit)collider;
				g.drawLine(
						(int)(limit.getAbsX() - limit.size / 2 * limit.normal.y),
						(int)(600 - limit.getAbsY() + limit.size / 2 * limit.normal.x),
						(int)(limit.getAbsX() + limit.size / 2 * limit.normal.y),
						(int)(600 - limit.getAbsY() - limit.size / 2 * limit.normal.x));
			}
	}
}
