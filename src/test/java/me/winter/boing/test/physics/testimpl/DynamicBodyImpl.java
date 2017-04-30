package me.winter.boing.test.physics.testimpl;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.DynamicBody;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
public class DynamicBodyImpl extends BodyImpl implements DynamicBody
{
	private Vector2 velocity, movement, lastReplacement;
	private float mass;

	public DynamicBodyImpl()
	{
		this(1f);
	}

	public DynamicBodyImpl(float mass)
	{
		this.velocity = new Vector2();
		this.movement = new Vector2();
		this.lastReplacement = new Vector2();
		this.mass = mass;
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
	public float getWeight(DynamicBody other)
	{
		return mass;
	}

	@Override
	public Vector2 getCollisionShifing()
	{
		return lastReplacement;
	}
}
