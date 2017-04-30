package me.winter.boing.physics.shapes;

import me.winter.boing.physics.Body;

/**
 * An Axis Aligned Bounding Box collider
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class Box extends AbstractCollider
{
	public float width, height;

	public Box(Body body, float x, float y, float width, float height)
	{
		super(body, x, y);
		this.width = width;
		this.height = height;
	}
}
