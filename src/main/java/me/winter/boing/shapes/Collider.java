package me.winter.boing.shapes;

import me.winter.boing.Body;

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

	/**
	 * A tag can be used to keep track of a Collider within it's use
	 *
	 * @return tag of this collider
	 */
	Object getTag();

	/**
	 * A tag can be used to keep track of a Collider within it's use
	 *
	 * @param tag new tag of this collider
	 */
	void setTag(Object tag);
}
