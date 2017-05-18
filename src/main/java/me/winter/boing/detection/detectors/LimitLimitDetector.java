package me.winter.boing.detection.detectors;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.detection.PooledDetector;
import me.winter.boing.shapes.Limit;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.signum;
import static me.winter.boing.util.FloatUtil.DEFAULT_ULPS;
import static me.winter.boing.util.FloatUtil.areEqual;
import static me.winter.boing.util.FloatUtil.isGreaterOrEqual;
import static me.winter.boing.util.FloatUtil.isSmallerOrEqual;

/**
 * Detects collisions between 2 Limits. This is the only
 * continous collision detector since limits do not have any area.
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class LimitLimitDetector extends PooledDetector<Limit, Limit>
{
	public LimitLimitDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
	}

	@Override
	public Collision collides(Limit limitA, Limit limitB)
	{
		if(!areEqual(limitA.normal.dot(limitB.normal), -1))
			return null;

		float csax = limitA.getCollisionShifting().x;
		float csay = limitA.getCollisionShifting().y;
		float csbx = limitB.getCollisionShifting().x;
		float csby = limitB.getCollisionShifting().y;

		float vax = limitA.getMovement().x + (signum(csax) == limitA.normal.x ? csax : 0);
		float vay = limitA.getMovement().y + (signum(csay) == limitA.normal.y ? csay : 0);
		float vbx = limitB.getMovement().x + (signum(csbx) == limitB.normal.x ? csbx : 0);
		float vby = limitB.getMovement().y + (signum(csby) == limitB.normal.y ? csby : 0);

		float epsilon = DEFAULT_ULPS * max(limitA.getPrecision(), limitB.getPrecision());

		float ax = limitA.getAbsX();
		float ay = limitA.getAbsY();
		float bx = limitB.getAbsX();
		float by = limitB.getAbsY();

		float nx = limitA.normal.x; //normal X
		float ny = limitA.normal.y; //normal Y

		if(!isGreaterOrEqual(ax * nx + ay * ny, bx * nx + by * ny, epsilon)) //if limitB with his velocity isn't after boxA with his velocity
			return null; //no collision

		float pax = ax - vax; //previous x for A
		float pay = ay - vay; //previous y for A
		float pbx = bx - vbx; //previous x for B
		float pby = by - vby; //previous y for B

		if(!isSmallerOrEqual(pax * nx + pay * ny, pbx * nx + pby * ny, epsilon)) //if limitB isn't before boxA
			return null; //no collision

		float diff = (pbx - pax) * nx + (pby - pay) * ny;
		float vecDiff = (vbx - vax) * nx + (vby - vay) * ny;

		float midpoint = vecDiff != 0 ? (diff + vecDiff) / vecDiff : 0f;

		float mxA = ax - vax * midpoint; //midpoint x for A
		float myA = ay - vay * midpoint; //midpoint y for A
		float mxB = bx - vbx * midpoint; //midpoint x for B
		float myB = by - vby * midpoint; //midpoint y for B

		float hsA = limitA.size / 2; //half size for A
		float hsB = limitB.size / 2; //half size for B

		float limitA1 = -ny * (mxA + hsA) + nx * (myA + hsA);
		float limitA2 = -ny * (mxA - hsA) + nx * (myA - hsA);
		float limitB1 = -ny * (mxB + hsB) + nx * (myB + hsB);
		float limitB2 = -ny * (mxB - hsB) + nx * (myB - hsB);

		float surface = min(max(limitA1, limitA2), max(limitB1, limitB2)) //minimum of the maximums
					- max(min(limitA1, limitA2), min(limitB1, limitB2)); //maximum of the minimums

		if(areEqual(surface, 0, epsilon))
		{
			float sizeDiff = (hsB + hsA) * abs(nx) + (hsB + hsA) * abs(ny);

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

			if(isSmallerOrEqual(surface, 0))
				return null;

			surface = 0f;
		}
		else if(surface < 0)
			return null;

		Collision collision = collisionPool.obtain();

		collision.colliderA = limitA;
		collision.colliderB = limitB;

		collision.normalA.set(limitA.normal);
		collision.normalB.set(limitB.normal);
		collision.setImpactVelocities(limitA.getBody(), limitB.getBody());
		collision.penetration = -((bx - ax) * nx + (by - ay) * ny);

		collision.contactSurface = surface;

		return collision;
	}
}
