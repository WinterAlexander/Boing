package me.winter.boing.detection.continuous;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.World;
import me.winter.boing.detection.PooledDetector;
import me.winter.boing.colliders.Box;
import me.winter.boing.colliders.Limit;

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
 * Detects collisions between an Axis Aligned Bounding Box and a Limit
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class BoxLimitDetector extends PooledDetector<Box, Limit>
{
	public BoxLimitDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
	}

	@Override
	public Collision collides(World world, Box boxA, Limit limitB)
	{
		float nx = -limitB.normal.x; //normal X
		float ny = -limitB.normal.y; //normal Y

		float ax = boxA.getAbsX() + nx * boxA.width / 2; //extends to side
		float ay = boxA.getAbsY() + ny * boxA.height / 2; //extends to side
		float bx = limitB.getAbsX();
		float by = limitB.getAbsY();

		float vax = boxA.getMovement(world).x;
		float vay = boxA.getMovement(world).y;
		float vbx = limitB.getMovement(world).x;
		float vby = limitB.getMovement(world).y;

		float epsilon = DEFAULT_ULPS * getGreatestULP(ax, ay, bx, by, vax, vay, vbx, vby, boxA.width, boxA.height, limitB.size);

		if(!isGreaterOrEqual(ax * nx + ay * ny, bx * nx + by * ny, epsilon)) //if limitB with his velocity isn't after boxA with his velocity
			return null; //no collision

		if(dot(nx, ny, boxA.getCollisionShifting(world).x, boxA.getCollisionShifting(world).y) > 0)
		{
			vax += boxA.getCollisionShifting(world).x;
			vay += boxA.getCollisionShifting(world).y;
		}

		if(limitB.normal.dot(limitB.getCollisionShifting(world)) > 0)
		{
			vbx += limitB.getCollisionShifting(world).x;
			vby += limitB.getCollisionShifting(world).y;
		}

		float pax = ax - vax;
		float pay = ay - vay;
		float pbx = bx - vbx;
		float pby = by - vby;

		if(!isSmallerOrEqual(pax * nx + pay * ny, pbx * nx + pby * ny, epsilon)) //if limitB isn't before boxA
			return null; //no collision

		float diff = (pbx - pax) * nx + (pby - pay) * ny;
		float vecDiff = (vbx - vax) * nx + (vby - vay) * ny;

		float midpoint = vecDiff != 0 ? (diff + vecDiff) / vecDiff : 0f;

		float mxA = ax - vax * midpoint; //midpoint x for A
		float myA = ay - vay * midpoint; //midpoint y for A
		float mxB = bx - vbx * midpoint; //midpoint x for B
		float myB = by - vby * midpoint; //midpoint y for B

		float hsA = nx * boxA.height / 2 + ny * boxA.width / 2; //half size of A (as a Limit)
		float hsB = limitB.size / 2; //half size for B

		float limitA1 = -ny * (mxA + hsA) + nx * (myA + hsA);
		float limitA2 = -ny * (mxA - hsA) + nx * (myA - hsA);
		float limitB1 = -ny * (mxB + hsB) + nx * (myB + hsB);
		float limitB2 = -ny * (mxB - hsB) + nx * (myB - hsB);

		final float surface = min(max(limitA1, limitA2), max(limitB1, limitB2)) //minimum of the maximums
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

			float surface2 = min(max(limitA1, limitA2), max(limitB1, limitB2)) //minimum of the maximums
					- max(min(limitA1, limitA2), min(limitB1, limitB2)); //maximum of the minimums

			if(isSmallerOrEqual(surface2, 0, epsilon))
				return null;
		}
		else if(surface < 0)
			return null;

		Collision collision = collisionPool.obtain();

		collision.colliderA = boxA;
		collision.colliderB = limitB;

		collision.normal.set(nx, ny);
		collision.setImpactVelocities(boxA.getBody(), limitB.getBody());
		collision.penetration = () -> -((bx - ax) * nx + (by - ay) * ny); //TODO

		collision.contactSurface = () -> surface; //TODO

		return collision;
	}
}