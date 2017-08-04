package me.winter.boing;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.CollisionDynamicVariable.Inverter;
import me.winter.boing.colliders.Collider;

/**
 * Represents a collision between 2 solids. This object should be pooled
 * but is not reset by pooling for performance reasons.
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class Collision
{
	/**
	 * Normal of the collision at the impact point from A's perpective (B is opposite)
	 */
	public final Vector2 normal = new Vector2();

	/**
	 * Velocity of solidA during impact
	 */
	public final Vector2 impactVelA = new Vector2();

	/**
	 * Velocity of solidB during impact
	 */
	public final Vector2 impactVelB = new Vector2();

	/**
	 * formula for penetration of the collision, combined distance of the colliders within each other
	 */
	public CollisionDynamicVariable penetration;

	/**
	 * @return current penetration of the collision
	 */
	public float getPenetration()
	{
		return penetration.getValue(colliderA, colliderB);
	}

	/**
	 * the amount of surface where solids are touching
	 */
	public CollisionDynamicVariable contactSurface;

	/**
	 * @return current contact surface of the collision
	 */
	public float getContactSurface()
	{
		return contactSurface.getValue(colliderA, colliderB);
	}

	/**
	 * Weight of body A over body B
	 */
	public float weightRatio;

	/**
	 * Priority of the collision, collisions should be sorted using this field before resolving
	 *
	 * The smaller this value is, the first it should be resolved
	 */
	public float priority;

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

	public void set(Collision collision)
	{
		normal.set(collision.normal);
		impactVelA.set(collision.impactVelA);
		impactVelB.set(collision.impactVelB);
		penetration = collision.penetration;
		weightRatio = collision.weightRatio;
		contactSurface = collision.contactSurface;
		priority = collision.priority;
		colliderA = collision.colliderA;
		colliderB = collision.colliderB;
	}

	/**
	 * Sets this collision as a swapped copy of another collision.
	 *
	 * @param collision collision to copy
	 */
	public void setAsSwapped(Collision collision)
	{
		normal.set(collision.normal).scl(-1);
		impactVelA.set(collision.impactVelB);
		impactVelB.set(collision.impactVelA);
		weightRatio = 1f - collision.weightRatio;
		contactSurface = CollisionDynamicVariable.inverter.wrap(collision.contactSurface);
		penetration = CollisionDynamicVariable.inverter.wrap(collision.penetration);
		priority = collision.priority;
		colliderA = collision.colliderB;
		colliderB = collision.colliderA;
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
