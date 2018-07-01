package me.winter.boing;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents a body that moves every frame.
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface DynamicBody extends Body
{

	/**
	 * The velocity of a DynamicBody represents how fast it's currently going.
	 *
	 * @return current velocity of the body
	 */
	Vector2 getVelocity();

	/**
	 * The weight of a DynamicBody is used by a CollisionResolver to choose how
	 * to handle their shifing.
	 * <p>
	 * The weight can be 0 but has to be positive. You can set Float.POSITIVE_INFINITY to have an infinite weight
	 *
	 * @return the weight this body should have against another body
	 */
	default float getWeight()
	{
		return 1f;
	}
}
