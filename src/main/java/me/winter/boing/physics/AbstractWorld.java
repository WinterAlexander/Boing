package me.winter.boing.physics;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.util.CollisionPool;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-27.
 */
public abstract class AbstractWorld implements World
{
	protected final Array<Collision> collisions = new Array<>();
	protected final Pool<Collision> collisionPool = new CollisionPool();

	@Override
	public void step(float delta)
	{
		update(delta);

		detectCollisions();

		resolveCollisions();
		collisionPool.freeAll(collisions);
		collisions.clear();
	}

	protected abstract void update(float delta);
	protected abstract void detectCollisions();
	protected abstract void resolveCollisions();
}
