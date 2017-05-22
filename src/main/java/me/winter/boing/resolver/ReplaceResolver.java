package me.winter.boing.resolver;

import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;

import static java.lang.Math.signum;

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

			replace(solid, replaceX, replaceY);
		}

		if(delta > 0)
		{
			DynamicBody solid = (DynamicBody)collision.colliderB.getBody();

			float replaceX = collision.normalA.x * delta;
			float replaceY = collision.normalA.y * delta;

			replace(solid, replaceX, replaceY);
		}
	}

	private void replace(DynamicBody solid, float replaceX, float replaceY)
	{
		if(replaceX != 0f)
		{
			float dirX = signum(solid.getCollisionShifting().x);

			if(dirX == 0 || replaceX * dirX > solid.getCollisionShifting().x * dirX)
				solid.getCollisionShifting().x = replaceX;
			else if(dirX != signum(replaceX))
				solid.getCollisionShifting().x += replaceX;
		}

		if(replaceY != 0f)
		{
			float dirY = signum(solid.getCollisionShifting().y);

			if(dirY == 0 || replaceY * dirY > solid.getCollisionShifting().y * dirY)
				solid.getCollisionShifting().y = replaceY;
			else if(dirY != signum(replaceY))
				solid.getCollisionShifting().y += replaceY;
		}
	}
}
