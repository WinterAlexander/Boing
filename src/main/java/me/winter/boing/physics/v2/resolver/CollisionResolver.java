package me.winter.boing.physics.v2.resolver;

import me.winter.boing.physics.v2.Collision;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public abstract class CollisionResolver
{
	public final float priority;

	public CollisionResolver(float priority)
	{
		this.priority = priority;
	}

	public abstract void resolve(Collision collision);
}
