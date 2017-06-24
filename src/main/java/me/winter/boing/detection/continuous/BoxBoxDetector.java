package me.winter.boing.detection.continuous;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.World;
import me.winter.boing.detection.PooledDetector;
import me.winter.boing.colliders.Box;
import me.winter.boing.util.DynamicFloat;
import me.winter.boing.util.FloatUtil;

import static com.badlogic.gdx.math.Vector2.dot;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.signum;
import static me.winter.boing.util.FloatUtil.DEFAULT_ULPS;
import static me.winter.boing.util.FloatUtil.areEqual;
import static me.winter.boing.util.FloatUtil.getGreatestULP;
import static me.winter.boing.util.FloatUtil.isGreaterOrEqual;
import static me.winter.boing.util.FloatUtil.isSmallerOrEqual;

/**
 * Detects collisions between 2 Axis Aligned Bounding Box
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class BoxBoxDetector extends PooledDetector<Box, Box>
{
	private me.winter.boing.detection.simple.BoxBoxDetector simple;

	public BoxBoxDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
		simple = new me.winter.boing.detection.simple.BoxBoxDetector(collisionPool);
	}

	@Override
	public Collision collides(World world, Box boxA, Box boxB)
	{
		Vector2 vecA = boxA.getMovement(world);
		Vector2 vecB = boxB.getMovement(world);

		Vector2 csA = boxA.getCollisionShifting(world);
		Vector2 csB = boxB.getCollisionShifting(world);

		float ax = boxA.getAbsX();
		float ay = boxA.getAbsY();
		float bx = boxB.getAbsX();
		float by = boxB.getAbsY();

		float epsilon = DEFAULT_ULPS * getGreatestULP(ax, ay, bx, by, vecA.x, vecA.y, vecB.x, vecB.y, boxA.width, boxA.height, boxB.width, boxB.height);


		//TODO find which limits collide and stop this cancer

		Collision collision = collides(
				boxA, vecA, csA,
				boxB, vecB, csB,
				epsilon, 0, 1);

		if(collision == null)
		{
			collision = collides(
					boxA, vecA, csA,
					boxB, vecB, csB,
					epsilon, 0, -1);

			if(collision == null)
			{
				collision = collides(
						boxA, vecA, csA,
						boxB, vecB, csB,
						epsilon, 1, 0);

				if(collision == null)
				{
					collision = collides(
							boxA, vecA, csA,
							boxB, vecB, csB,
							epsilon, -1, 0);

					if(collision == null)
						return simple.collides(world, boxA, boxB);
				}
			}
		}

		collision.colliderA = boxA;
		collision.colliderB = boxB;
		collision.setImpactVelocities(boxA.getBody(), boxB.getBody());

		return collision;
	}

	public Collision collides(final Box boxA, final Vector2 vecA, final Vector2 csA,
	                          final Box boxB, final Vector2 vecB, final Vector2 csB,
	                          final float epsilon, final float nx, final float ny)
	{
		final float ax = boxA.getAbsX() + nx * boxA.width / 2;
		final float ay = boxA.getAbsY() + ny * boxA.height / 2;

		final float bx = boxB.getAbsX() - nx * boxB.width / 2;
		final float by = boxB.getAbsY() - ny * boxB.height / 2;

		if(!isGreaterOrEqual(ax * nx + ay * ny, bx * nx + by * ny, epsilon)) //if limitB with his velocity isn't after limitA with his velocity
			return null; //no collision

		final float vax, vay, vbx, vby;

		if(dot(nx, ny, csA.x, csA.y) > 0)
		{
			vax = vecA.x + csA.x;
			vay = vecA.y + csA.y;
		}
		else
		{
			vax = vecA.x;
			vay = vecA.y;
		}

		if(dot(-nx, -ny, csB.x, csB.y) > 0)
		{
			vbx = vecB.x + csB.x;
			vby = vecB.y + csB.y;
		}
		else
		{
			vbx = vecB.x;
			vby = vecB.y;
		}

		float pax = ax - vax;
		float pay = ay - vay;
		float pbx = bx - vbx;
		float pby = by - vby;

		if(!isSmallerOrEqual(pax * nx + pay * ny, pbx * nx + pby * ny, epsilon)) //if limitB isn't before limitA
			return null; //no collision

		final float hsA = abs(ny * boxA.width / 2 + nx * boxA.height / 2);
		final float hsB = abs(ny * boxB.width / 2 + nx * boxB.height / 2);


		DynamicFloat surfaceFormula = () -> {

			float nax = boxA.getAbsX() + nx * boxA.width / 2;
			float nay = boxA.getAbsY() + ny * boxA.height / 2;
			float nbx = boxB.getAbsX() - nx * boxB.width / 2;
			float nby = boxB.getAbsY() - ny * boxB.height / 2;

			float diff = ((nbx - vbx) - (nax - vax)) * nx + ((nby - vby) - (nay - vay)) * ny;
			float vecDiff = (vbx - vax) * nx + (vby - vay) * ny;

			float midpoint = vecDiff != 0 ? (diff + vecDiff) / vecDiff : 0f;

			float mxA = nax - vax * midpoint; //midpoint x for A
			float myA = nay - vay * midpoint; //midpoint y for A
			float mxB = nbx - vbx * midpoint; //midpoint x for B
			float myB = nby - vby * midpoint; //midpoint y for B

			float limitA1 = -ny * (mxA + hsA) + nx * (myA + hsA);
			float limitA2 = -ny * (mxA - hsA) + nx * (myA - hsA);
			float limitB1 = -ny * (mxB + hsB) + nx * (myB + hsB);
			float limitB2 = -ny * (mxB - hsB) + nx * (myB - hsB);

			return min(FloatUtil.max(limitA1, limitA2), FloatUtil.max(limitB1, limitB2)) //minimum of the maximums
					- FloatUtil.max(min(limitA1, limitA2), min(limitB1, limitB2)); //maximum of the minimums
		};

		float surface = surfaceFormula.getValue();

		if(areEqual(surface, 0, epsilon))
		{
			float diff = (pbx - pax) * nx + (pby - pay) * ny;
			float vecDiff = (vbx - vax) * nx + (vby - vay) * ny;
			float sizeDiff = (hsB + hsA) * abs(nx) + (hsB + hsA) * abs(ny);

			float midpoint = vecDiff != 0 ? (diff + vecDiff + sizeDiff) / vecDiff : 0f;

			float mxA = ax - vax * midpoint; //midpoint x for A
			float myA = ay - vay * midpoint; //midpoint y for A
			float mxB = bx - vbx * midpoint; //midpoint x for B
			float myB = by - vby * midpoint; //midpoint y for B

			float limitA1 = -ny * (mxA + hsA) + nx * (myA + hsA);
			float limitA2 = -ny * (mxA - hsA) + nx * (myA - hsA);
			float limitB1 = -ny * (mxB + hsB) + nx * (myB + hsB);
			float limitB2 = -ny * (mxB - hsB) + nx * (myB - hsB);

			surface = min(FloatUtil.max(limitA1, limitA2), FloatUtil.max(limitB1, limitB2)) //minimum of the maximums
					- FloatUtil.max(min(limitA1, limitA2), min(limitB1, limitB2)); //maximum of the minimums

			if(isSmallerOrEqual(surface, 0, epsilon))
				return null;
		}
		else if(surface < 0)
			return null;

		Collision collision = collisionPool.obtain();

		collision.normal.set(nx, ny);

		collision.penetration = () -> {
			return -((boxB.getAbsX() - nx * boxB.width / 2 - (boxA.getAbsX() + nx * boxA.width / 2)) * nx
					+ (boxB.getAbsY() - ny * boxB.height / 2 - (boxA.getAbsY() + ny * boxA.height / 2)) * ny);
		};

		//boing v1 priority algorithm
		collision.priority = ((bx - ax) * nx + (by - ay) * ny) / ((vbx - vax) * nx + (vby - vay) * ny);

		collision.contactSurface = surfaceFormula;

		return collision;
	}
}