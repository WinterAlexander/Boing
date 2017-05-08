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
	 * <p>
	 * This shifting has to be taken and added to the movement (but not to the position)
	 * to prevent being shifted into another body.
	 *
	 * @return shifting imposed by collision resolver on previous frame
	 */
	Vector2 getCollisionShifing();

	/**
	 * The weight of a DynamicBody is used by a CollisionResolver to choose how
	 * to handle their shifing. In favor of game design over realism, you can choose
	 * your mass in function of the other object.
	 * <p>
	 * The weight can be 0 but has to be positive. You can set Float.POSITIVE_INFINITY to have an infinite weight
	 *
	 * @param collision with the other body
	 * @return the weight this body should have against another body
	 */
	default float getWeight(Collision collision)
	{
		return 1f;
	}
}
