package me.winter.boing.physics.resolver;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.DynamicBody;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.util.VelocityUtil;

import static java.lang.Math.signum;

/**
 * CollisionResolver resolving collisions by changing their velocity
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class VelocityResolver implements CollisionResolver
{
	@Override
	public void resolve(Collision collision, float weightA, float weightB)
	{
		if(collision.colliderA.getBody() instanceof DynamicBody)
			reflect((DynamicBody)collision.colliderA.getBody(), collision.normalB);

		if(collision.colliderB.getBody() instanceof DynamicBody)
			reflect((DynamicBody)collision.colliderB.getBody(), collision.normalA);
	}

	private void reflect(DynamicBody solid, Vector2 normal)
	{
		if(normal.dot(solid.getVelocity()) > 0) //not pointing toward surface anymore
			return;

		VelocityUtil.reflect(solid.getVelocity(), normal);
	}
}
