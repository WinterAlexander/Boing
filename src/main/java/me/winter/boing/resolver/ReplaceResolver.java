package me.winter.boing.resolver;

import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;
import me.winter.boing.World;

import static me.winter.boing.util.FloatUtil.areEqual;
import static me.winter.boing.util.FloatUtil.isGreaterOrEqual;
import static me.winter.boing.util.FloatUtil.isSmallerOrEqual;

/**
 * CollisionResolver resolving collisions by replacing the position of the objects colliding
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class ReplaceResolver implements CollisionResolver
{
	@Override
	public boolean resolveCollision(Collision collision, World world)
	{
		float pene = collision.getPenetration();
		float surface = collision.getContactSurface();

		if(pene <= 0 || isSmallerOrEqual(surface, 0))
		{
			if(areEqual(pene, 0) && isGreaterOrEqual(surface, 0))
				return true; //fake it
			return false;
		}

		float ratio = collision.weightRatio;

		if(ratio != 1)
		{
			float amount = (1f - ratio) * pene;

			world.getState((DynamicBody)collision.colliderA.getBody()).shift(amount * -collision.normal.x, amount * -collision.normal.y);
		}

		if(ratio != 0)
		{
			float amount = ratio * pene;

			world.getState((DynamicBody)collision.colliderB.getBody()).shift(amount * collision.normal.x, amount * collision.normal.y);
		}

		return true;
	}
}
