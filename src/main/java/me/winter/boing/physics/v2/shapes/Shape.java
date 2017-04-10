package me.winter.boing.physics.v2.shapes;

import me.winter.boing.physics.v2.Collider;
import me.winter.boing.physics.v2.Collision;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface Shape
{
	Collision collides(Shape shape);
	Collision collidesContinuous(Shape shape);
}
