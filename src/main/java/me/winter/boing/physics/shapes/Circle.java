package me.winter.boing.physics.shapes;

import me.winter.boing.physics.Solid;

/**
 * A circle collider
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class Circle extends AbstractCollider
{
	public float radius;

	public Circle(Solid solid, float x, float y, float radius)
	{
		super(solid, x, y);
		this.radius = radius;
	}
}
