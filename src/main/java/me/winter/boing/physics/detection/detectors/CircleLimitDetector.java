package me.winter.boing.physics.detection.detectors;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.detection.PooledDetector;
import me.winter.boing.physics.shapes.Circle;
import me.winter.boing.physics.shapes.Limit;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static java.lang.Math.abs;
import static java.lang.Math.signum;

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
	public Collision collides(Circle circleA, Limit limitB)
	{
		float dx = limitB.getAbsX() - circleA.getAbsX();
		float dy = limitB.getAbsY() - circleA.getAbsY();

		float absDx = abs(dx);
		float absDy = abs(dy);

		float halfW = limitB.size / 2 * limitB.normal.y;
		float halfH = limitB.size / 2 * limitB.normal.x;

		//stupid box box collision outside
		if(absDx >= halfW + circleA.radius
		|| absDy >= halfH + circleA.radius)
			return null;

		//stupid box box collision inside (inverted if)
		if(absDx >= halfW && absDy >= halfH //if it's not inside
		&& (absDx - halfW) * (absDx - halfW) + (absDy - halfH) * (absDy - halfH) >= circleA.radius * circleA.radius) //and it's not in the middle
			return null;

		float closestX = clamp(dx, -halfW, halfW);
		float closestY = clamp(dy, -halfH, halfH);

		Collision collision = collisionPool.obtain();

		if(absDx - halfW > absDy - halfH)
		{
			closestX = signum(closestX) * halfW;

			collision.normalA.set(closestX - dx, closestY - dy);
			collision.normalB.set(dx, 0);
			collision.penetration = circleA.radius - collision.normalA.len();
		}
		else
		{
			closestY = signum(closestY) * halfH;

			collision.normalA.set(closestX - dx, closestY - dy);
			collision.normalB.set(0, dy);
			collision.penetration = circleA.radius - collision.normalA.len();
		}


		collision.colliderA = limitB;
		collision.colliderB = circleA;
		collision.setImpactVelocities(limitB.getSolid(), circleA.getSolid());

		return collision;
	}
}