package me.winter.boing.detection;

import me.winter.boing.Collision;
import me.winter.boing.colliders.Collider;

/**
 * A single collision detector able to detect collision between 2 specific colliders.
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public interface CollisionDetector<A extends Collider, B extends Collider>
{
	Collision collides(A shapeA, B shapeB);
}
