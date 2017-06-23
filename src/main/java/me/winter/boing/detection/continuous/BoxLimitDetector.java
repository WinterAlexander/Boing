package me.winter.boing.detection.continuous;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.World;
import me.winter.boing.colliders.Box;
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
		final float nx = -limitB.normal.x; //normal X
		final float ny = -limitB.normal.y; //normal Y

		float ax = boxA.getAbsX() + nx * boxA.width / 2; //extends to side
		float ay = boxA.getAbsY() + ny * boxA.height / 2; //extends to side
		float bx = limitB.getAbsX();
		float by = limitB.getAbsY();

		final float vax, vay, vbx, vby;

		final Vector2 csA = boxA.getCollisionShifting(world);
		final Vector2 vecA = boxA.getMovement(world);

		if(Vector2.dot(nx, ny, csA.x, csA.y) > 0)
		{
			vax = vecA.x + csA.x;
			vay = vecA.y + csA.y;
		}
		else
		{
			vax = vecA.x;
			vay = vecA.y;
		}

		final Vector2 csB = limitB.getCollisionShifting(world);
		final Vector2 vecB = limitB.getMovement(world);

		if(limitB.normal.dot(limitB.getCollisionShifting(world)) > 0)
		{
			vbx = vecB.x + csB.x;
			vby = vecB.y + csB.y;
		}
		else
		{
			vbx = vecB.x;
			vby = vecB.y;
		}

		float epsilon = DEFAULT_ULPS * getGreatestULP(ax, ay, bx, by, vax, vay, vbx, vby, boxA.width, boxA.height, limitB.size);

		if(!isGreaterOrEqual(ax * nx + ay * ny, bx * nx + by * ny, epsilon)) //if limitB with his velocity isn't after boxA with his velocity
			return null; //no collision

		final float pax = ax - vax;
		final float pay = ay - vay;
		final float pbx = bx - vbx;
		final float pby = by - vby;

		if(!isSmallerOrEqual(pax * nx + pay * ny, pbx * nx + pby * ny, epsilon)) //if limitB isn't before boxA
			return null; //no collision

		final float hsA = abs(nx * boxA.height / 2 + ny * boxA.width / 2); //half size for A
		final float hsB = limitB.size / 2; //half size for B

		final float diff = (pbx - pax) * nx + (pby - pay) * ny;
		final float vecDiff = (vbx - vax) * nx + (vby - vay) * ny;

		DynamicFloat surfaceFormula = () -> {

			float midpoint = vecDiff != 0 ? (diff + vecDiff) / vecDiff : 0f;

			float mxA = boxA.getAbsX() + nx * boxA.width / 2 - vax * midpoint; //midpoint x for A
			float myA = boxA.getAbsY() + ny * boxA.height / 2 - vay * midpoint; //midpoint y for A
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

		collision.colliderA = boxA;
		collision.colliderB = limitB;

		collision.normal.set(nx, ny);
		collision.setImpactVelocities(boxA.getBody(), limitB.getBody());
		collision.penetration = () -> -((limitB.getAbsX() - (boxA.getAbsX() + nx * boxA.width / 2)) * nx + (limitB.getAbsY() - (boxA.getAbsY() + ny * boxA.height / 2)) * ny);

		collision.contactSurface = surfaceFormula;

		collision.priority = diff / vecDiff;

		return collision;
	}
}