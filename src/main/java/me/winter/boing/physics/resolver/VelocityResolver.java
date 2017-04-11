package me.winter.boing.physics.resolver;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.Collision;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class VelocityResolver extends CollisionResolver
{
	public VelocityResolver(float priority)
	{
		super(priority);
	}

	@Override
	public void resolve(Collision collision)
	{
		if(collision.colliderA.getSolid() instanceof DynamicSolid)
			reflect((DynamicSolid)collision.colliderA.getSolid(), collision.normalB);

		if(collision.colliderB.getSolid() instanceof DynamicSolid)
			reflect((DynamicSolid)collision.colliderB.getSolid(), collision.normalA);
	}

	private void reflect(DynamicSolid solid, Vector2 normal)
	{
		if(normal.dot(solid.getVelocity()) > 0) //not pointing toward surface anymore
			return;

		if(normal.y == 0)//means vertical tangent
		{
			solid.getVelocity().scl(-1, 1);
			return;
		}

		float a = -normal.x / normal.y;
		float d2 = 2 * (solid.getVelocity().x + solid.getVelocity().y * a) / (1 + a * a);

		solid.getVelocity().scl(-1).add(d2, d2 * a);
	}
}
