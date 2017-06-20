package me.winter.boing.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.DynamicBody;
import me.winter.boing.MoveState;
import me.winter.boing.Collision;
import me.winter.boing.World;

import static java.lang.Math.signum;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-06-17.
 */
public class MoveStateImpl implements MoveState
{
	private final World world;
	private final DynamicBody body;

	private final Vector2 movement = new Vector2(), shifting = new Vector2(), influence = new Vector2(Float.NaN, Float.NaN);
	private final Array<Collision> collisions = new Array<>();

	public MoveStateImpl(World world, DynamicBody body)
	{
		this.world = world;
		this.body = body;
	}

	@Override
	public void shift(float x, float y)
	{
		body.getPosition().sub(shifting);

		if(x != 0f)
		{
			float dirX = signum(shifting.x);

			if(dirX != signum(x))
				shifting.x += x;
			else if(dirX == 0 || x * dirX > shifting.x * dirX)
				shifting.x = x;
		}

		if(y != 0f)
		{
			float dirY = signum(shifting.y);

			if(dirY != signum(y))
				shifting.y += y;
			else if(dirY == 0 || y * dirY > shifting.y * dirY)
				shifting.y = y;
		}

		body.getPosition().add(shifting);
	}

	@Override
	public World getWorld()
	{
		return world;
	}

	@Override
	public DynamicBody getBody()
	{
		return body;
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
