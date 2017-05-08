package me.winter.boing.impl;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.Body;
import me.winter.boing.shapes.Collider;

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
	}
}
