package me.winter.boing.test.physics.testimpl;

import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.World;
import me.winter.boing.physics.util.VelocityUtil;

import static me.winter.boing.physics.util.VelocityUtil.getMassRatio;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class BouncingBallImpl extends DynamicSolidImpl
{
	public BouncingBallImpl(World world)
	{
		super(world);
	}

	public BouncingBallImpl(World world, float mass)
	{
		super(world, mass);
	}

	@Override
	public boolean collide(Collision collision)
	{
		VelocityUtil.reflect(getVelocity(), collision.normalB);

		if(collision.colliderB.getSolid() instanceof DynamicSolid)
		{
			DynamicSolid dynamicSolid = ((DynamicSolid)collision.colliderB.getSolid());

			float massRatio = getMassRatio(weightFor(dynamicSolid), dynamicSolid.weightFor(this));

			getVelocity().add(collision.impactVelB);
			getVelocity().scl(1f - massRatio);
		}

		return true;
	}
}