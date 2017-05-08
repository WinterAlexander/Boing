package me.winter.boing.physics.util;

import com.badlogic.gdx.math.Vector2;

import static java.lang.Float.POSITIVE_INFINITY;
import static java.lang.Math.signum;

/**
 * Offers useful methods to work with velocities and anything related
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class VelocityUtil
{
	private VelocityUtil() {}

	public static void reflect(Vector2 velocity, Vector2 normal)
	{
		if(normal.y == 0)//means vertical tangent
		{
			velocity.scl(-1, 1);
			return;
		}

		float a = -normal.x / normal.y;

		if(!Float.isFinite(a))
		{
			velocity.scl(-1, 1);
			return;
		}

		float d2 = 2 * (velocity.x + velocity.y * a) / (1 + a * a);

		velocity.scl(-1).add(d2, d2 * a);
	}

	/**
	 * Returns the ratio from 0 to 1 of the weight A over the total
	 *
	 * @param weightA weightA
	 * @param weightB weightB
	 * @return the ratio of the weightA over the 2 weights
	 */
	public static float getWeightRatio(float weightA, float weightB)
	{
		if(weightA == POSITIVE_INFINITY)
			return weightB == POSITIVE_INFINITY ? 0.5f : 1f;

		if(weightB == POSITIVE_INFINITY)
			return 0f;

		if(weightA == 0f)
			return weightB == 0f ? 0.5f : 0f;

		if(weightB == 0f)
			return 1f;

		return weightA / (weightA + weightB);
	}
}
