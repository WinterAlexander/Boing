package me.winter.boing.physics.v2;

import com.badlogic.gdx.utils.Array;
import me.winter.boing.physics.v2.colliders.Collider;
import me.winter.boing.physics.v2.response.CollisionResponse;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class World
{
	private Array<Solid> solids = new Array<>();

	public void step(float delta)
	{
		for(Solid solid : getSolids())
		{
			solid.getMovement().set(solid.getVelocity()).scl(delta);
			solid.getPosition().add(solid.getMovement());
		}

		int size = solids.size;

		for(int i = size; i-- >= 0;)
		{
			for(int j = i; j-- > 0;)
			{
				Solid solidA = solids.get(i);
				Solid solidB = solids.get(i);

				for(int k = 0; k < solidA.getColliders().size; k++)
				{
					Collider colliderA = solidA.getColliders().get(k);

					for(Collider colliderB : solidB.getColliders())
					{
						CollisionResponse responseA = colliderA.collides(colliderB);
						CollisionResponse responseB = colliderB.collides(colliderA);

						solidA.responses().add(responseB);
						solidB.responses().add(responseA);
					}
				}
			}
		}

		for(Solid solid : getSolids())
		{
			for(CollisionResponse response : solid.responses())
				response.apply(solid);

			solid.setVelFresh(false);
		}
	}

	public Array<Solid> getSolids()
	{
		return solids;
	}
}
