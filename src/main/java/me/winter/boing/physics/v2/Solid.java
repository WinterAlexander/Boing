package me.winter.boing.physics.v2;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface Solid
{
	Vector2 getPosition();
	Vector2 getVelocity();

	Array<Collider> getColliders();
}
