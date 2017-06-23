package me.winter.boing.impl;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.DynamicBody;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
public class DynamicBodyImpl extends BodyImpl implements DynamicBody
{
	private Vector2 velocity = new Vector2();

	private float weight;

	public DynamicBodyImpl()
	{
		this(1f);
	}

	public DynamicBodyImpl(float weight)
	{
		this.weight = weight;
	}

	@Override
	public Vector2 getVelocity()
	{
		return velocity;
	}

	@Override
	public float getWeight()
	{
		return weight;
	}
}
