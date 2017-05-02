package me.winter.boing.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.physics.shapes.Collider;

/**
 * Represents a simple, static body. Cannot move but can prevent a DynamicSolid from moving.
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface Body
{
	/**
	 * @return the absolute position of this body
	 */
	Vector2 getPosition();

	/**
	 * @return the list of colliders this body have
	 */
	Collider[] getColliders();

	/**
	 * Method called by the World to indicate a collision is occuring with this body.
	 * colliderA of the collision is always a collider of this body.
	 * <p>
	 * You can return false to cancel the collision and let the bodies overlap or
	 * true if you want the collision to be resolved.
	 *
	 * @param collision detected collision about to be resolved
	 * @return true if the collision should be resolved, otherwise false
	 */
	default boolean notifyCollision(Collision collision)
	{
		return true;
	}

	/**
	 * Method called by the World to indicate a contact is occuring with this body.
	 * colliderA of the collision is always a collider of this body. Not called when a
	 * collision occurs.
	 *
	 * @param contact detected contact, penetration will always be 0
	 */
	default void notifyContact(Collision contact) {}
}
