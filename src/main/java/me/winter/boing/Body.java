package me.winter.boing;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.colliders.Collider;

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
	 * Method called by the World to ask if a collision should be occuring with this body.
	 * <p>
	 * You can return false to cancel the collision and let the bodies overlap or
	 * true if you want the collision to be resolved.
	 *
	 * @param collision detected collision about to be resolved
	 * @return true if the collision should be resolved, otherwise false
	 */
	default boolean cancelCollision(Collision collision) { return false; }

	/**
	 * Method called by the World to indicate a collision is occuring with this body.
	 * colliderA of the collision is always a collider of this body.
	 * <p>
	 * Note that with non dynamic bodies, this method won't be called every tick.
	 * The World implementation must call a contact at least once
	 *
	 * @param collision detected collision about to be resolved
	 */
	default void notifyCollision(Collision collision) {}

}
