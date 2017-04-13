package me.winter.boing.test.physics;

import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.VelocityUtil;
import me.winter.boing.physics.World;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-13.
 */
public class SpringImpl extends SolidImpl
{
	public SpringImpl(World world)
	{
		super(world);
	}

	@Override
	public boolean collide(Collision collision)
	{
		if(collision.colliderB.getSolid() instanceof DynamicSolid)
		{
			DynamicSolid ds = (DynamicSolid)collision.colliderB.getSolid();
			VelocityUtil.reflect(ds.getVelocity(), collision.normalB);

			if(ds.getVelocity().len2() < 20f * 20f)
				ds.getVelocity().nor().scl(20f);
		}
		return true;
	}
}
