package me.winter.boing;

/**
 * Represents a World for a physics simulation. Note that the usage of World
 * and World derived classes isn't mandatory for Body objects but might
 * become in the future. To prevent issues, you should let your world class
 * implement this interface.
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
}

