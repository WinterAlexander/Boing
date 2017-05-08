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
}

