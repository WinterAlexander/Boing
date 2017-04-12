package me.winter.boing.physics;

import me.winter.boing.physics.resolver.CollisionResolver;
import me.winter.boing.physics.shapes.Shape;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class Collider
{
	private Solid solid;
	private Shape shape;

	public Collider(Solid solid, Shape shape)
	{
		this.solid = solid;
		this.shape = shape;
	}

	public Solid getSolid()
	{
		return solid;
	}

	public Shape getShape()
	{
		return shape;
	}
}
