package me.winter.boing.physics.detection.detectors;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.detection.PooledDetector;
import me.winter.boing.physics.shapes.Box;
import me.winter.boing.physics.shapes.Circle;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static java.lang.Math.abs;
import static java.lang.Math.signum;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class BoxCircleDetector extends PooledDetector<Box, Circle>
{
	public BoxCircleDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
	}

	@Override
	public Collision collides(Box boxA, Circle circleB)
	{
		float dx = circleB.getAbsX() - boxA.getAbsX();
		float dy = circleB.getAbsY() - boxA.getAbsY();

		float absDx = abs(dx);
		float absDy = abs(dy);

		float halfW = boxA.width / 2;
		float halfH = boxA.height / 2;

		//stupid box box collision outside
		if(absDx > halfW + circleB.radius
				|| absDy > halfH + circleB.radius)
			return null;

		//stupid box box collision inside (inverted if)
		if(!(absDx < halfW || absDy < halfH) //if it's not inside
				&& (absDx - halfW) * (absDx - halfW) + (absDy - halfH) * (absDy - halfH) >= circleB.radius * circleB.radius) //and it's not in the middle
			return null;

		float closestX = clamp(dx, -halfW, halfW);
		float closestY = clamp(dy, -halfH, halfH);

		Collision collision = collisionPool.obtain();

		if(absDx - halfW > absDy - halfH)
		{
			closestX = signum(closestX) * halfW;

			collision.normalA.set(dx, 0);
			collision.normalB.set(closestX - dx, closestY - dy);
			collision.penetration = circleB.radius - collision.normalB.len();
		}
		else
		{
			closestY = signum(closestY) * halfH;

			collision.normalA.set(0, dy);
			collision.normalB.set(closestX - dx, closestY - dy);
			collision.penetration = circleB.radius - collision.normalB.len();
		}


		collision.colliderA = boxA;
		collision.colliderB = circleB;
		collision.setImpactVelocities(boxA.getSolid(), circleB.getSolid());

		return collision;
	}
}