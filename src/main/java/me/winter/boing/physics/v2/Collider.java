package me.winter.boing.physics.v2;

import me.winter.boing.physics.v2.shapes.Shape;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class Collider
{
	private Solid solid;
	private Shape shape;
	private float bounciness;

	public Collider(Solid solid, Shape shape, float bounciness)
	{
		this.solid = solid;
		this.shape = shape;
		this.bounciness = bounciness;
	}

	public Solid getSolid()
	{
		return solid;
	}

	public Shape getShape()
	{
		return shape;
	}

	public float getBounciness()
	{
		return bounciness;
	}

	public void setBounciness(float bounciness)
	{
		this.bounciness = bounciness;
	}
}
