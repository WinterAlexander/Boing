package me.winter.boing;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static java.lang.Math.abs;
import static java.lang.Math.ulp;
import static me.winter.boing.util.FloatUtil.max;

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
	 * The movement of a DynamicBody is how much it moved in this frame.
	 * <p>
	 * It is calculated by multiplying the current velocity with the delta of the frame.
	 *
	 * @return unit-scaled movement of this frame
	 */
	Vector2 getMovement();

	/**
	 * The collision shifting is a way to represents the movement imposed by the
	 * collision resolver when dynamic bodies collides. If two bodies overlap, they
	 * need to be shifted in order to stop overlapping.
	 *
	 * @return shifting imposed by collision resolver on previous frame
	 */
	Vector2 getCollisionShifting();

	/**
	 *
	 * @return
	 */
	Vector2 getInfluencedMovement();

	/**
	 *
	 * @return list of bodies this body is touching
	 */
	Array<Collision> getCollisions();

	/**
	 *
	 * @return the precision needed to do float operations with this body
	 */
	@Override
	default float getPrecision()
	{
		return ulp(max(abs(getPosition().x), abs(getPosition().y), getWidth(), getHeight(), abs(getMovement().x), abs(getMovement().y)));
	}

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
