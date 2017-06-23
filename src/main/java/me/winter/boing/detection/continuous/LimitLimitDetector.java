package me.winter.boing.detection.continuous;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.World;
import me.winter.boing.colliders.Limit;
import me.winter.boing.detection.PooledDetector;
import me.winter.boing.util.DynamicFloat;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static me.winter.boing.util.FloatUtil.DEFAULT_ULPS;
import static me.winter.boing.util.FloatUtil.areEqual;
import static me.winter.boing.util.FloatUtil.getGreatestULP;
import static me.winter.boing.util.FloatUtil.isGreaterOrEqual;
import static me.winter.boing.util.FloatUtil.isSmallerOrEqual;
import static me.winter.boing.util.FloatUtil.max;

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
	public Collision collides(World world, Limit limitA, Limit limitB)
	{
		if(!areEqual(limitA.normal.dot(limitB.normal), -1))
			return null;

		final float ax = limitA.getAbsX();
		final float ay = limitA.getAbsY();
		final float bx = limitB.getAbsX();
		final float by = limitB.getAbsY();

		final float nx = limitA.normal.x; //normal X
		final float ny = limitA.normal.y; //normal Y

		final float vax, vay, vbx, vby;

		if(limitA.normal.dot(limitA.getCollisionShifting(world)) > 0)
		{
			vax = limitA.getMovement(world).x + limitA.getCollisionShifting(world).x;
			vay = limitA.getMovement(world).y + limitA.getCollisionShifting(world).y;
		}
		else
		{
			vax = limitA.getMovement(world).x;
			vay = limitA.getMovement(world).y;
		}

		if(limitB.normal.dot(limitB.getCollisionShifting(world)) > 0)
		{
			vbx = limitB.getMovement(world).x + limitB.getCollisionShifting(world).x;
			vby = limitB.getMovement(world).y + limitB.getCollisionShifting(world).y;
		}
		else
		{
			vbx = limitB.getMovement(world).x;
			vby = limitB.getMovement(world).y;
		}

		float epsilon = DEFAULT_ULPS * getGreatestULP(ax, ay, bx, by, vax, vay, vbx, vby, limitA.size, limitB.size);

		if(!isGreaterOrEqual(ax * nx + ay * ny, bx * nx + by * ny, epsilon)) //if limitB with his velocity isn't after limitA with his velocity
			return null; //no collision


		final float pax = ax - vax; //previous x for A
		final float pay = ay - vay; //previous y for A
		final float pbx = bx - vbx; //previous x for B
		final float pby = by - vby; //previous y for B

		if(!isSmallerOrEqual(pax * nx + pay * ny, pbx * nx + pby * ny, epsilon)) //if limitB isn't before limitA
			return null; //no collision

		final float hsA = limitA.size / 2; //half size for A
		final float hsB = limitB.size / 2; //half size for

		final float diff = (pbx - pax) * nx + (pby - pay) * ny;
		final float vecDiff = (vbx - vax) * nx + (vby - vay) * ny;

		DynamicFloat surfaceFormula = () -> {

			float midpoint = vecDiff != 0 ? (diff + vecDiff) / vecDiff : 0f;

			float mxA = limitA.getAbsX() - vax * midpoint; //midpoint x for A
			float myA = limitA.getAbsY() - vay * midpoint; //midpoint y for A
			float mxB = limitB.getAbsX() - vbx * midpoint; //midpoint x for B
			float myB = limitB.getAbsY() - vby * midpoint; //midpoint y for B

			float limitA1 = -ny * (mxA + hsA) + nx * (myA + hsA);
			float limitA2 = -ny * (mxA - hsA) + nx * (myA - hsA);
			float limitB1 = -ny * (mxB + hsB) + nx * (myB + hsB);
			float limitB2 = -ny * (mxB - hsB) + nx * (myB - hsB);

			return min(max(limitA1, limitA2), max(limitB1, limitB2)) //minimum of the maximums
					- max(min(limitA1, limitA2), min(limitB1, limitB2)); //maximum of the minimums
		};

		float surface = surfaceFormula.getValue();

		if(areEqual(surface, 0, epsilon))
		{
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

			surface = min(max(limitA1, limitA2), max(limitB1, limitB2)) //minimum of the maximums
					- max(min(limitA1, limitA2), min(limitB1, limitB2)); //maximum of the minimums

			if(isSmallerOrEqual(surface, 0, epsilon))
				return null;
		}
		else if(surface < 0)
			return null;

		Collision collision = collisionPool.obtain();

		collision.colliderA = limitA;
		collision.colliderB = limitB;

		collision.normal.set(limitA.normal);
		collision.setImpactVelocities(limitA.getBody(), limitB.getBody());
		collision.penetration = () -> {
			return -((limitB.getAbsX() - limitA.getAbsX()) * nx + (limitB.getAbsY() - limitA.getAbsY()) * ny); //TODO
		};


		collision.priority = diff / vecDiff;

		collision.contactSurface = surfaceFormula;

		return collision;
	}
}
