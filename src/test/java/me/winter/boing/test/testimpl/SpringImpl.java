package me.winter.boing.test.testimpl;

import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;
import me.winter.boing.impl.BodyImpl;
import me.winter.boing.util.VelocityUtil;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-13.
 */
public class SpringImpl extends BodyImpl
{
	@Override
	public void notifyCollision(Collision collision)
	{
		if(collision.colliderB.getBody() instanceof DynamicBody)
		{
			DynamicBody ds = (DynamicBody)collision.colliderB.getBody();
			ds.getVelocity().set(collision.impactVelB);
			VelocityUtil.reflect(ds.getVelocity(), collision.normalA);

			if(ds.getVelocity().len2() < 500f * 500f)
				ds.getVelocity().nor().scl(500f);
		}
	}
}
