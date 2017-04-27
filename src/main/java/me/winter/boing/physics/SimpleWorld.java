package me.winter.boing.physics;

import me.winter.boing.physics.detection.DetectionHandler;
import me.winter.boing.physics.resolver.CollisionResolver;
import me.winter.boing.physics.shapes.Collider;
import me.winter.boing.physics.util.iterator.IndexIterator;
import me.winter.boing.physics.util.iterator.ReusableIterator;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-25.
 */
public abstract class SimpleWorld extends AbstractWorld
{
	private DetectionHandler mapper;
	private CollisionResolver resolver;

	public SimpleWorld(CollisionResolver resolver)
	{
		this.mapper = new DetectionHandler(collisionPool);
		this.resolver = resolver;
	}

	/**
	 * @return An iterator for all solids
	 */
	protected abstract IndexIterator<Solid> getSolidIterator();

	/**
	 * @return An iterator only for dynamic solids
	 */
	protected abstract ReusableIterator<DynamicSolid> getDynamicIterator();

	@Override
	protected void update(float delta)
	{
		for(DynamicSolid dynamic : getDynamicIterator())
			dynamic.update();

		for(DynamicSolid dynamic : getDynamicIterator())
		{
			dynamic.getMovement().set(dynamic.getVelocity()).scl(delta);
			dynamic.getPosition().add(dynamic.getMovement());

			dynamic.getMovement().add(dynamic.getLastReplacement());
			dynamic.getLastReplacement().setZero();
		}
	}

	@Override
	protected void detectCollisions()
	{
		int size = getSolidIterator().size();

		for(int i = size; i-- >= 0;)
		{
			for(int j = i; j-- > 0;)
			{
				Solid solidA = getSolidIterator().objectAt(i);
				Solid solidB = getSolidIterator().objectAt(j);

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
	}

	@Override
	protected void resolveCollisions()
	{
		Collision swapped = collisionPool.obtain();

		for(Collision collision : collisions)
		{
			swapped.setAsSwapped(collision);

			if(collision.colliderA.getSolid().collide(collision) && collision.colliderB.getSolid().collide(swapped))
				resolver.resolve(collision);
		}

		collisionPool.free(swapped);
	}
}
