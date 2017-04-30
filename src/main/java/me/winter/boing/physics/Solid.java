package me.winter.boing.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.physics.shapes.Collider;

/**
 * Represents a simple, static solid. Cannot move but can prevent a DynamicSolid from moving.
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface Solid
{
	/**
	 * @return the absolute position of this solid
	 */
	Vector2 getPosition();

	/**
	 * @return the list of colliders this solid have
	 */
	Array<Collider> getColliders();

	/**
	 * Method called by the World to indicate a collision is occuring with this solid.
	 * colliderA of the collision is always a collider of this solid.
	 * <p>
	 * You can return false to cancel the collision and let the solids overlap or
	 * true if you want the collision to be resolved.
	 *
	 * @param collision detected collision about to be resolved
	 * @return true if the collision should be resolved, otherwise false
	 */
	default boolean notifyCollision(Collision collision)
	{
		return true;
	}
}
