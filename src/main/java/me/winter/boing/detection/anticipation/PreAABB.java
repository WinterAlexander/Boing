package me.winter.boing.detection.anticipation;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.Body;
import me.winter.boing.colliders.Collider;

/**
 * Undocumented :(
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

	private float absMinX()
	{
		return minX + body.getPosition().x + (movement.x < 0 ? movement.x : 0);
	}

	private float absMaxX()
	{
		return maxX + body.getPosition().x + (movement.x > 0 ? movement.x : 0);
	}

	private float absMinY()
	{
		return minY + body.getPosition().y + (movement.y < 0 ? movement.y : 0);
	}

	private float absMaxY()
	{
		return maxY + body.getPosition().y + (movement.y > 0 ? movement.y : 0);
	}

}
