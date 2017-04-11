package me.winter.boing.physics.resolver;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicSolid;

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
			replace((DynamicSolid)collision.colliderA.getSolid(),
					collision.normalB,
					solidBDyn ? collision.penetration / 2 : collision.penetration);

		if(solidBDyn)
			replace((DynamicSolid)collision.colliderB.getSolid(),
					collision.normalA,
					solidADyn ? collision.penetration / 2 : collision.penetration);

		//System.out.println("COLLISION");
	}

	private void replace(DynamicSolid solid, Vector2 normal, float delta)
	{
		tmpVector.set(normal).nor().scl(delta);

		solid.getPosition().add(tmpVector);
	}
}
