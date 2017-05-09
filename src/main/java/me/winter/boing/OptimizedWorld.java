package me.winter.boing;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.detection.DetectionHandler;
import me.winter.boing.resolver.CollisionResolver;
import me.winter.boing.shapes.Collider;
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

				collision.weightRatio = -1f; //not set

				swappedBuffer.setAsSwapped(collision);

				if(colliderA.getBody().cancelCollision(collision) || colliderB.getBody().cancelCollision(swappedBuffer))
					continue;

				if(bodyB instanceof DynamicBody || bodyA instanceof DynamicBody) //at least one have to be able to move to resolve it...
				{
					resolveWeights(collision, swappedBuffer);
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

	private void resolveWeights(Collision collision, Collision miroir)
	{
		if(!(collision.colliderA.getBody() instanceof DynamicBody))
		{
			collision.weightRatio = 1f;
			miroir.weightRatio = 0f;
			return;
		}
		else if(!(collision.colliderB.getBody() instanceof DynamicBody))
		{
			collision.weightRatio = 0f;
			miroir.weightRatio = 1f;
			return;
		}

		DynamicBody dynA = (DynamicBody)collision.colliderA.getBody();
		DynamicBody dynB = (DynamicBody)collision.colliderB.getBody();

		collision.weightRatio = VelocityUtil.getWeightRatio(getWeight(dynA, collision.normalB, dynB), getWeight(dynB, collision.normalA, dynA));
		miroir.weightRatio = 1f - collision.weightRatio;
	}

	private float getWeight(DynamicBody body, Vector2 normal, DynamicBody against)
	{
		float bestWeight = body.getWeight(against);

		Collision swapped = collisionPool.obtain();

		for(int i = 0; i < prevCollisions.size; i++)
		{
			Collision collision = prevCollisions.get(i);

			if(collision.colliderB.getBody() == body)
			{
				swapped.setAsSwapped(collision);
				collision = swapped;
			}

			if(collision.colliderA.getBody() == body)
			{
				if(collision.colliderB.getBody() == against)
					continue;

				if(collision.normalA.dot(normal) > 0.7)
				{
					if(!(collision.colliderB.getBody() instanceof DynamicBody))
						return POSITIVE_INFINITY;

					float currentWeight = getWeight((DynamicBody)collision.colliderB.getBody(), normal, against);
					//float currentWeight = body.getWeight(against);

					if(currentWeight > bestWeight)
						bestWeight = currentWeight;

				}
			}
		}

		collisionPool.free(swapped);

		return bestWeight;
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

			resolver.resolve(collision);
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
}
