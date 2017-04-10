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
			if(!(solid instanceof DynamicSolid))
				continue;

			((DynamicSolid)solid).getMovement().set(solid.getVelocity()).scl(delta);
			solid.getPosition().add(((DynamicSolid)solid).getMovement());
		}

		int size = solids.size;

		for(int i = size; i-- >= 0;)
		{
			for(int j = i; j-- > 0;)
			{
				Solid solidA = solids.get(i);
				Solid solidB = solids.get(j);

				boolean aDyn = solidA instanceof DynamicSolid;
				boolean bDyn = solidB instanceof DynamicSolid;

				if(!aDyn && !bDyn)
					continue;

				for(int k = 0; k < solidA.getColliders().size; k++)
				{
					Collider colliderA = solidA.getColliders().get(k);

					for(Collider colliderB : solidB.getColliders())
					{
						if(aDyn)
						{
							CollisionResponse responseB = colliderB.collides(colliderA);

							if(responseB != CollisionResponse.NONE)
								((DynamicSolid)solidA).responses().add(responseB);
						}

						if(bDyn)
						{
							CollisionResponse responseA = colliderA.collides(colliderB); //response from a to b

							if(responseA != CollisionResponse.NONE)
								((DynamicSolid)solidB).responses().add(responseA);
						}
					}
				}
			}
		}

		for(Solid solid : getSolids())
		{
			if(!(solid instanceof DynamicSolid))
				continue;

			for(CollisionResponse response : ((DynamicSolid)solid).responses())
				response.apply(((DynamicSolid)solid));

			((DynamicSolid)solid).setVelFresh(false);
		}
	}

	public Array<Solid> getSolids()
	{
		return solids;
	}
}
