package me.winter.boing;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-06-17.
 */
public class WorldImplBodyStep implements BodyStep
{
	private Vector2 movement = new Vector2(), cs = new Vector2(), im = new Vector2();
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
	public Vector2 getInfluencedMovement()
	{
		return im;
	}

	@Override
	public Array<Collision> getCollisions()
	{
		return collisions;
	}
}
