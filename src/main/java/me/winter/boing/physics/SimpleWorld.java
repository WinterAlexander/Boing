package me.winter.boing.physics;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IdentityMap;
import com.badlogic.gdx.utils.Queue;
import me.winter.boing.physics.detection.DetectionHandler;
import me.winter.boing.physics.resolver.CollisionResolver;
import me.winter.boing.physics.shapes.Collider;
import me.winter.boing.physics.util.VelocityUtil;

import java.util.Iterator;

import static me.winter.boing.physics.util.VectorUtil.append;

/**
 * Simple implementation of a World detecting and resolving collisions.
 * <p>
 * Created by Alexander Winter on 2017-04-25.
 */
public class SimpleWorld extends AbstractWorld implements Iterable<Body>
{
	/**
	 * Collisions occuring in the current frame
	 */
	protected final Array<Collision> collisions = new Array<>();


	protected DetectionHandler mapper;
	protected CollisionResolver resolver;

	private Queue<DynamicBody> dynamics = new Queue<>();
	private Queue<Body> all = new Queue<>();

	protected boolean refresh;

	public SimpleWorld(CollisionResolver resolver)
	{
		this.mapper = new DetectionHandler(collisionPool);
		this.resolver = resolver;
	}

	@Override
	protected void update(float delta)
	{
		for(DynamicBody dynamic : dynamics)
			if(dynamic instanceof UpdatableBody)
				((UpdatableBody)dynamic).update(delta);

		for(DynamicBody dynamic : dynamics)
		{
			dynamic.getMovement().set(dynamic.getVelocity()).scl(delta);
			dynamic.getPosition().add(dynamic.getMovement());

			append(dynamic.getMovement(), dynamic.getCollisionShifing());
			dynamic.getCollisionShifing().setZero();
		}
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
	protected void fullDetection()
	{
		int size = all.size;
		Collision swapped = collisionPool.obtain();

		for(int i = size; i-- > 1;)
		{
			for(int j = i; j-- > 0;)
			{
				Body bodyB = all.get(j);

				for(Collider colliderA : all.get(i).getColliders())
				{
					for(Collider colliderB : bodyB.getColliders())
					{
						Collision collision = mapper.collides(colliderA, colliderB);

						if(collision != null)
						{
							if(collision.penetration == 0)
							{
								swapped.setAsSwapped(collision);

								collision.colliderA.getBody().notifyContact(collision);
								collision.colliderB.getBody().notifyContact(swapped);

								collisionPool.free(collision);
								continue;
							}

							if(bodyB instanceof DynamicBody || colliderA.getBody() instanceof DynamicBody) //at least one have to be able to move to resolve it...
								collisions.add(collision);
							else
							{
								swapped.setAsSwapped(collision);

								colliderA.getBody().notifyCollision(collision);
								bodyB.notifyCollision(swapped);

								collisionPool.free(collision);
							}
						}
					}
				}
			}
		}

		collisionPool.free(swapped);
	}

	/**
	 * Detects collision with D + (D + 1) + (D + 2) + ... + (N - 1) iterations, N being the amount of bodies and D being the amount of dynamic bodies
	 */
	protected void dynamicDetection()
	{
		int dyns = dynamics.size - 1;
		int size = all.size;
		Collision swapped = collisionPool.obtain();

		for(int i = 0; i < dyns; i++)
		{
			for(int j = i + 1; j < size; j++)
			{
				Body bodyB = all.get(j);

				for(Collider colliderA : dynamics.get(i).getColliders())
				{
					for(Collider colliderB : bodyB.getColliders())
					{
						Collision collision = mapper.collides(colliderA, colliderB);

						if(collision != null)
						{
							if(collision.penetration == 0)
							{
								swapped.setAsSwapped(collision);

								collision.colliderA.getBody().notifyContact(collision);
								collision.colliderB.getBody().notifyContact(swapped);

								collisionPool.free(collision);
								continue;
							}

							collisions.add(collision);
						}
					}
				}
			}
		}

		collisionPool.free(swapped);
	}

	@Override
	protected void resolveCollisions()
	{
		Collision swapped = collisionPool.obtain();

		for(Collision collision : collisions)
		{
			float weightA, weightB;

			if(!(collision.colliderA.getBody() instanceof DynamicBody))
			{
				weightA = 0f;
				weightB = 1f;
			}
			else if(!(collision.colliderB.getBody() instanceof DynamicBody))
			{
				weightA = 1f;
				weightB = 0f;
			}
			else
			{
				DynamicBody dynA = (DynamicBody)collision.colliderA.getBody();
				DynamicBody dynB = (DynamicBody)collision.colliderB.getBody();

				weightA = VelocityUtil.getMassRatio(dynB.getWeight(dynA), dynA.getWeight(dynB));
				weightB = 1f - weightA;/*

				for(int i = 0; i < collisions.size; i++)
				{
					Collision current = collisions.get(i);

					if(current == collision)
						continue;

					if(current.colliderA.getBody() == dynA)
					{
						//weightA +=
					}
				}*/
			}

			swapped.setAsSwapped(collision);

			if(collision.colliderA.getBody().notifyCollision(collision) && collision.colliderB.getBody().notifyCollision(swapped))
				resolver.resolve(collision, weightA, weightB);
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

	public void add(Body body)
	{
		if(body instanceof DynamicBody)
		{
			dynamics.addFirst((DynamicBody)body);
			all.addFirst(body);
		}
		else
		{
			all.addLast(body);
		}
		refresh();
	}

	public void remove(Body body)
	{
		all.removeValue(body, true);
		if(body instanceof DynamicBody)
			dynamics.removeValue((DynamicBody)body, true);
		refresh();
	}

	@Override
	public Iterator<Body> iterator()
	{
		return all.iterator();
	}
}
