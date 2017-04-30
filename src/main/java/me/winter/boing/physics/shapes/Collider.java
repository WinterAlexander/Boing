package me.winter.boing.physics.shapes;

import me.winter.boing.physics.Body;

/**
 * Represents a shape that can enter in collision with another
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface Collider
{
	/**
	 * @return parent solid of this collider
	 */
	Body getBody();

	/**
	 * @return absolute x position of this collider
	 */
	float getAbsX();

	/**
	 * @return absolute y position of this collider
	 */
	float getAbsY();
}
