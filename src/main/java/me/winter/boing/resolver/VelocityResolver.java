package me.winter.boing.resolver;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;
import me.winter.boing.PhysicsWorld;
import me.winter.boing.util.VelocityUtil;

/**
 * CollisionResolver resolving collisions by changing the velocity of objects colliding
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class VelocityResolver implements CollisionResolver
{
	@Override
	public boolean resolveCollision(Collision collision, PhysicsWorld world)
	{
		if(collision.colliderA.getBody() instanceof DynamicBody)
			reflect((DynamicBody)collision.colliderA.getBody(), collision.normal);

		if(collision.colliderB.getBody() instanceof DynamicBody)
			reflect((DynamicBody)collision.colliderB.getBody(), collision.normal);

		return true;
	}

	private void reflect(DynamicBody solid, Vector2 normal)
	{
		if(normal.dot(solid.getVelocity()) > 0) //not pointing toward surface anymore
			return;

		VelocityUtil.reflect(solid.getVelocity(), normal);
	}
}
