package me.winter.boing.physics.v2.shapes;

import me.winter.boing.physics.v2.Collision;
import me.winter.boing.physics.v2.Solid;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class Circle extends AbstractShape
{
	private float radius;

	public Circle(Solid solid, float x, float y, float radius)
	{
		super(solid, x, y);
		this.radius = radius;
	}

	@Override
	public Collision collides(Shape shape)
	{
		Circle that = (Circle)shape;

		float dx = getAbsX() - that.getAbsX();
		float dy = getAbsY() - that.getAbsY();

		float r = radius + that.radius;

		float dst2 = dx * dx + dy * dy;

		if(dst2 >= r * r)
			return null;

		Collision collision = ((Circle)shape).getSolid().getWorld().collisionPool.obtain();

		collision.normalA.set(-dx, -dy);
		collision.normalB.set(dx, dy);

		collision.contact.set(-dx, -dy).scl(radius / r).add(getAbsX(), getAbsY());

		return collision;
	}

	public float getRadius()
	{
		return radius;
	}
}
