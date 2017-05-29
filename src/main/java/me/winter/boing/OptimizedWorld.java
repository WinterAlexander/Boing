package me.winter.boing;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.detection.DetectionHandler;
import me.winter.boing.resolver.CollisionResolver;
import me.winter.boing.colliders.Collider;
import me.winter.boing.util.VelocityUtil;

import static java.lang.Float.POSITIVE_INFINITY;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-08.
 */
public abstract class OptimizedWorld extends AbstractWorld
{
	/**
	 * Collisions occuring in the current frame
	 */
	protected Array<Collision> collisions = new Array<>();
	protected Array<Collision> prevCollisions = new Array<>();

	protected DetectionHandler detector;
	protected CollisionResolver resolver;

	protected boolean refresh;

	private Vector2 tmpVector = new Vector2();

	public OptimizedWorld(CollisionResolver resolver)
	{
		this.detector = new DetectionHandler(collisionPool);
		this.resolver = resolver;
	}

	@Override
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
		for(Collider colliderA : bodyA.getColliders())
		{
			for(Collider colliderB : bodyB.getColliders())
			{
				Collision collision = detector.collides(colliderA, colliderB);

				if(collision == null)
					continue;

				swappedBuffer.setAsSwapped(collision);

				if(colliderA.getBody().cancelCollision(collision) || colliderB.getBody().cancelCollision(swappedBuffer))
					continue;

				if(bodyB instanceof DynamicBody || bodyA instanceof DynamicBody) //at least one have to be able to move to resolve it...
				{
					collisions.add(collision);
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

	@Override
	protected void resolveCollisions()
	{
		Collision swapped = collisionPool.obtain();

		for(Collision collision : collisions)
		{
			swapped.setAsSwapped(collision);
			collision.colliderA.getBody().notifyCollision(collision);
			collision.colliderB.getBody().notifyCollision(swapped);

			resolver.resolve(collision, this);
		}

		collisionPool.free(swapped);
		collisionPool.freeAll(prevCollisions);
		prevCollisions.clear();

		Array<Collision> tmp = prevCollisions;

		prevCollisions = collisions;
		collisions = tmp;
	}

	public boolean isRefreshing()
	{
		return refresh;
	}

	public void refresh()
	{
		this.refresh = true;
	}

	@Override
	public Array<Collision> getCollisions()
	{
		return collisions;
	}
}
