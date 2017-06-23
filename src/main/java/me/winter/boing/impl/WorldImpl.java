package me.winter.boing.impl;

import com.badlogic.gdx.utils.IdentityMap;
import com.badlogic.gdx.utils.Queue;
import me.winter.boing.AbstractWorld;
import me.winter.boing.Body;
import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;
import me.winter.boing.MoveState;
import me.winter.boing.resolver.CollisionResolver;

/**
 * Simple implementation of a World using an Queue.
 * <p>
 * Created by Alexander Winter on 2017-04-25.
 */
public class WorldImpl extends AbstractWorld
{
	private Queue<DynamicBody> dynamics = new Queue<>();
	private Queue<Body> all = new Queue<>();

	private IdentityMap<DynamicBody, MoveStateImpl> steps = new IdentityMap<>();

	public WorldImpl(CollisionResolver resolver)
	{
		super(resolver);
	}

	private int frame;

	@Override
	protected void update(float delta)
	{
		frame++;
		for(DynamicBody dynamic : dynamics)
			getState(dynamic).step(frame, delta);
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
			steps.put((DynamicBody)body, new MoveStateImpl(this, (DynamicBody)body));
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
		{
			dynamics.removeValue((DynamicBody)body, true);
			steps.remove((DynamicBody)body);
		}
		refresh();
	}

	@Override
	public MoveState getState(DynamicBody body)
	{
		return steps.get(body);
	}

	@Override
	public Iterable<Body> getBodies()
	{
		return all;
	}
}
