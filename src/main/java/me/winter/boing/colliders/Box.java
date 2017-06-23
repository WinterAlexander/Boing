package me.winter.boing.colliders;

import me.winter.boing.Body;

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

	@Override
	public float getWidth()
	{
		return width;
	}

	@Override
	public float getHeight()
	{
		return height;
	}
}
