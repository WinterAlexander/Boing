package me.winter.boing.testimpl;

import me.winter.boing.impl.WorldImpl;
import me.winter.boing.resolver.CollisionResolver;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-08.
 */
public class TestWorldImpl extends WorldImpl
{
	private int collisionCount;

	public TestWorldImpl(CollisionResolver resolver)
	{
		super(resolver);
	}

	@Override
	protected void resolveCollisions()
	{
		collisionCount = collisions.size;
		super.resolveCollisions();
	}

	public int collisionCount()
	{
		return collisionCount;
	}
}
