package me.winter.boing.physics.resolver;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.Solid;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class ReplaceResolver extends CollisionResolver
{
	private Vector2 tmpVector = new Vector2();

	public ReplaceResolver(float priority)
	{
		super(priority);
	}

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

				replace(dsA, collision.normalB, getMassRatio(dsB, dsA)  * collision.penetration);
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

				replace(dsB, collision.normalA, getMassRatio(dsA, dsB) * collision.penetration);
			}
		}
	}

	private void replace(DynamicSolid solid, Vector2 normal, float delta)
	{
		tmpVector.set(normal).nor().scl(delta);

		solid.getPosition().add(tmpVector);
	}

	private float getMassRatio(DynamicSolid solid, DynamicSolid other)
	{
		if(solid.getMass() == Float.POSITIVE_INFINITY)
		{
			if(other.getMass() == Float.POSITIVE_INFINITY)
				return 0.5f;
			return 1f;
		}

		if(other.getMass() == Float.POSITIVE_INFINITY)
			return 0f;

		if(solid.getMass() == 0f)
		{
			if(other.getMass() == 0f)
				return 0.5f;
			return 0f;
		}

		if(other.getMass() == 0f)
			return 1f;


		return solid.getMass() / (solid.getMass() + other.getMass());
	}
}
