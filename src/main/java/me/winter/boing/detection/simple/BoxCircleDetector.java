package me.winter.boing.detection.simple;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.World;
import me.winter.boing.detection.PooledDetector;
import me.winter.boing.colliders.Box;
import me.winter.boing.colliders.Circle;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static java.lang.Math.abs;
import static java.lang.Math.signum;
import static me.winter.boing.util.VectorUtil.divide;

/**
 * Detects collisions between an Axis Aligned Bounding Box and a Circle
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
	public Collision collides(World world, Box shapeA, Circle shapeB)
	{
		float dx = shapeB.getAbsX() - shapeA.getAbsX();
		float dy = shapeB.getAbsY() - shapeA.getAbsY();

		float absDx = abs(dx);
		float absDy = abs(dy);

		float halfW = shapeA.width / 2;
		float halfH = shapeA.height / 2;

		if(absDx > halfW + shapeB.radius
				|| absDy > halfH + shapeB.radius)
			return null;

		//stupid box box collision inside (inverted if)
		if(absDx > halfW && absDy > halfH //if it's not inside
		&& (absDx - halfW) * (absDx - halfW) + (absDy - halfH) * (absDy - halfH) > shapeB.radius * shapeB.radius) //and it's not in the middle
			return null;

		float closestX = clamp(dx, -halfW, halfW);
		float closestY = clamp(dy, -halfH, halfH);

		Collision collision = collisionPool.obtain();

		if(absDx - halfW > absDy - halfH)
		{
			closestX = signum(closestX) * halfW;

			collision.normal.set(dx < 0 ? -1 : 1, 0);
			float len = Vector2.len(closestX - dx, closestY - dy);
			collision.penetration = shapeB.radius - len;
		}
		else
		{
			closestY = signum(closestY) * halfH;

			collision.normal.set(0, dy < 0 ? -1 : 1);
			float len = Vector2.len(closestX - dx, closestY - dy);
			collision.penetration = shapeB.radius - len;
		}

		collision.contactSurface = 0;
		collision.colliderA = shapeA;
		collision.colliderB = shapeB;
		collision.setImpactVelocities(shapeA.getBody(), shapeB.getBody());

		return collision;
	}
}