package me.winter.boing.physics.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IdentityMap;
import me.winter.boing.physics.Body;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicBody;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
public class DynamicBodyImpl extends BodyImpl implements DynamicBody
{
	private Vector2 velocity, movement, lastReplacement;
	private float weight;

	private IdentityMap<Vector2, Body> collisions = new IdentityMap<>();

	public DynamicBodyImpl()
	{
		this(1f);
	}

	public DynamicBodyImpl(float weight)
	{
		this.velocity = new Vector2();
		this.movement = new Vector2();
		this.lastReplacement = new Vector2();
		this.weight = weight;
	}

	@Override
	public void notifyCollision(Collision collision)
	{
		collisions.put(collision.normalA, collision.colliderB.getBody());
	}

	@Override
	public Vector2 getVelocity()
	{
		return velocity;
	}

	@Override
	public Vector2 getMovement()
	{
		return movement;
	}

	@Override
	public float getWeight(Collision collision)
	{
		return weight;
	}

	@Override
	public Vector2 getCollisionShifing()
	{
		return lastReplacement;
	}
}
