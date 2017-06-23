package me.winter.boing;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.detection.anticipation.PreAABB;

/**
 * Represents a step for a dynamic body, typically has a movement and collision shifing but
 * different World implementation may Steps with more fields.
 * <p>
 * Created by Alexander Winter on 2017-06-17.
 */
public interface MoveState
{
	/**
	 * World in which this MoveState is valid.
	 *
	 * @return world reference of this MoveState
	 */
	World getWorld();

	/**
	 * Each MoveState keeps data about body in context of a world.
	 * This returns the body this MoveState keeps track of.
	 *
	 * @return the body this MoveState represent the movement of
	 */
	DynamicBody getBody();

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

	/**
	 * Shifts this body by the specified vector, as response of a collision
	 *
	 * @param x x component of the vector to shift this body with
	 * @param y y component of the vector to shift this body with
	 */
	void shift(float x, float y);

	/**
	 * Makes a step in the simulation, calculating the movement from the velocity and ready to
	 * resolve collisions.
	 *
	 * Also has the job to clear the collisions from the previous step
	 *
	 * @param frame get the number of frames (frame id or step id)
	 * @param delta get the delta to step it with
	 */
	void step(int frame, float delta);
}
