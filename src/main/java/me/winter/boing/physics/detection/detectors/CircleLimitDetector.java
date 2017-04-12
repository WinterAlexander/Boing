package me.winter.boing.physics.detection.detectors;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.detection.PooledDetector;
import me.winter.boing.physics.shapes.Circle;
import me.winter.boing.physics.shapes.Limit;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class CircleLimitDetector extends PooledDetector<Circle, Limit>
{
	public CircleLimitDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
	}

	@Override
	public Collision collides(Circle shapeA, Limit shapeB)
	{
		return null;
	}
}