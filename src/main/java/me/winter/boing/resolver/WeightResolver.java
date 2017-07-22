package me.winter.boing.resolver;

import me.winter.boing.Collision;
import me.winter.boing.World;

/**
 * Object able to resolve the weight of 2 objects of a collision
 * <p>
 * Created by Alexander Winter on 2017-07-22.
 */
public interface WeightResolver
{
	void resolveWeight(Collision collision, World world);
}
