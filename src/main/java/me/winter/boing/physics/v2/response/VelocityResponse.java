package me.winter.boing.physics.v2.response;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.v2.Solid;

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
	public void apply(Solid solid)
	{
		if(!solid.freshVel())
		{
			solid.getVelocity().set(vel);
			solid.setVelFresh(true);
		}
		else
		{
			solid.getVelocity().add(vel);
		}
	}

	public Vector2 getVel()
	{
		return vel;
	}
}
