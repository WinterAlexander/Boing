package me.winter.boing.physics;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.resolver.CollisionResolver;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public class Collision
{
	//public Vector2 contact = new Vector2();
	public Vector2 normalA = new Vector2(), normalB = new Vector2();
	public float penetration;

	public Collider colliderA, colliderB;

	public void setAsSwapped(Collision collision)
	{
		normalA.set(collision.normalB);
		normalB.set(collision.normalA);
		penetration = collision.penetration;
		colliderA = collision.colliderB;
		colliderB = collision.colliderA;
	}
}
