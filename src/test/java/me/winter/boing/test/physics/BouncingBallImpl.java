package me.winter.boing.test.physics;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.VelocityUtil;
import me.winter.boing.physics.World;

import static me.winter.boing.physics.VelocityUtil.getMassRatio;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class DynSolidImpl extends SolidImpl implements DynamicSolid
{
	private Vector2 velocity, movement;
	private float mass;

	private Vector2 tmpVector = new Vector2();

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
	public boolean collide(Collision collision)
	{
		VelocityUtil.reflect(getVelocity(), collision.normalB);

		if(collision.colliderB.getSolid() instanceof DynamicSolid)
		{
			DynamicSolid dynamicSolid = ((DynamicSolid)collision.colliderB.getSolid());

			float massRatio = getMassRatio(getMass(), dynamicSolid.getMass());

			getVelocity().add(collision.impactVelB);
			getVelocity().scl(1f - massRatio);
		}

		return true;
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
