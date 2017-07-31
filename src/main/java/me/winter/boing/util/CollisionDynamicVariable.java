package me.winter.boing.util;

import me.winter.boing.colliders.Collider;

/**
 * A variable within a collision, need
 * <p>
 * Created by Alexander Winter on 2017-06-10.
 */
@FunctionalInterface
public interface CollisionDynamicVariable
{
	float getValue(Collider a, Collider b);
}
