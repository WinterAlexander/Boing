package me.winter.boing.physics.shapes;

import me.winter.boing.physics.Collision;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class AABB implements Shape
{
	@Override
	public Collision collides(Shape shape)
	{
		Circle that = (Circle)shape;

		Collision collision = ((Circle)shape).getSolid().getWorld().collisionPool.obtain();

		collision.normalA.set(-dx, -dy);
		collision.normalB.set(dx, dy);

		collision.contact.set(-dx, -dy).scl(radius / r).add(getAbsX(), getAbsY());

		return collision;
	}
}
