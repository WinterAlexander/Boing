package me.winter.boing;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.colliders.Collider;

/**
 * Used to avoid doing expensive collision detection by checking if 2 bodies have the possibility to collide
 * 
 * In order to do that, AABB are formed around all their colliders and its movement. Movement is assumed to be already
 * added to the collision, as when doing collision detection
 * <p>
 * Created by Alexander Winter on 23/06/17.
 */
public class PreAABB
{
	public final Body body;
	public final Vector2 movement; //reference to the actual movement.

	public float minX, minY, maxX, maxY;

	public PreAABB(Body body, Vector2 movement)
	{
		this.body = body;
		this.movement = movement;

		update();
	}

	/**
	 * Iterates through all the colliders to update this AABB from the body to fit its maximum size
	 *
	 */
	public void update()
	{
		minX = Float.POSITIVE_INFINITY;
		minY = Float.POSITIVE_INFINITY;
		maxX = Float.NEGATIVE_INFINITY;
		maxY = Float.NEGATIVE_INFINITY;

		for(Collider current : body.getColliders())
		{
			float cMinX = current.getRelX() - current.getWidth() / 2;
			float cMinY = current.getRelY() - current.getHeight() / 2;
			float cMaxX = current.getRelX() + current.getWidth() / 2;
			float cMaxY = current.getRelY() + current.getHeight() / 2;

			if(cMinX < minX)
				minX = cMinX;

			if(cMinY < minY)
				minY = cMinY;

			if(cMaxX > maxX)
				maxX = cMaxX;

			if(cMaxY > maxY)
				maxY = cMaxY;
		}
	}

	/**
	 * Assumes this body is currently in movement, meaning that its movement was already added to its position
	 *
	 * @param box box to check if overlapping with
	 * @return true if are overlapping, otherwise false
	 */
	public boolean overlaps(PreAABB box)
	{
		if(absMinX() > box.absMaxX())
			return false;

		if(absMaxX() < box.absMinX())
			return false;

		if(absMinY() > box.absMaxY())
			return false;

		if(absMaxY() < box.absMinY())
			return false;

		return true;
	}

	/**
	 * Assumes movement has already been added to body's position
	 *
	 * @return absolute minimum x coordinate of this AABB
	 */
	public float absMinX()
	{
		return minX + body.getPosition().x - (movement.x > 0 ? movement.x : 0);
	}

	/**
	 * Assumes movement has already been added to body's position
	 *
	 * @return absolute maximum x coordinate of this AABB
	 */
	public float absMaxX()
	{
		return maxX + body.getPosition().x - (movement.x < 0 ? movement.x : 0);
	}

	/**
	 * Assumes movement has already been added to body's position
	 *
	 * @return absolute minimum y coordinate of this AABB
	 */
	public float absMinY()
	{
		return minY + body.getPosition().y - (movement.y > 0 ? movement.y : 0);
	}

	/**
	 * Assumes movement has already been added to body's position
	 *
	 * @return absolute maximum y coordinate of this AABB
	 */
	public float absMaxY()
	{
		return maxY + body.getPosition().y - (movement.y < 0 ? movement.y : 0);
	}

}
