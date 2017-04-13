package me.winter.boing.test.physics;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.World;

import static java.lang.Math.abs;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-13.
 */
public class PlayerImpl extends SolidImpl implements DynamicSolid
{
	private boolean onGround;
	private Vector2 velocity = new Vector2(), movement = new Vector2();

	public PlayerImpl(World world)
	{
		super(world);
	}

	@Override
	public void update()
	{
		velocity.x *= 0.9f;
		velocity.y -= 5;

		if(onGround)
		{
			velocity.add(10, 0);
			velocity.y = 0;
		}
		onGround = false;
	}

	@Override
	public boolean collide(Collision collision)
	{
		if(abs(collision.normalB.angle() - 90f) < 0.001f)
			onGround = true;
		return true;
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
	public float getMass()
	{
		return 1f;
	}
}
