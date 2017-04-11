package me.winter.boing.physics.shapes;

import me.winter.boing.physics.Collision;
import me.winter.boing.physics.Solid;

import static me.winter.boing.physics.CollisionDetection.circleBox;
import static me.winter.boing.physics.CollisionDetection.circleCircle;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class Circle extends AbstractShape
{
	public float radius;

	public Circle(Solid solid, float x, float y, float radius)
	{
		super(solid, x, y);
		this.radius = radius;
	}

	@Override
	public Collision collides(Shape shape)
	{
		if(shape instanceof Circle)
			return circleCircle(this, (Circle)shape, solid.getWorld().collisionPool);

		if(shape instanceof AABB)
			return circleBox(this, (AABB)shape, solid.getWorld().collisionPool);

		return null;
	}
}
