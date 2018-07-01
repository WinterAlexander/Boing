package me.winter.boing.detection.simple;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.PhysicsWorld;
import me.winter.boing.colliders.Circle;
import me.winter.boing.detection.PooledDetector;

import static java.lang.Math.sqrt;
import static me.winter.boing.util.VectorUtil.divide;

/**
 * Detects collisions between 2 circles
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
	public Collision collides(PhysicsWorld world, Circle circleA, Circle circleB)
	{
		float dx = circleB.getAbsX() - circleA.getAbsX();
		float dy = circleB.getAbsY() - circleA.getAbsY();

		float r = circleA.radius + circleB.radius;
		float r2 = r * r;

		float dst2 = dx * dx + dy * dy;

		if(dst2 > r2)
			return null;

		Collision collision = collisionPool.obtain();

		float dst = (float)sqrt(dst2);

		divide(collision.normal.set(dx, dy), dst);
		collision.penetration = (cA, cB) -> {
			float dynR = ((Circle)cA).radius + ((Circle)cB).radius;
			float dynDx = cB.getAbsX() - cA.getAbsX();
			float dynDy = cB.getAbsY() - cA.getAbsY();

			return dynR - (float)sqrt(dynDx * dynDx + dynDy * dynDy);
		};
		collision.contactSurface = (cA, cB) -> 0f; //0 since circles can other touch each other at one point

		collision.colliderA = circleA;
		collision.colliderB = circleB;
		collision.setImpactVelocities(circleA.getBody(), circleB.getBody());

		return collision;
	}
}
