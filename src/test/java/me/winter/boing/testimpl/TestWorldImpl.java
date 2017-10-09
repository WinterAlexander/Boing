package me.winter.boing.testimpl;

import me.winter.boing.Body;
import me.winter.boing.PreAABB;
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
	public boolean splittedStep = false, collisionByCollision = false;

	private boolean step = false;

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

	@Override
	public void step(float delta)
	{
		if(!splittedStep)
		{
			update(delta);

			detectCollisions();

			collisions.sort((c1, c2) -> -Float.compare(c1.priority, c2.priority));

			resolveCollisions();
			return;
		}

		if(!step)
		{
			update(delta);
			step = true;
		}
		else
		{
			detectCollisions();

			collisions.sort((c1, c2) -> -Float.compare(c1.priority, c2.priority));

			resolveCollisions();
			step = false;
		}
	}

	public int collisionCount()
	{
		return collisionCount;
	}

	public PreAABB getSurroundingBox(Body body)
	{
		return surroundingBoxes.get(body);
	}
}
