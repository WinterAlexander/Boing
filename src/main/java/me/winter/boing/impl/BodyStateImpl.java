package me.winter.boing.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.BodyState;
import me.winter.boing.Collision;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-06-17.
 */
public class BodyStateImpl implements BodyState
{
	private Vector2 movement = new Vector2(), cs = new Vector2(), influence = new Vector2(Float.NaN, Float.NaN);
	private Array<Collision> collisions = new Array<>();

	@Override
	public Vector2 getMovement()
	{
		return movement;
	}

	@Override
	public Vector2 getCollisionShifting()
	{
		return cs;
	}

	@Override
	public Vector2 getInfluence()
	{
		return influence;
	}

	@Override
	public Array<Collision> getCollisions()
	{
		return collisions;
	}
}
