package me.winter.boing;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.colliders.Collider;
import me.winter.boing.detection.DetectionHandler;
import me.winter.boing.resolver.CollisionResolver;
import me.winter.boing.util.CollisionPool;

/**
 * Abstract implementation for World. Pools its collision objects and
 * able to reduce the collision detection process by preventing checking
 * collisions for 2 non movable bodies repetitively
 * <p>
 * Created by Alexander Winter on 2017-05-08.
 */
public abstract class AbstractWorld implements World
{
	/**
	 * Collision pool to prevent creating new collisions objects.
	 */
	protected final Pool<Collision> collisionPool = new CollisionPool();

	/**
	 * Collisions occuring in the current frame
	 */
	protected Array<Collision> collisions = new Array<>();

	protected DetectionHandler detector;
	protected CollisionResolver resolver;

	private boolean refresh;

	public AbstractWorld(CollisionResolver resolver)
	{
		this.detector = new DetectionHandler(collisionPool);
		this.resolver = resolver;
	}

	protected boolean DEBUG_DABOX = false;

	@Override
	public void step(float delta)
	{
		if(DEBUG_DABOX)
			System.out.println();
		update(delta);

		detectCollisions();

		collisions.sort((c1, c2) -> -Float.compare(c1.priority, c2.priority));

		if(DEBUG_DABOX)
			System.out.println("Resolve:");
		resolveCollisions();
	}

	/**
	 * Updates the game objects by looping through them and make the DynamicBody objects move
	 *
	 * @param delta time since last update
	 */
	protected abstract void update(float delta);

	protected void detectCollisions()
	{
		/*if(refresh)
		{
			fullDetection();
			refresh = false;
		}
		else
		{//*/
			dynamicDetection();
		//}//*/
	}

	/**
	 * Detects collision with 1 + 2 + 3 + ... + (N - 1) iterations, N being the number of bodies.
	 */
	protected abstract void fullDetection();

	/**
	 * Detects collision with D + (D + 1) + (D + 2) + ... + (N - 1) iterations, N being the amount of bodies and D being the amount of dynamic bodies
	 */
	protected abstract void dynamicDetection();

	protected void detect(Body bodyA, Body bodyB, Collision swappedBuffer)
	{
		boolean atLeastOneDyn = bodyB instanceof DynamicBody || bodyA instanceof DynamicBody;

		for(Collider colliderA : bodyA.getColliders())
		{
			for(Collider colliderB : bodyB.getColliders())
			{
				Collision collision = detector.collides(this, colliderA, colliderB);

				if(collision == null)
					continue;

				swappedBuffer.setAsSwapped(collision);

				if(colliderA.getBody().cancelCollision(collision) || colliderB.getBody().cancelCollision(swappedBuffer))
					continue;

				if(atLeastOneDyn) //at least one have to be able to move to resolve it...
				{
					collisions.add(collision);

					if(bodyA instanceof DynamicBody)
					{
						Collision copy = collisionPool.obtain();
						copy.set(collision);
						getState((DynamicBody)bodyA).getCollisions().add(copy);
					}

					if(bodyB instanceof DynamicBody)
					{
						Collision copy = collisionPool.obtain();
						copy.setAsSwapped(collision);
						getState((DynamicBody)bodyB).getCollisions().add(copy);
					}
				}
				else
				{
					colliderA.getBody().notifyCollision(collision);
					bodyB.notifyCollision(swappedBuffer);

					collisionPool.free(collision);
				}
			}
		}
	}

	protected void resolveCollisions()
	{
		Collision swapped = collisionPool.obtain();

		for(Collision collision : collisions)
		{
			if(resolver.resolve(collision, this))
			{
				swapped.setAsSwapped(collision);
				collision.colliderA.getBody().notifyCollision(collision);
				collision.colliderB.getBody().notifyCollision(swapped);
			}
		}

		collisionPool.free(swapped);
		collisionPool.freeAll(collisions);
		collisions.clear();
	}


	public boolean isRefreshing()
	{
		return refresh;
	}

	public void refresh()
	{
		this.refresh = true;
	}

	public Pool<Collision> getCollisionPool()
	{
		return collisionPool;
	}

	public Array<Collision> getCollisions()
	{
		return collisions;
	}
}
