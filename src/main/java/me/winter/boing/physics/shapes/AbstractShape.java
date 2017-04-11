package me.winter.boing.physics.shapes;

import me.winter.boing.physics.Solid;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public abstract class AbstractShape implements Shape
{
	public Solid solid;
	public float x, y;

	public AbstractShape(Solid solid, float x, float y)
	{
		this.solid = solid;
		this.x = x;
		this.y = y;
	}

	public float getAbsX()
	{
		return x + solid.getPosition().x;
	}

	public float getAbsY()
	{
		return y + solid.getPosition().y;
	}
}
