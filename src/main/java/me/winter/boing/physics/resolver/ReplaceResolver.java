package me.winter.boing.physics.resolver;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.Solid;

import static me.winter.boing.physics.VelocityUtil.getMassRatio;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class ReplaceResolver implements CollisionResolver
{
	private Vector2 tmpVector = new Vector2();

	@Override
	public void resolve(Collision collision)
	{
		boolean solidADyn = collision.colliderA.getSolid() instanceof DynamicSolid;
		boolean solidBDyn = collision.colliderB.getSolid() instanceof DynamicSolid;

		if(solidADyn)
		{
			DynamicSolid dsA = (DynamicSolid)collision.colliderA.getSolid();

			if(!solidBDyn)
			{
				replace(dsA, collision.normalB, collision.penetration);
			}
			else
			{
				DynamicSolid dsB = (DynamicSolid)collision.colliderB.getSolid();
				replace(dsA, collision.normalB, getMassRatio(dsB.getMass(), dsA.getMass())  * collision.penetration);
			}
		}

		if(solidBDyn)
		{
			DynamicSolid dsB = (DynamicSolid)collision.colliderB.getSolid();

			if(!solidADyn)
			{
				replace(dsB, collision.normalA, collision.penetration);
			}
			else
			{
				DynamicSolid dsA = (DynamicSolid)collision.colliderA.getSolid();
				replace(dsB, collision.normalA, getMassRatio(dsA.getMass(), dsB.getMass()) * collision.penetration);
			}
		}
	}

	private void replace(DynamicSolid solid, Vector2 normal, float delta)
	{
		tmpVector.set(normal).nor().scl(delta);

		solid.getPosition().add(tmpVector);
	}
}
