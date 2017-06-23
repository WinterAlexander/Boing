package me.winter.boing.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.Body;
import me.winter.boing.colliders.Box;
import me.winter.boing.colliders.Collider;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class BodyImpl implements Body
{
	private Vector2 position = new Vector2();

	private Array<Collider> colliders = new Array<>(Collider.class);
	private Collider[] array = new Collider[0];
	private float width = 0, height = 0;

	@Override
	public Vector2 getPosition()
	{
		return position;
	}

	public Collider[] getColliders()
	{
		return array;
	}

	public void addCollider(Collider collider)
	{
		colliders.add(collider);
		array = colliders.toArray();

		float minX = Float.POSITIVE_INFINITY;
		float minY = Float.POSITIVE_INFINITY;
		float maxX = Float.NEGATIVE_INFINITY;
		float maxY = Float.NEGATIVE_INFINITY;

		for(Collider current : colliders)
		{
			float cMinX = current.getAbsX() - current.getWidth() / 2;
			float cMinY = current.getAbsY() - current.getHeight() / 2;
			float cMaxX = current.getAbsX() + current.getWidth() / 2;
			float cMaxY = current.getAbsY() + current.getHeight() / 2;

			if(cMinX < minX)
				minX = cMinX;

			if(cMinY < minY)
				minY = cMinY;

			if(cMaxX > maxX)
				maxX = cMaxX;

			if(cMaxY > maxY)
				maxY = cMaxY;
		}

		//originX = (minX + maxX) / 2;
		//originY = (minY + maxY) / 2;
		width = maxX - minX;
		height = maxY - minY;
	}
}
