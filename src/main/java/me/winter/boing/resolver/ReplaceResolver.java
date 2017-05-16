package me.winter.boing.resolver;

import com.badlogic.gdx.math.Vector2;
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
		float peneB = collision.weightRatio * collision.penetration;
		float peneA = collision.penetration - peneB;

		if(peneA > 0)
			replace((DynamicBody)collision.colliderA.getBody(), collision.normalB, peneA);

		if(peneB > 0)
			replace((DynamicBody)collision.colliderB.getBody(), collision.normalA, peneB);
	}

	private void replace(DynamicBody solid, Vector2 normal, float delta)
	{
		float replaceX = normal.x * delta;
		float replaceY = normal.y * delta;

		if(abs(replaceX) > abs(solid.getCollisionShifing().x))
		{
			solid.getPosition().x += replaceX - solid.getCollisionShifing().x;
			solid.getCollisionShifing().x = replaceX;
		}

		if(abs(replaceY) > abs(solid.getCollisionShifing().y))
		{
			solid.getPosition().y += replaceY - solid.getCollisionShifing().y;
			solid.getCollisionShifing().y = replaceY;
		}
	}
}
