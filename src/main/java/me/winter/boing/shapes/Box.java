package me.winter.boing.shapes;

import me.winter.boing.Body;

import static java.lang.Math.ulp;
import static me.winter.boing.util.FloatUtil.max;

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
	public float getPrecision()
	{
		return ulp(max(getAbsX(), getAbsY(), width, height, getMovement().x, getMovement().y));
	}
}
