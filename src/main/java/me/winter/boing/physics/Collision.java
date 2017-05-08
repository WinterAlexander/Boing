package me.winter.boing.physics;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.shapes.Collider;

/**
 * Represents a collision between 2 solids. This object should be pooled
 * but is not reset by pooling for performance reasons.
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class Collision
{
	/**
	 * Normal of colliderA at the impact point
	 */
	public final Vector2 normalA = new Vector2();

	/**
	 * Normal of colliderB at the impact point
	 */
	public final Vector2 normalB = new Vector2();

	/**
	 * Velocity of solidA during impact
	 */
	public final Vector2 impactVelA = new Vector2();

	/**
	 * Velocity of solidB during impact
	 */
	public final Vector2 impactVelB = new Vector2();

	/**
	 * penetration of the collision, combined distance of the colliders within each other
	 */
	public float penetration;

	/**
	 * the amount of surface where solids are touching
	 */
	public float contactSurface;

	/**
	 * Weight of solid A for solid B
	 */
	public float weightA;

	/**
	 * Weight of solid B for solid A
	 */
	public float weightB;

	/**
	 * Collider involved in the collision.
	 * <p>
	 * In the notify method of a solid, this collider is a collider of your solid.
	 */
	public Collider colliderA;

	/**
	 * Collider involved in the collision.
	 */
	public Collider colliderB;

	/**
	 * Sets this collision as a swapped copy of another collision.
	 *
	 * @param collision collision to copy
	 */
	public void setAsSwapped(Collision collision)
	{
		normalA.set(collision.normalB);
		normalB.set(collision.normalA);
		impactVelA.set(collision.impactVelB);
		impactVelB.set(collision.impactVelA);
		penetration = collision.penetration;
		contactSurface = collision.contactSurface;
		colliderA = collision.colliderB;
		colliderB = collision.colliderA;
		weightA = collision.weightB;
		weightB = collision.weightA;
	}

	/**
	 * Sets the impact velocities of this collision from the solids.
	 * <p>
	 * If a solid isn't a DynamicSolid, its velocity will be set to 0
	 *
	 * @param bodyA solid of impactVelA
	 * @param bodyB solid of impactVelB
	 */
	public void setImpactVelocities(Body bodyA, Body bodyB)
	{
		if(bodyA instanceof DynamicBody)
			impactVelA.set(((DynamicBody)bodyA).getVelocity());
		else
			impactVelA.set(0, 0);

		if(bodyB instanceof DynamicBody)
			impactVelB.set(((DynamicBody)bodyB).getVelocity());
		else
			impactVelB.set(0, 0);
	}
}
