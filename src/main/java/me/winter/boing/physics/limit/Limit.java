package me.winter.boing.physics.limit;


import me.winter.boing.physics.IntVector;

/**
 * Represents a physical limit that objects cannot cross
 * Example: a box edge
 * <p>
 * @see AxisLimit
 */
public interface Limit
{
	/**
	 * Checks if the parameter limit collides with this limit
	 * Both limit are checked from the movement of their LimitHolder
	 *
	 * @param limit limit to check
	 * @return true if there's collision
	 */
	boolean collides(Limit limit);

	/**
	 * Check if the parameter limit is in contact with this limit
	 *
	 * @param limit limit to check
	 * @return true if there's contact, otherwise false
	 */
	boolean touches(Limit limit);

	/**
	 * The priority of a limit to replace another is defined by the distance between them
	 * e.g. A limit behind another should never replace before, replacement is done in order using this priority
	 *
	 * @param limit limit to replace
	 * @param movementVector movement of the limit to replace
	 * @return the priority of this limit replacing another with it's movement
	 */
	float getPriority(Limit limit, IntVector movementVector);

	/**
	 * Replaces the limit by editing it's movement to one that doesn't collide with this limit
	 *
	 * @param limit limit to replace
	 * @param movementVector movement of the limit
	 * @return true if replaced, otherwise false
	 */
	boolean replace(Limit limit, IntVector movementVector);

	LimitHolder getHolder();
	void setHolder(LimitHolder holder);

	/**
	 * The angle of the limit is used to identify it's orientation while having no meaning in it's internal use
	 * @return angle the limit is pointing at, 0 means right, 90 means top etc.
	 */
	float getAngle();

	/**
	 * @return absolute position representing the start of the limit
	 */
	IntVector start();

	/**
	 * @return absolute position representing the end of the limit
	 */
	IntVector end();

	/**
	 *
	 * @param limit limit to compare
	 * @return 1 to 0 to represents how much that speficied limit has his surface in contact with this limit
	 */
	float surfaceContact(Limit limit);

	/**
	 * Clones this limit
	 *
	 * @return a copy of this limit.
	 */
	Limit clone();
}
