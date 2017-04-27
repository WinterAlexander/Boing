package me.winter.boing.test.physics.testimpl;

import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.World;
import me.winter.boing.physics.util.VelocityUtil;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-13.
 */
public class SpringImpl extends SolidImpl
{
	@Override
	public boolean collide(Collision collision)
	{
		if(collision.colliderB.getSolid() instanceof DynamicSolid)
		{
			DynamicSolid ds = (DynamicSolid)collision.colliderB.getSolid();
			ds.getVelocity().set(collision.impactVelB);
			VelocityUtil.reflect(ds.getVelocity(), collision.normalA);

			if(ds.getVelocity().len2() < 500f * 500f)
				ds.getVelocity().nor().scl(500f);
		}
		return true;
	}
}
