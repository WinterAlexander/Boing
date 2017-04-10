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

		Collision collision = new Collision();

		float x = that.getSolid().getVelocity().x; //to reflect
		float y = that.getSolid().getVelocity().y; //to reflect

		if(dy == 0)//same height means vertical tangent
		{
			velocityResponse.getVel().set(-x, y);
			return velocityResponse;
		}

		float a = -dx / dy;

		float d = (x + y * a) / (1 + a * a);

		velocityResponse.getVel().set(2 * d - x, 2 * d * a - y);

		return velocityResponse;
	}

	@Override
	public Collision collidesContinuous(Shape shape)
	{
		Circle that = (Circle)shape;
		return null;
	}

	public float getRadius()
	{
		return radius;
	}
}
