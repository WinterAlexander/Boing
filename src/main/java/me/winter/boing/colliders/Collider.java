package me.winter.boing.colliders;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.Body;
import me.winter.boing.World;

import static java.lang.Math.abs;
import static java.lang.Math.ulp;
import static me.winter.boing.util.FloatUtil.max;

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
	 * @return relative x position of this collider to its body
	 */
	float getRelX();

	/**
	 * @return relative y position of this collider to its body
	 */
	float getRelY();

	/**
	 *
	 * @return width of the collider
	 */
	float getWidth();

	/**
	 *
	 * @return height of the collider
	 */
	float getHeight();

	/**
	 *
	 * @return the current movement of the body of this collider
	 */
	Vector2 getMovement(World world);

	/**
	 *
	 * @return the last movement imposed by collision resolving
	 */
	Vector2 getCollisionShifting(World world);

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
