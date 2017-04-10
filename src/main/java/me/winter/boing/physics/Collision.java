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
	public Vector2 contact = new Vector2();
	public Vector2 normalA = new Vector2(), normalB = new Vector2();

	public Collider colliderA, colliderB;
	public CollisionResolver resolver;

	public void resolve()
	{
		resolver.resolve(this);
	}
}
