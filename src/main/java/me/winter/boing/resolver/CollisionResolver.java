package me.winter.boing.resolver;

import me.winter.boing.Collision;

/**
 * Represents a object able to resolve a collision
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface CollisionResolver
{
	void resolve(Collision collision);
}