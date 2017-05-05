package me.winter.boing.test.physics.testimpl;

import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicBody;

import static me.winter.boing.physics.util.VelocityUtil.getMassRatio;
import static me.winter.boing.physics.util.VelocityUtil.reflect;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class BouncingBallImpl extends DynamicBodyImpl
{
	public BouncingBallImpl()
	{
		super();
	}

	public BouncingBallImpl(float mass)
	{
		super(mass);
	}

	@Override
	public void notifyCollision(Collision collision)
	{
		reflect(getVelocity(), collision.normalB);

		if(collision.colliderB.getBody() instanceof DynamicBody)
		{
			DynamicBody dynamicSolid = ((DynamicBody)collision.colliderB.getBody());

			float massRatio = getMassRatio(getWeight(dynamicSolid), dynamicSolid.getWeight(this));

			getVelocity().add(collision.impactVelB);
			getVelocity().scl(1f - massRatio);
		}
	}
}
