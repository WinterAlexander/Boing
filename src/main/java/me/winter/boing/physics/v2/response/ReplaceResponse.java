package me.winter.boing.physics.v2.response;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.v2.DynamicSolid;

import static java.lang.Math.abs;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class ReplaceResponse extends CollisionResponse
{
	private final Vector2 delta, from;
	private final Vector2 tmpVec = new Vector2();
	private float friction;

	public ReplaceResponse()
	{
		super(CollisionResponseType.REPLACE);
		delta = new Vector2();
		from = new Vector2();
		friction = 1f;
	}

	@Override
	public void apply(DynamicSolid solid)
	{
		//float dynamicToX = solid.getPosition().x + (toX - fromX);
		//float dynamicToY = solid.getPosition().y + (toY - fromY);
		if(!solid.freshVel())
		{
			tmpVec.set(delta);
			tmpVec.nor();

			float slideX = (1f - friction) * (1f - abs(tmpVec.x));
			float slideY = (1f - friction) * (1f - abs(tmpVec.y));

			solid.getVelocity().scl(slideX, slideY);
			solid.setVelFresh(true);
		}
		solid.getPosition().set(from).add(delta);
	}

	public Vector2 getDelta()
	{
		return delta;
	}

	public Vector2 getFrom()
	{
		return from;
	}

	public float getFriction()
	{
		return friction;
	}

	public void setFriction(float friction)
	{
		this.friction = friction;
	}
}
