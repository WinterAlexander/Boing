package me.winter.boing.test.testimpl;

import me.winter.boing.Collision;
import me.winter.boing.UpdatableBody;
import me.winter.boing.impl.DynamicBodyImpl;

import static java.lang.Math.abs;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-08.
 */
public class GravityAffected extends DynamicBodyImpl implements UpdatableBody
{
	private boolean onGround;

	@Override
	public void update(float delta)
	{
		if(onGround)
		{
			if(getVelocity().y < 0)
				getVelocity().y = 0;
		}

		getVelocity().y -= 5;

		onGround = false;
	}

	@Override
	public void notifyCollision(Collision collision)
	{
		if(abs(collision.normalB.angle() - 90f) < 0.001f)
			onGround = true;

		super.notifyCollision(collision);
	}
}
