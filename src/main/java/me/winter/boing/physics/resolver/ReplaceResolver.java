package me.winter.boing.physics.resolver;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicBody;

import static java.lang.Math.abs;
import static me.winter.boing.physics.util.VelocityUtil.getWeightRatio;

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
		float deltaA = getWeightRatio(collision.weightA, collision.weightB);

		float deltaB = 1f - deltaA;

		if(deltaB != 0)
			replace((DynamicBody)collision.colliderA.getBody(), collision.normalB, deltaB * collision.penetration);

		if(deltaA != 0)
			replace((DynamicBody)collision.colliderB.getBody(), collision.normalA, deltaA * collision.penetration);
	}

	private void replace(DynamicBody solid, Vector2 normal, float delta)
	{
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
