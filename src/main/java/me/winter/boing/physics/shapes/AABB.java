package me.winter.boing.physics.shapes;

import me.winter.boing.physics.Solid;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class AABB extends AbstractShape
{
	public float width, height;

	public AABB(Solid solid, float x, float y, float width, float height)
	{
		super(solid, x, y);
		this.width = width;
		this.height = height;
	}
}
