package me.winter.boing.physics.detection.detectors;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.detection.PooledDetector;
import me.winter.boing.physics.shapes.Box;
import me.winter.boing.physics.shapes.Limit;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class BoxLimitDetector extends PooledDetector<Box, Limit>
{
	public BoxLimitDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
	}

	@Override
	public Collision collides(Box shapeA, Limit shapeB)
	{
		return null;
	}
}