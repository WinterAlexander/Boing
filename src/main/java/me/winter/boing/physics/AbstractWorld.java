package me.winter.boing.physics;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.util.CollisionPool;

/**
 * Basic organizational abstract implementation of World.
 * <p>
 * Provides a pool for collisions objects and an array to
 * keep them during detection.
 * <p>
 * Created by Alexander Winter on 2017-04-27.
 */
public abstract class AbstractWorld implements World
{
	/**
	 * Collisions occuring in the current frame
	 */
	protected final Array<Collision> collisions = new Array<>();

	/**
	 * Collision pool to prevent creating new collisions objects.
	 */
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

	/**
	 * Updates the game objects by looping through them and make the DynamicSolid objects move
	 *
	 * @param delta time since last update
	 */
	protected abstract void update(float delta);

	/**
	 * Detects all the collision of this world and put them in the collision array for resolution
	 */
	protected abstract void detectCollisions();

	/**
	 * Resolve all collisions in the collision array and update the LastShifting to all DynamicSolids
	 */
	protected abstract void resolveCollisions();
}
