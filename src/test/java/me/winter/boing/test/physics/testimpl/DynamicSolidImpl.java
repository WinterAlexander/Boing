package me.winter.boing.test.physics.testimpl;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.World;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-26.
 */
public class DynamicSolidImpl extends SolidImpl implements DynamicSolid
{
	private Vector2 velocity, movement, lastReplacement;
	private float mass;

	public DynamicSolidImpl(World world)
	{
		this(world, 1f);
	}

	public DynamicSolidImpl(World world, float mass)
	{
		super(world);
		this.velocity = new Vector2();
		this.movement = new Vector2();
		this.lastReplacement = new Vector2();
		this.mass = mass;
	}

	@Override
	public Vector2 getVelocity()
	{
		return velocity;
	}

	@Override
	public Vector2 getMovement()
	{
		return movement;
	}

	@Override
	public float weightFor(DynamicSolid against)
	{
		return mass;
	}

	@Override
	public Vector2 getLastReplacement()
	{
		return lastReplacement;
	}
}