package me.winter.boing.physics.v2.colliders;

import me.winter.boing.physics.v2.Solid;
import me.winter.boing.physics.v2.response.CollisionResponse;
import me.winter.boing.physics.v2.response.VelocityResponse;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class CircleCollider extends Collider
{
	private float radius;

	public CircleCollider(Solid solid, float x, float y, float radius)
	{
		super(solid, x, y);
		this.radius = radius;
	}

	@Override
	public CollisionResponse collides(Collider collider)
	{
		CircleCollider that = (CircleCollider)collider;

		float dx = getAbsX() - that.getAbsX();
		float dy = getAbsY() - that.getAbsY();

		float r = radius + that.radius;

		float dst2 = dx * dx + dy * dy;

		if(dst2 > r * r)
			return CollisionResponse.NONE;

		VelocityResponse velocityResponse = new VelocityResponse();

		float x = -collider.getSolid().getVelocity().x; //to reflect
		float y = -collider.getSolid().getVelocity().y; //to reflect

		if(dx == 0)
		{
			velocityResponse.getVel().set(-x, y);
			return velocityResponse;
		}

		float a = dy / dx;

		float d = (x + y * a) / (1 + a * a);

		velocityResponse.getVel().set(2 * d - x, 2 * d * a - y);

		return velocityResponse;
	}

	@Override
	public CollisionResponse collidesContinuous(Collider collider)
	{
		CircleCollider that = (CircleCollider)collider;
		return CollisionResponse.NONE;
	}

	public float getRadius()
	{
		return radius;
	}
}
