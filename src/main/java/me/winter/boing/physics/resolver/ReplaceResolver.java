package me.winter.boing.physics.resolver;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicBody;

import static me.winter.boing.physics.util.VelocityUtil.getMassRatio;

/**
 * CollisionResolver resolving collisions by replacing the objects colliding (if they are Dynamic)
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class ReplaceResolver implements CollisionResolver
{
	private Vector2 tmpVector = new Vector2();

	@Override
	public void resolve(Collision collision)
	{
		boolean solidADyn = collision.colliderA.getBody() instanceof DynamicBody;
		boolean solidBDyn = collision.colliderB.getBody() instanceof DynamicBody;

		if(solidADyn)
		{
			DynamicBody dsA = (DynamicBody)collision.colliderA.getBody();

			if(!solidBDyn)
			{
				replace(dsA, collision.normalB, collision.penetration);
			}
			else
			{
				DynamicBody dsB = (DynamicBody)collision.colliderB.getBody();
				replace(dsA, collision.normalB, getMassRatio(dsB.getWeight(dsA), dsA.getWeight(dsB))  * collision.penetration);
			}
		}

		if(solidBDyn)
		{
			DynamicBody dsB = (DynamicBody)collision.colliderB.getBody();

			if(!solidADyn)
			{
				replace(dsB, collision.normalA, collision.penetration);
			}
			else
			{
				DynamicBody dsA = (DynamicBody)collision.colliderA.getBody();
				replace(dsB, collision.normalA, getMassRatio(dsA.getWeight(dsB), dsB.getWeight(dsA)) * collision.penetration);
			}
		}
	}

	private void replace(DynamicBody solid, Vector2 normal, float delta)
	{
		tmpVector.set(normal).nor().scl(delta);

		solid.getPosition().add(tmpVector);
		solid.getCollisionShifing().add(tmpVector);
	}
}
