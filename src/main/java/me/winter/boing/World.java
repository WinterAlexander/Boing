package me.winter.boing;

import com.badlogic.gdx.utils.Pool;

/**
 * Represents a World for a physics simulation. Note that the usage of World
 * and World derived classes isn't mandatory for Body objects but might
 * become in the future. To prevent issues, you should let your world class
 * implement this interface.
 *
 * Since Boing is a library designed for usage with LibGDX, a collision pool
 * has to be implemented and accessible through the getCollisionPool method.
 *
 * If you want not to use the collision pool, you will have to make sure all the
 * implementations (Body, DynamicBody, MoveState, World) does not require it.
 * <p>
 * Created by Alexander Winter on 2017-04-25.
 */
public interface World
{
	/**
	 * Simulates the physics of the world for a frame
	 * @param delta time since the last update
	 */
	void step(float delta);

	/**
	 * Fetches the MoveState for the passed body. MoveStep is used to store data about the
	 * body that only make sense in a physics world context.
	 *
	 * @param body body to get state of
	 * @return state of the passed body
	 */
	MoveState getState(DynamicBody body);

	/**
	 * Iterates through all the bodies of the world.
	 *
	 * This operation can be slow, a should implementation of World should
	 * provide an iterator of its active bodies.
	 *
	 * @return all the bodies
	 */
	Iterable<Body> getBodies();


	/**
	 * Let's you access the pool of collisions. You can obtain and free objects
	 * with this pool without using regular Garbage Collector.
	 *
	 * @return the pool of objects for collisions
	 */
	Pool<Collision> getCollisionPool();

	/**
	 * Updates the colliders for a body, letting know the world that the colliders
	 * for a body has changed. Effect may vary depending on the implementation of
	 * the World.
	 *
	 * @param body body with new colliders
	 */
	default void updateColliders(Body body) {};
}

