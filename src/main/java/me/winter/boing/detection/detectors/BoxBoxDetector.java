package me.winter.boing.detection.detectors;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.detection.PooledDetector;
import me.winter.boing.shapes.Box;

import static java.lang.Math.abs;

/**
 * Detects collisions between 2 Axis Aligned Bounding Box
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class BoxBoxDetector extends PooledDetector<Box, Box>
{
	public BoxBoxDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
	}

	@Override
	public Collision collides(Box shapeA, Box shapeB)
	{
		float dx = shapeB.getAbsX() - shapeA.getAbsX();
		float dy = shapeB.getAbsY() - shapeA.getAbsY();

		float peneX = shapeA.width / 2 + shapeB.width / 2 - abs(dx);
		float peneY = shapeA.height / 2 + shapeB.height / 2 - abs(dy);

		if(peneX < 0 || peneY < 0)
			return null;

		Collision collision = collisionPool.obtain();

		if(peneX < peneY)
		{
			collision.normalA.set(dx < 0 ? -1 : 1, 0);
			collision.normalB.set(-dx < 0 ? -1 : 1, 0);
			collision.penetration = peneX;
			collision.contactSurface = peneY;
		}
		else
		{
			collision.normalA.set(0, dy < 0 ? -1 : 1);
			collision.normalB.set(0, -dy < 0 ? -1 : 1);
			collision.penetration = peneY;
			collision.contactSurface = peneX;
		}

		collision.colliderA = shapeA;
		collision.colliderB = shapeB;
		collision.setImpactVelocities(shapeA.getBody(), shapeB.getBody());

		return collision;
	}
}