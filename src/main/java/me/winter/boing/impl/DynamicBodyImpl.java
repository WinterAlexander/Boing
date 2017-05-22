package me.winter.boing.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IdentityMap;
import me.winter.boing.Body;
import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
public class DynamicBodyImpl extends BodyImpl implements DynamicBody
{
	private Vector2 velocity, movement, shifting;
	private float weight;

	public DynamicBodyImpl()
	{
		this(1f);
	}

	public DynamicBodyImpl(float weight)
	{
		this.velocity = new Vector2();
		this.movement = new Vector2();
		this.shifting = new Vector2();
		this.weight = weight;
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
	public Vector2 getCollisionShifting()
	{
		return shifting;
	}

	@Override
	public float getWeight(DynamicBody against)
	{
		return weight;
	}
}
