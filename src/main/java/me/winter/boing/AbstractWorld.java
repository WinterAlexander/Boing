package me.winter.boing;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.detection.CollisionDetector;
import me.winter.boing.resolver.CollisionResolver;
import me.winter.boing.util.CollisionPool;

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
	 * Collision pool to prevent creating new collisions objects.
	 */
	protected final Pool<Collision> collisionPool = new CollisionPool();

	@Override
	public void step(float delta)
	{
		update(delta);

		detectCollisions();

		resolveCollisions();

		moveBodies();
	}


	/**
	 * Updates the game objects by looping through them and make the DynamicBody objects move
	 *
	 * @param delta time since last update
	 */
	protected abstract void update(float delta);

	/**
	 * Detects all the collision of this world and put them in the collision array for resolution
	 */
	protected abstract void detectCollisions();

	/**
	 * Resolve all collisions in the collision array and update the LastShifting to all DynamicBody
	 */
	protected abstract void resolveCollisions();


	protected abstract void moveBodies();
}
