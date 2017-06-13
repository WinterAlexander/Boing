package me.winter.boing.impl;

import com.badlogic.gdx.math.Vector2;
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
import static me.winter.boing.util.VectorUtil.DOWN;

/**
 * Simple implementation of a World detecting and resolving collisions.
 * <p>
 * Created by Alexander Winter on 2017-04-25.
 */
public class WorldImpl extends OptimizedWorld implements Iterable<Body>
{
	private Queue<DynamicBody> dynamics = new Queue<>();
	private Queue<Body> all = new Queue<>();


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
		}

		for(DynamicBody dynamic : dynamics)
		{
			dynamic.getMovement().set(dynamic.getVelocity()).scl(delta);
			dynamic.getMovement().add(getInfluenceMovement(dynamic, delta));

			dynamic.getPosition().add(dynamic.getMovement());

		}
	}

	private Vector2 getInfluenceMovement(DynamicBody dynamic, float delta)
	{
		if(!Float.isNaN(dynamic.getInfluencedMovement().len2()))
			return dynamic.getInfluencedMovement();

		dynamic.getInfluencedMovement().set(0, 0);

		for(Collision collision : dynamic.getCollisions())
		{
			if(collision.colliderB.getBody() instanceof DynamicBody
					&& collision.normal.dot(DOWN) == 1)
			{
				Vector2 vel = ((DynamicBody)collision.colliderB.getBody()).getVelocity();

				dynamic.getInfluencedMovement().add(vel.x * delta, vel.y * delta);
				dynamic.getInfluencedMovement().add(getInfluenceMovement((DynamicBody)collision.colliderB.getBody(), delta));
			}

			collisionPool.free(collision);
		}
		dynamic.getCollisions().clear();

		return dynamic.getInfluencedMovement();
	}

	@Override
	protected void resolveCollisions()
	{
		for(DynamicBody dynamic : dynamics)
		{
			dynamic.getCollisionShifting().setZero();
			dynamic.getInfluencedMovement().set(Float.NaN, Float.NaN);
		}

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
