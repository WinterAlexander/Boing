package me.winter.boing.physics.v2.colliders;

import me.winter.boing.physics.v2.response.CollisionResponse;
import me.winter.boing.physics.v2.Solid;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public abstract class Collider
{
	private Solid solid;
	private float x, y; //position relative to solid

	public Collider(Solid solid, float x, float y)
	{
		this.solid = solid;
		this.x = x;
		this.y = y;
	}

	public abstract CollisionResponse collides(Collider collider);
	public abstract CollisionResponse collidesContinuous(Collider collider);

	public Solid getSolid()
	{
		return solid;
	}

	public float getAbsX()
	{
		return this.x + solid.getPosition().x;
	}

	public float getAbsY()
	{
		return this.y + solid.getPosition().y;
	}

	public float getRelX()
	{
		return this.x;
	}

	public float getRelY()
	{
		return this.y;
	}
}
