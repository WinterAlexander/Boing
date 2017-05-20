package me.winter.boing.detection;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.colliders.Collider;

/**
 * CollisionDetection that requires a collisionPool to prevent creating new objects
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public abstract class PooledDetector<A extends Collider, B extends Collider> implements CollisionDetector<A, B>
{
	protected Pool<Collision> collisionPool;

	public PooledDetector(Pool<Collision> collisionPool)
	{
		this.collisionPool = collisionPool;
	}
}
