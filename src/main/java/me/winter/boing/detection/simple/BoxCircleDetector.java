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
	public Collision collides(World world, Box boxA, Circle circleB)
	{
		float dx = circleB.getAbsX() - boxA.getAbsX();
		float dy = circleB.getAbsY() - boxA.getAbsY();

		float absDx = abs(dx);
		float absDy = abs(dy);

		float halfW = boxA.width / 2;
		float halfH = boxA.height / 2;

		if(absDx > halfW + circleB.radius
				|| absDy > halfH + circleB.radius)
			return null;

		//stupid box box collision inside (inverted if)
		if(absDx > halfW && absDy > halfH //if it's not inside
		&& (absDx - halfW) * (absDx - halfW) + (absDy - halfH) * (absDy - halfH) > circleB.radius * circleB.radius) //and it's not in the middle
			return null;

		Collision collision = collisionPool.obtain();

		if(absDx - halfW > absDy - halfH)
		{
			collision.normal.set(dx < 0 ? -1 : 1, 0);

			collision.penetration = (cA, cB) -> {

				float ddx = cB.getAbsX() - cA.getAbsX();
				float ddy = cB.getAbsY() - cA.getAbsY();

				float dynHalfW = ((Box)cA).width / 2;
				float dynHalfH = ((Box)cA).height / 2;

				return ((Circle)cB).radius - Vector2.len(signum(clamp(ddx, -dynHalfW, dynHalfW)) * dynHalfW - ddx, clamp(ddy, -dynHalfH, dynHalfH) - ddy);
			};
		}
		else
		{
			collision.normal.set(0, dy < 0 ? -1 : 1);

			collision.penetration = (cA, cB) -> {

				float ddx = cB.getAbsX() - cA.getAbsX();
				float ddy = cB.getAbsY() - cA.getAbsY();

				float dynHalfW = ((Box)cA).width / 2;
				float dynHalfH = ((Box)cA).height / 2;

				return ((Circle)cB).radius - Vector2.len(clamp(ddx, -dynHalfW, dynHalfW) - ddx, signum(clamp(ddy, -dynHalfH, dynHalfH)) * dynHalfH - ddy);
			};
		}

		collision.contactSurface = (cA, cB) -> 0;
		collision.colliderA = boxA;
		collision.colliderB = circleB;
		collision.setImpactVelocities(boxA.getBody(), circleB.getBody());

		return collision;
	}
}