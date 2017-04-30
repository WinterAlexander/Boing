package me.winter.boing.physics.resolver;

import me.winter.boing.physics.Collision;

/**
 * Represents a object able to resolve a collision
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface CollisionResolver
{
	void resolve(Collision collision);
}
