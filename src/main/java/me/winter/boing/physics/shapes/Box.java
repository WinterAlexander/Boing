package me.winter.boing.physics.shapes;

import me.winter.boing.physics.Solid;

/**
 * An Axis Aligned Bounding Box collider
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class Box extends AbstractCollider
{
	public float width, height;

	public Box(Solid solid, float x, float y, float width, float height)
	{
		super(solid, x, y);
		this.width = width;
		this.height = height;
	}
}
