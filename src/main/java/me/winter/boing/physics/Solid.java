package me.winter.boing.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import me.winter.boing.physics.shapes.Collider;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-10.
 */
public interface Solid
{
	World getWorld();

	Vector2 getPosition();

	default void update() {}

	Array<Collider> getColliders();

	default boolean collide(Collision collision)
	{
		return true;
	}
}
