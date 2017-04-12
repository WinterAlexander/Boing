package me.winter.boing.physics.detection;

import me.winter.boing.physics.Collision;
import me.winter.boing.physics.shapes.Collider;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public interface CollisionDetector<A extends Collider, B extends Collider>
{
	Collision collides(A shapeA, B shapeB);
}
