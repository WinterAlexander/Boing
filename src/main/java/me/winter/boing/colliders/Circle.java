package me.winter.boing.colliders;

import me.winter.boing.Body;

/**
 * A circle collider
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class Circle extends AbstractCollider
{
	public float radius;

	public Circle(Body body, float x, float y, float radius)
	{
		super(body, x, y);
		this.radius = radius;
	}

	@Override
	public float getWidth()
	{
		return radius;
	}

	@Override
	public float getHeight()
	{
		return radius;
	}
}
