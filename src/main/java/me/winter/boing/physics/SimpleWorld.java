package me.winter.boing.physics;

import me.winter.boing.physics.detection.DetectionHandler;
import me.winter.boing.physics.resolver.CollisionResolver;
import me.winter.boing.physics.shapes.Collider;
import me.winter.boing.physics.util.iterator.IndexIterator;
import me.winter.boing.physics.util.iterator.ReusableIterator;

/**
 * Simple abstract implementation of a World detecting and resolving collisions.
 * Does not contain the objects by itself but access them by iterator to implement.
 * <p>
 * Note: the test folder contains a basic (full) implementation of this class using
 * a LibGDX array
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
	 * @return An iterator for all bodies
	 */
	protected abstract IndexIterator<Body> getBodyIterator();

	/**
	 * @return An iterator only for dynamic bodies
	 */
	protected abstract ReusableIterator<DynamicBody> getDynamicIterator();

	@Override
	protected void update(float delta)
	{
		for(DynamicBody dynamic : getDynamicIterator())
			if(dynamic instanceof UpdatableBody)
				((UpdatableBody)dynamic).update(delta);

		for(DynamicBody dynamic : getDynamicIterator())
		{
			dynamic.getMovement().set(dynamic.getVelocity()).scl(delta);
			dynamic.getPosition().add(dynamic.getMovement());

			dynamic.getMovement().add(dynamic.getCollisionShifing());
			dynamic.getCollisionShifing().setZero();
		}
	}

	@Override
	protected void detectCollisions()
	{
		int size = getBodyIterator().size();

		for(int i = size; i-- >= 0;)
		{
			for(int j = i; j-- > 0;)
			{
				Body bodyA = getBodyIterator().objectAt(i);
				Body bodyB = getBodyIterator().objectAt(j);

				boolean aDyn = bodyA instanceof DynamicBody;
				boolean bDyn = bodyB instanceof DynamicBody;

				if(!aDyn && !bDyn)
					continue;

				for(int k = 0; k < bodyA.getColliders().length; k++)
				{
					Collider colliderA = bodyA.getColliders()[k];

					for(Collider colliderB : bodyB.getColliders())
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

			if(collision.colliderA.getBody().notifyCollision(collision) && collision.colliderB.getBody().notifyCollision(swapped))
				resolver.resolve(collision);
		}

		collisionPool.free(swapped);
	}
}
