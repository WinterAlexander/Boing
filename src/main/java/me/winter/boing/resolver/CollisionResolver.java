package me.winter.boing.resolver;

import me.winter.boing.Collision;
import me.winter.boing.World;

/**
 * Represents a object able to resolve a collision between 2 bodies where at least one is dynamic.
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface CollisionResolver
{
	/**
	 * Resolves the collision. The way it is resolved is based on the implentation.
	 * (See ReplaceResolver, VelocityResolver)
	 *
	 * @param collision collision to resolve
	 * @param world world in which this collision occurred
	 * @return true if the collision was resolved, otherwise false
	 */
	boolean resolveCollision(Collision collision, World world);
}
