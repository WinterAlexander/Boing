package me.winter.boing.physics;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.resolver.CollisionResolver;
import me.winter.boing.physics.shapes.Collider;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class Collision
{
	//public Vector2 contact = new Vector2();
	public Vector2 normalA = new Vector2(), normalB = new Vector2();
	public Vector2 impactVelA = new Vector2(), impactVelB = new Vector2();
	public float penetration;

	public Collider colliderA, colliderB;

	public void setAsSwapped(Collision collision)
	{
		normalA.set(collision.normalB);
		normalB.set(collision.normalA);
		impactVelA.set(collision.impactVelB);
		impactVelB.set(collision.impactVelA);
		penetration = collision.penetration;
		colliderA = collision.colliderB;
		colliderB = collision.colliderA;
	}

	public void setImpactVelocities(Solid solidA, Solid solidB)
	{
		if(solidA instanceof DynamicSolid)
			impactVelA.set(((DynamicSolid)solidA).getVelocity());
		else
			impactVelA.set(0, 0);

		if(solidB instanceof DynamicSolid)
			impactVelB.set(((DynamicSolid)solidB).getVelocity());
		else
			impactVelB.set(0, 0);
	}
}
