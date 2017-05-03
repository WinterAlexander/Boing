package me.winter.boing.physics.resolver;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IdentityMap;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicBody;

import static java.lang.Math.abs;
import static me.winter.boing.physics.util.VelocityUtil.getMassRatio;

/**
 * CollisionResolver resolving collisions by replacing the objects colliding (if they are Dynamic)
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class ReplaceResolver implements CollisionResolver
{
	@Override
	public void resolve(Collision collision, float weightA, float weightB)
	{
		if(weightB != 0)
			replace((DynamicBody)collision.colliderA.getBody(), collision.normalB, weightB * collision.penetration);

		if(weightA != 0)
			replace((DynamicBody)collision.colliderB.getBody(), collision.normalA, weightA * collision.penetration);
	}

	private void replace(DynamicBody solid, Vector2 normal, float delta)
	{
		if(delta == 0)
			return;

		float replaceX = normal.x * delta;
		float replaceY = normal.y * delta;

		if(abs(replaceX) > abs(solid.getCollisionShifing().x))
		{
			solid.getPosition().sub(solid.getCollisionShifing().x, 0).add(replaceX, 0);
			solid.getCollisionShifing().x = replaceX;
		}

		if(abs(replaceY) > abs(solid.getCollisionShifing().y))
		{
			solid.getPosition().sub(0, solid.getCollisionShifing().y).add(0, replaceY);
			solid.getCollisionShifing().y = replaceY;
		}
	}
}
