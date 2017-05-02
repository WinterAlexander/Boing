package me.winter.boing.physics;

import com.badlogic.gdx.utils.Queue;
import me.winter.boing.physics.detection.DetectionHandler;
import me.winter.boing.physics.resolver.CollisionResolver;
import me.winter.boing.physics.shapes.Collider;

import java.util.Iterator;

/**
 * Simple implementation of a World detecting and resolving collisions.
 * <p>
 * Created by Alexander Winter on 2017-04-25.
 */
public class SimpleWorld extends AbstractWorld implements Iterable<Body>
{
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

			dynamic.getMovement().add(dynamic.getCollisionShifing());
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
							collisions.add(collision);
					}
				}
			}
		}
	}

	/**
	 * Detects collision with D + (D + 1) + (D + 2) + ... + (N - 1) iterations, N being the amount of bodies and D being the amount of dynamic bodies
	 */
	protected void dynamicDetection()
	{
		int dyns = dynamics.size - 1;
		int size = all.size;

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

			if(collision.penetration == 0)
			{
				collision.colliderA.getBody().notifyContact(collision);
				collision.colliderB.getBody().notifyContact(swapped);
			}
			else if(collision.colliderA.getBody().notifyCollision(collision) && collision.colliderB.getBody().notifyCollision(swapped))
				resolver.resolve(collision);
		}

		collisionPool.free(swapped);
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
