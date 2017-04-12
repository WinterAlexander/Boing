package me.winter.boing.test.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.Solid;
import me.winter.boing.physics.World;
import me.winter.boing.physics.shapes.AABB;
import me.winter.boing.physics.shapes.Circle;
import me.winter.boing.physics.Collider;
import me.winter.boing.physics.shapes.Shape;

import java.awt.Graphics;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class DynSolidImpl extends SolidImpl implements DynamicSolid
{
	private Vector2 velocity, movement;
	private float mass;

	public DynSolidImpl(World world)
	{
		this(world, 1f);
	}

	public DynSolidImpl(World world, float mass)
	{
		super(world);
		this.velocity = new Vector2();
		this.movement = new Vector2();
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
	public float getMass()
	{
		return mass;
	}
}
