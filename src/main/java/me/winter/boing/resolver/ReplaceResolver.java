package me.winter.boing.resolver;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;

import static java.lang.Math.abs;
import static java.lang.Math.nextAfter;
import static java.lang.Math.signum;
import static me.winter.boing.util.VelocityUtil.incrementMantissa;

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
		float deltaA = collision.weightRatio;
		float deltaB = 1f - deltaA;

		float peneA = deltaB * collision.penetration;
		float peneB = deltaA * collision.penetration;

		if(deltaB > 0)
			replace((DynamicBody)collision.colliderA.getBody(), collision.normalB, peneA);

		if(deltaA > 0)
			replace((DynamicBody)collision.colliderB.getBody(), collision.normalA, peneB);


		//if((double)peneA + (double)peneB != (double)collision.penetration)
		//	System.out.println(peneA + " + " + peneB + " = " + (peneA + peneB) + " (instead of " + collision.penetration + ")");
	}

	private void replace(DynamicBody solid, Vector2 normal, float delta)
	{
		//assert normal.len() == 1f;

		float replaceX = normal.x * delta;
		float replaceY = normal.y * delta;

		//replaceX = incrementMantissa(replaceX);
		//replaceY = incrementMantissa(replaceY);
		//replaceX += normal.x;
		//replaceY += normal.y;

		//replaceX = nextAfter(replaceX, replaceX + normal.x);
		//replaceY = nextAfter(replaceY, replaceY + normal.y);

		//if(replaceX != 0 && abs(replaceX) < FLOAT_ROUNDING_ERROR)
		//	replaceX = signum(replaceX) * FLOAT_ROUNDING_ERROR;

		//if(replaceY != 0 && abs(replaceY) < FLOAT_ROUNDING_ERROR)
		//	replaceY = signum(replaceY) * FLOAT_ROUNDING_ERROR;

		if(abs(replaceX) > abs(solid.getCollisionShifing().x))
		{
			solid.getPosition().x += replaceX - solid.getCollisionShifing().x;
			solid.getCollisionShifing().x = replaceX;
		}

		if(abs(replaceY) > abs(solid.getCollisionShifing().y))
		{
			solid.getPosition().y += replaceY - solid.getCollisionShifing().y;
			solid.getCollisionShifing().y = replaceY;
		}
	}
}