package me.winter.boing.resolver;

import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;

import static java.lang.Math.abs;

/**
 * CollisionResolver resolving collisions by replacing the objects colliding (if they are Dynamic)
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class ReplaceResolver implements CollisionResolver
{
	@Override
	public void resolve(Collision collision)
	{
		float delta = collision.weightRatio * collision.penetration;

		if(delta < collision.penetration)
		{
			DynamicBody solid = (DynamicBody)collision.colliderA.getBody();

			float replaceX = collision.normalB.x * collision.penetration - collision.normalB.x * delta;
			float replaceY = collision.normalB.y * collision.penetration - collision.normalB.y * delta;

			solid.getMovement().x += replaceX;
			solid.getMovement().y += replaceY;
		}

		if(delta > 0)
		{
			DynamicBody solid = (DynamicBody)collision.colliderB.getBody();

			float replaceX = collision.normalA.x * delta;
			float replaceY = collision.normalA.y * delta;

			solid.getMovement().x += replaceX;
			solid.getMovement().y += replaceY;
		}
	}
}
