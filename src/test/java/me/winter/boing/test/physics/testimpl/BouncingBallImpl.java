package me.winter.boing.test.physics.testimpl;

import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.util.VelocityUtil;

import static me.winter.boing.physics.util.VelocityUtil.getMassRatio;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class BouncingBallImpl extends DynamicSolidImpl
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
	public boolean notifyCollision(Collision collision)
	{
		VelocityUtil.reflect(getVelocity(), collision.normalB);

		if(collision.colliderB.getSolid() instanceof DynamicSolid)
		{
			DynamicSolid dynamicSolid = ((DynamicSolid)collision.colliderB.getSolid());

			float massRatio = getMassRatio(getWeight(dynamicSolid), dynamicSolid.getWeight(this));

			getVelocity().add(collision.impactVelB);
			getVelocity().scl(1f - massRatio);
		}

		return true;
	}
}
