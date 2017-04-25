package me.winter.boing.physics;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.detection.DetectionHandler;
import me.winter.boing.physics.resolver.CollisionResolver;
import me.winter.boing.physics.shapes.Collider;
import me.winter.boing.physics.util.CollisionPool;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class WorldImpl implements World
{
	private Array<Solid> solids = new Array<>();
	private Array<Collision> collisions = new Array<>();

	private DetectionHandler mapper;
	private CollisionResolver resolver;

	public final Pool<Collision> collisionPool = new CollisionPool();

	public WorldImpl(CollisionResolver resolver)
	{
		mapper = new DetectionHandler(collisionPool);
		this.resolver = resolver;
	}

	public void step(float delta)
	{
		for(Solid solid : getSolids())
		{
			if(!(solid instanceof DynamicSolid))
				continue;

			DynamicSolid dynamic = (DynamicSolid)solid;

			solid.update();
			dynamic.getMovement().set(dynamic.getVelocity()).scl(delta);
			solid.getPosition().add(dynamic.getMovement());
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
						Collision collision = mapper.collides(colliderA, colliderB);

						if(collision != null)
							collisions.add(collision);
					}
				}
			}
		}

		Collision swapped = collisionPool.obtain();

		for(Collision collision : collisions)
		{
			swapped.setAsSwapped(collision);

			if(collision.colliderA.getSolid().collide(collision) && collision.colliderB.getSolid().collide(swapped))
				resolver.resolve(collision);

		}

		collisionPool.free(swapped);
		collisionPool.freeAll(collisions);
		collisions.clear();
	}

	public Array<Solid> getSolids()
	{
		return solids;
	}

}
