package me.winter.boing.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.DynamicBody;
import me.winter.boing.MoveState;
import me.winter.boing.Collision;
import me.winter.boing.UpdatableBody;
import me.winter.boing.World;
import me.winter.boing.detection.anticipation.PreAABB;

import static java.lang.Math.signum;
import static me.winter.boing.util.VectorUtil.DOWN;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-06-17.
 */
public class MoveStateImpl implements MoveState
{
	protected final World world;
	protected final DynamicBody body;

	protected final Vector2 movement = new Vector2(), shifting = new Vector2(), influence = new Vector2(Float.NaN, Float.NaN);
	protected final Array<Collision> collisions = new Array<>();

	protected int frame;
	protected boolean stepped;

	public MoveStateImpl(World world, DynamicBody body)
	{
		this.world = world;
		this.body = body;
	}

	@Override
	public void step(int frame, float delta)
	{
		if(this.frame >= frame)
			return;
		this.frame = frame;
		stepped = true;

		if(body instanceof UpdatableBody)
			((UpdatableBody)body).update(delta);

		getMovement().set(body.getVelocity()).scl(delta);
		getMovement().add(getInfluence(body, delta));

		body.getPosition().add(getMovement());
	}

	private Vector2 tmpInfluence = new Vector2();

	private Vector2 getInfluence(DynamicBody dynamic, float delta)
	{
		Vector2 influence = world.getState(dynamic).getInfluence();

		if(!Float.isNaN(influence.len2()))
			return influence;

		influence.set(0, 0);

		for(Collision collision : world.getState(dynamic).getCollisions())
		{
			if(collision.colliderB.getBody() instanceof DynamicBody && collision.normal.dot(DOWN) == 1)
			{
				DynamicBody other = ((DynamicBody)collision.colliderB.getBody());

				world.getState(other).step(frame, delta);

				tmpInfluence.set(other.getVelocity()).scl(delta);
				tmpInfluence.add(getInfluence((DynamicBody)collision.colliderB.getBody(), delta));

				if(influence.len2() < tmpInfluence.len2())
					influence.set(tmpInfluence);
			}

			world.getCollisionPool().free(collision);
		}
		world.getState(dynamic).getCollisions().clear();

		return influence;
	}

	@Override
	public void shift(float x, float y)
	{
		if(stepped)
		{
			shifting.setZero();
			influence.set(Float.NaN, Float.NaN);
			stepped = false;
		}
		else
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
