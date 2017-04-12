package me.winter.boing.physics.shapes;

import me.winter.boing.physics.Solid;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public abstract class AbstractCollider implements Collider
{
	protected Solid solid;
	protected float x, y;

	public AbstractCollider(Solid solid, float x, float y)
	{
		this.solid = solid;
		this.x = x;
		this.y = y;
	}

	@Override
	public Solid getSolid()
	{
		return solid;
	}

	@Override
	public float getAbsX()
	{
		return x + solid.getPosition().x;
	}

	@Override
	public float getAbsY()
	{
		return y + solid.getPosition().y;
	}
}
