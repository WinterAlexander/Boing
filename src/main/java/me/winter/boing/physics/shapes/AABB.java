package me.winter.boing.physics.shapes;

import me.winter.boing.physics.Collision;
import me.winter.boing.physics.Solid;

import static me.winter.boing.physics.CollisionDetection.boxBox;
import static me.winter.boing.physics.CollisionDetection.boxCircle;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class AABB extends AbstractShape
{
	public float width, height;

	public AABB(Solid solid, float x, float y, float width, float height)
	{
		super(solid, x, y);
		this.width = width;
		this.height = height;
	}

	@Override
	public Collision collides(Shape shape)
	{
		if(shape instanceof AABB)
			return boxBox(this, (AABB)shape, solid.getWorld().collisionPool);

		if(shape instanceof Circle)
			return boxCircle(this, (Circle)shape, solid.getWorld().collisionPool);


		return null;
	}
}
