package me.winter.boing.detection.detectors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.DynamicBody;
import me.winter.boing.detection.PooledDetector;
import me.winter.boing.shapes.Limit;

import static com.badlogic.gdx.math.MathUtils.FLOAT_ROUNDING_ERROR;
import static com.badlogic.gdx.math.Vector2.Zero;
import static java.lang.Math.max;
import static java.lang.Math.min;

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
		if(limitA.normal.dot(limitB.normal) + 1 > FLOAT_ROUNDING_ERROR)
			return null;

		Vector2 vecA = limitA.getBody() instanceof DynamicBody
					? ((DynamicBody)limitA.getBody()).getMovement()
					: Zero;

		Vector2 vecB = limitB.getBody() instanceof DynamicBody
					? ((DynamicBody)limitB.getBody()).getMovement()
					: Zero;

		float ax = limitA.getAbsX();
		float ay = limitA.getAbsY();
		float bx = limitB.getAbsX();
		float by = limitB.getAbsY();

		float nx = limitA.normal.x; //normal X
		float ny = limitA.normal.y; //normal Y

		if((ax * nx + ay * ny) - (bx * nx + by * ny) < -FLOAT_ROUNDING_ERROR) //if limitA after his velocity isn't after limitB with his velocity
			return null; //no collision

		float pax = ax - vecA.x; //previous x for A
		float pay = ay - vecA.y; //previous y for A
		float pbx = bx - vecB.x; //previous x for B
		float pby = by - vecB.y; //previous y for B

		if((pax * nx + pay * ny) - (pbx * nx + pby * ny) > FLOAT_ROUNDING_ERROR) //if limitA isn't before limitB
			return null; //no collision

		float diff = (pbx - pax) * nx + (pby - pay) * ny;
		float vecDiff = (vecB.x - vecA.x) * nx + (vecB.y - vecA.y) * ny;

		float midpoint = vecDiff != 0 ? (diff + vecDiff) / vecDiff : 0f;

		float mxA = ax - vecA.x * midpoint; //midpoint x for A
		float myA = ay - vecA.y * midpoint; //midpoint y for A
		float mxB = bx - vecB.x * midpoint; //midpoint x for B
		float myB = by - vecB.y * midpoint; //midpoint y for B

		float hsA = limitA.size / 2; //half size for A
		float hsB = limitB.size / 2; //half size for B

		float limitA1 = -ny * (mxA + hsA) + nx * (myA + hsA);
		float limitA2 = -ny * (mxA - hsA) + nx * (myA - hsA);
		float limitB1 = -ny * (mxB + hsB) + nx * (myB + hsB);
		float limitB2 = -ny * (mxB - hsB) + nx * (myB - hsB);

		float surface = min(max(limitA1, limitA2), max(limitB1, limitB2)) //minimum of the maximums
					- max(min(limitA1, limitA2), min(limitB1, limitB2)); //maximum of the minimums

		if(surface <= FLOAT_ROUNDING_ERROR)
		{
			float sizeDiff = (hsB + hsA) * nx + (hsB + hsA) * ny;

			midpoint = vecDiff != 0 ? (diff + vecDiff + sizeDiff) / vecDiff : 0f;

			mxA = ax - vecA.x * midpoint; //midpoint x for A
			myA = ay - vecA.y * midpoint; //midpoint y for A
			mxB = bx - vecB.x * midpoint; //midpoint x for B
			myB = by - vecB.y * midpoint; //midpoint y for B

			limitA1 = -ny * (mxA + hsA) + nx * (myA + hsA);
			limitA2 = -ny * (mxA - hsA) + nx * (myA - hsA);
			limitB1 = -ny * (mxB + hsB) + nx * (myB + hsB);
			limitB2 = -ny * (mxB - hsB) + nx * (myB - hsB);

			surface = min(max(limitA1, limitA2), max(limitB1, limitB2)) //minimum of the maximums
					- max(min(limitA1, limitA2), min(limitB1, limitB2)); //maximum of the minimums

			if(surface <= FLOAT_ROUNDING_ERROR)
				return null;

			surface = 0f;
		}

		Collision collision = collisionPool.obtain();

		collision.colliderA = limitA;
		collision.colliderB = limitB;

		collision.normalA.set(limitA.normal);
		collision.normalB.set(limitB.normal);
		collision.setImpactVelocities(limitA.getBody(), limitB.getBody());
		collision.penetration = -(diff + vecDiff);

		collision.contactSurface = surface;

		return collision;
	}
}
