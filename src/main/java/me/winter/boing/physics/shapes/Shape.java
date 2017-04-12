package me.winter.boing.physics.shapes;

import me.winter.boing.physics.Solid;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface Shape
{
	Solid getSolid();

	float getAbsX();
	float getAbsY();
}
