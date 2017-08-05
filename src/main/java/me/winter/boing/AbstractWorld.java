package me.winter.boing;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.CollisionDynamicVariable.Inverter;
import me.winter.boing.colliders.Collider;
import me.winter.boing.detection.DetectionHandler;
import me.winter.boing.resolver.CappedWeightResolver;
import me.winter.boing.resolver.CollisionResolver;
import me.winter.boing.resolver.WeightResolver;
import me.winter.boing.util.CollisionPool;
import me.winter.boing.util.Wrapper;

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

	protected final Wrapper<CollisionDynamicVariable, CollisionDynamicVariable> varInverter = new Inverter();

	/**
	 * Collisions occuring in the current frame
	 */
	protected Array<Collision> collisions = new Array<>();

	protected DetectionHandler detector;
	protected CollisionResolver resolver;
	protected WeightResolver weightResolver;

	private boolean refresh = true;

	public AbstractWorld(CollisionResolver resolver)
	{
		this(resolver, new CappedWeightResolver());
	}

	public AbstractWorld(CollisionResolver resolver, WeightResolver weightResolver)
	{
		this.detector = new DetectionHandler(collisionPool, varInverter);
		this.resolver = resolver;
		this.weightResolver = weightResolver;
	}

	//protected boolean DEBUG_DABOX = false;

	@Override
	public void step(float delta)
	{
		//if(DEBUG_DABOX)
		//	System.out.println();
		update(delta);

		detectCollisions();

		collisions.sort((c1, c2) -> -Float.compare(c1.priority, c2.priority));

		//if(DEBUG_DABOX)
		//	System.out.println("Resolve:");
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
		if(refresh)
		{
			fullDetection();
			refresh = false;
		}
		else
		{
			dynamicDetection();
		}
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

				swappedBuffer.setAsSwapped(collision, varInverter);

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
						copy.setAsSwapped(collision, varInverter);
						getState((DynamicBody)bodyB).getCollisions().add(copy);
					}
				}
				else
				{
					collision.weightRatio = 0f;
					colliderA.getBody().notifyCollision(collision);
					swappedBuffer.weightRatio = 0f;
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
			weightResolver.resolveWeight(collision, this);

			if(resolver.resolveCollision(collision, this))
			{
				swapped.setAsSwapped(collision, varInverter);
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
