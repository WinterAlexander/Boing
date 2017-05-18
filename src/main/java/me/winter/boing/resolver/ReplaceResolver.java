package me.winter.boing.resolver;

import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;

import static java.lang.Math.abs;

/**
 * CollisionResolver resolving collisions by replacing the objects colliding (if they are Dynamic)
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class ReplaceResolver implements CollisionResolver
{
	@Override
	public void resolve(Collision collision)
	{
		float pene = collision.penetration;
		float delta = collision.weightRatio * pene;

		/*String debug = null;

		if(plyCls().isInstance(collision.colliderA.getBody()))
		{
			System.out.println(collision.weightRatio);
			debug = "A";
		}

		if(plyCls().isInstance(collision.colliderB.getBody()))
		{
			System.out.println(1f - collision.weightRatio);
			debug = "B";
		}*/

		if(delta < pene)
		{
			DynamicBody solid = (DynamicBody)collision.colliderA.getBody();

			if(abs(collision.normalB.x * pene - collision.normalB.x * delta) > abs(solid.getLastReplacement().x))
			{
				solid.getPosition().x = solid.getPosition().x + collision.normalB.x * pene - collision.normalB.x * delta - solid.getLastReplacement().x;
				solid.getLastReplacement().x = collision.normalB.x * pene - collision.normalB.x * delta;
			}

			if(abs(collision.normalB.y * pene - collision.normalB.y * delta) > abs(solid.getLastReplacement().y))
			{
				solid.getPosition().y = solid.getPosition().y + collision.normalB.y * pene - collision.normalB.y * delta - solid.getLastReplacement().y;
				solid.getLastReplacement().y = collision.normalB.y * pene - collision.normalB.y * delta;
			}
		}
		/*else if(debug != null)
		{
			System.out.println("No movement for A, player is " + debug);
		}*/

		if(delta > 0)
		{
			DynamicBody solid = (DynamicBody)collision.colliderB.getBody();

			if(delta == pene)
				System.out.println("B getting pushed af");

			float replaceX = collision.normalA.x * delta;
			float replaceY = collision.normalA.y * delta;

			if(abs(replaceX) > abs(solid.getLastReplacement().x))
			{
				solid.getPosition().x = solid.getPosition().x + replaceX - solid.getLastReplacement().x;
				solid.getLastReplacement().x = replaceX;
			}

			if(abs(replaceY) > abs(solid.getLastReplacement().y))
			{
				solid.getPosition().y = solid.getPosition().y + replaceY - solid.getLastReplacement().y;
				solid.getLastReplacement().y = replaceY;
			}
		}
		/*else if(debug != null)
		{
			System.out.println("No movement for B, player is " + debug);
		}*/
	}

	private Class<?> plyCls()
	{
		try
		{
			return Class.forName("me.winter.boing.testimpl.PlayerImpl");
		}
		catch(Throwable t)
		{
			t.printStackTrace();
			return null;
		}
	}

}
