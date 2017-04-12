package me.winter.boing.physics.detection;

import me.winter.boing.physics.Collision;
import me.winter.boing.physics.shapes.Shape;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public interface CollisionDetector<A extends Shape, B extends Shape>
{
	Collision collides(A shapeA, B shapeB);
}
