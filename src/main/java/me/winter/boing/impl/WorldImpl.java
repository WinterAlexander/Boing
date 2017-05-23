package me.winter.boing.impl;

import com.badlogic.gdx.utils.Queue;
import me.winter.boing.Body;
import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;
import me.winter.boing.OptimizedWorld;
import me.winter.boing.UpdatableBody;
import me.winter.boing.resolver.CollisionResolver;

import java.util.Iterator;

import static java.lang.Math.abs;
import static me.winter.boing.util.FloatUtil.DEFAULT_ULPS;

/**
 * Simple implementation of a World detecting and resolving collisions.
 * <p>
 * Created by Alexander Winter on 2017-04-25.
 */
public class WorldImpl extends OptimizedWorld implements Iterable<Body>
{
	private Queue<DynamicBody> dynamics = new Queue<>();
	private Queue<Body> all = new Queue<>();

	//private int n = 0;

	public WorldImpl(CollisionResolver resolver)
	{
		super(resolver);
	}

	@Override
	protected void update(float delta)
	{
		for(DynamicBody dynamic : dynamics)
		{
			if(dynamic instanceof UpdatableBody)
				((UpdatableBody)dynamic).update(delta);

			/*for(int i = 0; i < prevCollisions.size; i++)
			{
				Collision collision = prevCollisions.get(i);

				Body other;
				Vector2 impactVel, normal;

				if(collision.colliderA.getBody() == dynamic)
				{
					impactVel = collision.impactVelA;
					normal = collision.normal;
					other = collision.colliderB.getBody();
				}
				else
				{
					impactVel = collision.impactVelB;
					normal = collision.normalB;
					other = collision.colliderA.getBody();
				}

				if(!(other instanceof DynamicBody))
					continue;

				if(impactVel.dot(normal) < 0)
					continue;

				dynamic.getMovement().add(((DynamicBody)other).getMovement());
			}*/

			dynamic.getMovement().set(dynamic.getVelocity()).scl(delta);

			if(abs(dynamic.getMovement().x) < DEFAULT_ULPS * 2 * dynamic.getPrecision()) //twice as much as the collision detection
			{
				dynamic.getMovement().x = 0;
				dynamic.getVelocity().x = 0;
			}

			if(abs(dynamic.getMovement().y) < DEFAULT_ULPS * 2 * dynamic.getPrecision()) //twice as much as the collision detection
			{
				dynamic.getMovement().y = 0;
				dynamic.getVelocity().y = 0;
			}
		}
	}

	@Override
	protected void moveBodies()
	{
		for(DynamicBody dynamic : dynamics)
		{
			dynamic.getPosition().add(dynamic.getMovement());
			dynamic.getPosition().add(dynamic.getCollisionShifting());
		}
	}

	@Override
	protected void resolveCollisions()
	{
		for(DynamicBody dynamic : dynamics)
			dynamic.getCollisionShifting().setZero();

		super.resolveCollisions();
	}

	/**
	 * Detects collision with 1 + 2 + 3 + ... + (N - 1) iterations, N being the number of bodies.
	 */
	@Override
	protected void fullDetection()
	{
		int size = all.size;
		Collision swapped = collisionPool.obtain();

		for(int i = size; i-- > 1;)
		{
			Body bodyA = all.get(i);

			for(int j = i; j-- > 0;)
				detect(bodyA, all.get(j), swapped);
		}

		collisionPool.free(swapped);
	}

	/**
	 * Detects collision with D + (D + 1) + (D + 2) + ... + (N - 1) iterations, N being the amount of bodies and D being the amount of dynamic bodies
	 */
	@Override
	protected void dynamicDetection()
	{
		int dyns = dynamics.size;
		int size = all.size;
		Collision swapped = collisionPool.obtain();

		for(int i = 0; i < dyns; i++)
		{
			DynamicBody bodyA = dynamics.get(i);

			for(int j = i + 1; j < size; j++)
				detect(bodyA, all.get(j), swapped);
		}

		collisionPool.free(swapped);
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
