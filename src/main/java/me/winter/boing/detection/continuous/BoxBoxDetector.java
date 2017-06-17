package me.winter.boing.detection.continuous;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.World;
import me.winter.boing.detection.PooledDetector;
import me.winter.boing.colliders.Box;

import static com.badlogic.gdx.math.Vector2.dot;
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
	public BoxBoxDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
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
				boxA.width / 2, vecA.x, vecA.y, ax, ay + boxA.height / 2, csA.x, csA.y,
				boxB.width / 2, vecB.x, vecB.y, bx, by - boxB.height / 2, csB.x, csB.y,
				epsilon, 0, 1);

		if(collision == null)
		{
			collision = collides(
					boxA.width / 2, vecA.x, vecA.y, ax, ay - boxA.height / 2, csA.x, csA.y,
					boxB.width / 2, vecB.x, vecB.y, bx, by + boxB.height / 2, csB.x, csB.y,
					epsilon, 0, -1);

			if(collision == null)
			{
				collision = collides(
						boxA.height / 2, vecA.x, vecA.y, ax + boxA.width / 2, ay, csA.x, csA.y,
						boxB.height / 2, vecB.x, vecB.y, bx - boxB.width / 2, by, csB.x, csB.y,
						epsilon, 1, 0);

				if(collision == null)
				{
					collision = collides(
							boxA.height / 2, vecA.x, vecA.y, ax - boxA.width / 2, ay, csA.x, csA.y,
							boxB.height / 2, vecB.x, vecB.y, bx + boxB.width / 2, by, csB.x, csB.y,
							epsilon, -1, 0);

					if(collision == null)
						return null;
				}
			}
		}

		collision.colliderA = boxA;
		collision.colliderB = boxB;
		collision.setImpactVelocities(boxA.getBody(), boxB.getBody());

		return collision;
	}

	public Collision collides(float hsA, float vax, float vay, float ax, float ay, float csax, float csay,
	                          float hsB, float vbx, float vby, float bx, float by, float csbx, float csby,
	                          float epsilon, float nx, float ny)
	{

		if(!isGreaterOrEqual(ax * nx + ay * ny, bx * nx + by * ny, epsilon)) //if limitB with his velocity isn't after limitA with his velocity
			return null; //no collision

		if(dot(nx, ny, csax, csay) > 0)
		{
			vax += csax;
			vay += csay;
		}

		if(dot(-nx, -ny, csbx, csby) > 0)
		{
			vbx += csbx;
			vby += csby;
		}

		float pax = ax - vax;
		float pay = ay - vay;
		float pbx = bx - vbx;
		float pby = by - vby;

		if(!isSmallerOrEqual(pax * nx + pay * ny, pbx * nx + pby * ny, epsilon)) //if limitB isn't before limitA
			return null; //no collision

		float diff = (pbx - pax) * nx + (pby - pay) * ny;
		float vecDiff = (vbx - vax) * nx + (vby - vay) * ny;

		float midpoint = vecDiff != 0 ? (diff + vecDiff) / vecDiff : 0f;

		float mxA = ax - vax * midpoint; //midpoint x for A
		float myA = ay - vay * midpoint; //midpoint y for A
		float mxB = bx - vbx * midpoint; //midpoint x for B
		float myB = by - vby * midpoint; //midpoint y for B

		float limitA1 = -ny * (mxA + hsA) + nx * (myA + hsA);
		float limitA2 = -ny * (mxA - hsA) + nx * (myA - hsA);
		float limitB1 = -ny * (mxB + hsB) + nx * (myB + hsB);
		float limitB2 = -ny * (mxB - hsB) + nx * (myB - hsB);

		float surface = min(max(limitA1, limitA2), max(limitB1, limitB2)) //minimum of the maximums
				- max(min(limitA1, limitA2), min(limitB1, limitB2)); //maximum of the minimums

		if(areEqual(surface, 0, epsilon))
		{
			float sizeDiff = (hsB + hsA) * nx + (hsB + hsA) * ny;

			midpoint = vecDiff != 0 ? (diff + vecDiff + sizeDiff) / vecDiff : 0f;

			mxA = ax - vax * midpoint; //midpoint x for A
			myA = ay - vay * midpoint; //midpoint y for A
			mxB = bx - vbx * midpoint; //midpoint x for B
			myB = by - vby * midpoint; //midpoint y for B

			limitA1 = -ny * (mxA + hsA) + nx * (myA + hsA);
			limitA2 = -ny * (mxA - hsA) + nx * (myA - hsA);
			limitB1 = -ny * (mxB + hsB) + nx * (myB + hsB);
			limitB2 = -ny * (mxB - hsB) + nx * (myB - hsB);

			surface = min(max(limitA1, limitA2), max(limitB1, limitB2)) //minimum of the maximums
					- max(min(limitA1, limitA2), min(limitB1, limitB2)); //maximum of the minimums

			if(isSmallerOrEqual(surface, 0, epsilon))
				return null;

			surface = 0f;
		}
		else if(surface < 0)
			return null;

		Collision collision = collisionPool.obtain();

		collision.normal.set(nx, ny);
		collision.penetration = -((bx - ax) * nx + (by - ay) * ny);

		collision.contactSurface = surface;

		return collision;
	}
}