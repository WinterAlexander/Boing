package me.winter.boing.physics.v2.shapes;

import me.winter.boing.physics.v2.Solid;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public abstract class AbstractShape implements Shape
{
	private Solid solid;
	private float x, y;

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

	public Solid getSolid()
	{
		return solid;
	}

	public void setSolid(Solid solid)
	{
		this.solid = solid;
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}
}
