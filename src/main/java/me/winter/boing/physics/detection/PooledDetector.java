package me.winter.boing.physics.detection;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.shapes.Shape;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public abstract class PooledDetector<A extends Shape, B extends Shape> implements CollisionDetector<A, B>
{
	protected Pool<Collision> collisionPool;

	public PooledDetector(Pool<Collision> collisionPool)
	{
		this.collisionPool = collisionPool;
	}
}
