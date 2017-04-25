package me.winter.boing.physics.util;

import com.badlogic.gdx.math.Vector2;

/**
 * Undocumented :(
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

	public static float getMassRatio(float massA, float massB)
	{
		if(massA == Float.POSITIVE_INFINITY)
			return massB == Float.POSITIVE_INFINITY ? 0.5f : 1f;

		if(massB == Float.POSITIVE_INFINITY)
			return 0f;

		if(massA == 0f)
			return massB == 0f ? 0.5f : 0f;

		if(massB == 0f)
			return 1f;

		return massA / (massA + massB);
	}
}
