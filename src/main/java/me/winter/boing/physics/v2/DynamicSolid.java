package me.winter.boing.physics.v2;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface DynamicSolid extends Solid
{
	Vector2 getVelocity();
	Vector2 getMovement();

	float getMass();
}
