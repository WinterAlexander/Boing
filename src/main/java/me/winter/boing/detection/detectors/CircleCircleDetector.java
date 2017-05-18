package me.winter.boing.detection.detectors;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.detection.PooledDetector;
import me.winter.boing.shapes.Circle;

import static java.lang.Math.sqrt;
import static me.winter.boing.util.VectorUtil.divide;

/**
 * Detects collisions between 2 circles
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
@Deprecated
public class CircleCircleDetector extends PooledDetector<Circle, Circle>
{
	public CircleCircleDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
	}

	@Override
	public Collision collides(Circle shapeA, Circle shapeB)
	{
		float dx = shapeB.getAbsX() - shapeA.getAbsX();
		float dy = shapeB.getAbsY() - shapeA.getAbsY();

		float r = shapeA.radius + shapeB.radius;
		float r2 = r * r;

		float dst2 = dx * dx + dy * dy;

		if(dst2 > r2)
			return null;

		Collision collision = collisionPool.obtain();

		float dst = (float)sqrt(dst2);

		divide(collision.normalA.set(dx, dy), dst);
		divide(collision.normalB.set(-dx, -dy), dst);
		collision.penetration = r - dst;
		//collision.contactSurface = 2 * (float)sqrt(r2 - dst2);
		collision.contactSurface = 0f; //theorically 0 since circles can other touch each other on one point

		collision.colliderA = shapeA;
		collision.colliderB = shapeB;
		collision.setImpactVelocities(shapeA.getBody(), shapeB.getBody());

		return collision;
	}
}
