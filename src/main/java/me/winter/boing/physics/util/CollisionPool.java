package me.winter.boing.physics.util;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.Collision;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-25.
 */
public class CollisionPool extends Pool<Collision>
{
	@Override
	protected Collision newObject()
	{
		return new Collision();
	}
}