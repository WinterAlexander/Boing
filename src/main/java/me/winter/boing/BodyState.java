package me.winter.boing;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Represents a step for a dynamic body, typically has a movement and collision shifing but
 * different World implementation may Steps with more fields.
 * <p>
 * Created by Alexander Winter on 2017-06-17.
 */
public interface BodyState
{
	/**
	 * The movement of a DynamicBody is how much it moved in this frame.
	 * <p>
	 * It is calculated by multiplying the current velocity with the delta of the frame.
	 *
	 * @return unit-scaled movement of this frame/step
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
	 * @return vector of movement moving this body in function of the movement of other objects
	 */
	Vector2 getInfluence();

	/**
	 *
	 * @return list of bodies this body is touching in this step
	 */
	Array<Collision> getCollisions();
}
