package me.winter.boing.physics.detection.detectors;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.detection.PooledDetector;
import me.winter.boing.physics.shapes.Circle;

import static java.lang.Math.sqrt;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
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

		float dst2 = dx * dx + dy * dy;

		if(dst2 >= r * r)
			return null;

		Collision collision = collisionPool.obtain();

		collision.normalA.set(dx, dy);
		collision.normalB.set(-dx, -dy);
		collision.penetration = r - (float)sqrt(dst2);

		collision.colliderA = shapeA;
		collision.colliderB = shapeB;
		collision.setImpactVelocities(shapeA.getSolid(), shapeB.getSolid());

		return collision;
	}
}
