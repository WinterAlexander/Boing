package me.winter.boing.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * Represents a solid that moves every frame.
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface DynamicSolid extends Solid
{
	/**
	 * The velocity of a DynamicSolid represents how fast it's currently going.
	 *
	 * @return current velocity of the solid
	 */
	Vector2 getVelocity();

	/**
	 * The movement of a DynamicSolid is how much it moved in this frame.
	 * <p>
	 * It is calculated by multiplying the current velocity with the delta of the frame.
	 *
	 * @return unit-scaled movement of this frame
	 */
	Vector2 getMovement();

	/**
	 * The collision shifting is a way to represents the movement imposed by the
	 * collision resolver when dynamic solids collides. If two solids overlap, they
	 * need to be shifted in order to stop overlapping.
	 * <p>
	 * This shifting has to be taken and added to the movement (but not to the position)
	 * to prevent being shifted into another solid.
	 *
	 * @return shifting imposed by collision resolver on previous frame
	 */
	Vector2 getCollisionShifing();

	/**
	 * The weight of a DynamicSolid is used by a CollisionResolver to choose how
	 * to handle their shifing. In favor of game design over realism, you can choose
	 * your mass in function of the other object.
	 * <p>
	 * The weight can be 0 but has to be positive.
	 *
	 * @param other solid colliding with this one
	 * @return the weight this solid should have against another solid
	 */
	default float getWeight(DynamicSolid other)
	{
		return 1f;
	}
}
