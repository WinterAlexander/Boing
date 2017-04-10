package me.winter.boing.physics.v2.response;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.v2.DynamicSolid;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class VelocityResponse extends CollisionResponse
{
	private final Vector2 vel;

	public VelocityResponse()
	{
		super(CollisionResponseType.VELOCITY);
		vel = new Vector2();
	}

	@Override
	public void apply(DynamicSolid solid)
	{
		if(!solid.freshVel())
		{
			solid.getVelocity().set(vel);
			solid.setVelFresh(true);
		}
		else
		{
			solid.getVelocity().scl(0.5f).add(vel.x * 0.5f, vel.y * 0.5f);
		}
	}

	public Vector2 getVel()
	{
		return vel;
	}
}
