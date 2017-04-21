package me.winter.boing.physics.detection.detectors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.detection.PooledDetector;
import me.winter.boing.physics.shapes.Limit;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static com.badlogic.gdx.math.Vector2.Zero;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class LimitLimitDetector extends PooledDetector<Limit, Limit>
{
	private Vector2 tmpVecA = new Vector2(), tmpVecB = new Vector2();

	public LimitLimitDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
	}

	@Override
	public Collision collides(Limit shapeA, Limit shapeB)
	{
		if(shapeA.normal.dot(shapeB.normal) >= 0)
			return null;

		Vector2 vecA = shapeA.getSolid() instanceof DynamicSolid
					? ((DynamicSolid)shapeA.getSolid()).getMovement()
					: Zero;

		Vector2 vecB = shapeB.getSolid() instanceof DynamicSolid
					? ((DynamicSolid)shapeB.getSolid()).getMovement()
					: Zero;


		/*
			Use the sign of the determinant of vectors (AB,AM), where M(X,Y) is the query point:

			position = sign((Bx - Ax) * (Y - Ay) - (By - Ay) * (X - Ax))
			It is 0 on the line, and +1 on one side, -1 on the other side.
		*/
		float side = shapeA.normal.x * -shapeB.normal.y - shapeA.normal.y * -shapeB.normal.x;

		Vector2 closestA, farthestA, closestB, farthestB;

		if(side < 0)
		{
			closestA = shapeA.getPoint1();
			farthestA = shapeA.getPoint2();
			closestB = shapeB.getPoint2();
			farthestB = shapeB.getPoint1();
		}
		else
		{
			closestA = shapeA.getPoint2();
			farthestA = shapeA.getPoint1();
			closestB = shapeB.getPoint1();
			farthestB = shapeB.getPoint2();
		}

		tmpVecA.set(farthestA).sub(vecA).scl(shapeA.normal);
		tmpVecB.set(farthestB).sub(vecB).scl(shapeA.normal);

		if(!isBefore(tmpVecA, tmpVecB)) //if limitA isn't before limitB
			return null; //no collision

		tmpVecA.set(closestA).scl(shapeA.normal);
		tmpVecB.set(closestB).scl(shapeA.normal);

		if(!isAfter(tmpVecA, tmpVecB)) //if limitA after his velocity isn't after limitB with his velocity
			return null; //no collision


		float dx = closestB.x - closestA.x;
		float dy = closestB.y - closestA.y;

		float vdx = vecB.x - vecA.x;
		float vdy = vecB.y - vecA.y;

		float diff = dx * shapeA.normal.x + dy * shapeA.normal.y;
		float vecDiff = vdx * shapeA.normal.x  + vdy * shapeA.normal.y;

		tmpVecA.set(vecA);
		tmpVecB.set(vecB);

		if(vecDiff != 0)
		{
			//finding the collision point
			tmpVecA.scl((diff + vecDiff) / vecDiff);
			tmpVecB.scl((diff + vecDiff) / vecDiff);
		}

		if(!contains(shapeA, shapeB, tmpVecA, tmpVecB)) //if it wasn't in bounds at the impact point
		{
			/*
			//finding the collision point + 1 (to prevent the corner glitch)
			divide(tmpVecA.scl(diff.x + shapeA.normal.x, diff.y + shapeA.normal.y), diff);
			divide(tmpVecB.scl(diff.x + shapeB.normal.x, diff.y + shapeB.normal.y), diff);

			if(!contains(shapeA, shapeB, tmpVecA, tmpVecB))*/
				return null;
		}

		Collision collision = collisionPool.obtain();

		collision.colliderA = shapeA;
		collision.colliderB = shapeB;

		collision.normalA.set(shapeA.normal);
		collision.normalB.set(shapeB.normal);
		collision.setImpactVelocities(shapeA.getSolid(), shapeB.getSolid());
		collision.penetration = -diff;

		return collision;
	}

	private boolean isBefore(Vector2 posA, Vector2 posB)
	{
		return posA.x + posA.y < posB.x + posB.y || isEqual(posA.x + posA.y, posB.x + posB.y);
	}

	private boolean isAfter(Vector2 posA, Vector2 posB)
	{
		return posA.x + posA.y > posB.x + posB.y;
	}

	private boolean contains(Limit limitA, Limit limitB, Vector2 offsetA, Vector2 offsetB)
	{
		Vector2 p1A = limitA.getPoint1();
		Vector2 p2A = limitA.getPoint2();
		Vector2 p1B = limitB.getPoint1();
		Vector2 p2B = limitB.getPoint2();

		float limitA1 = -limitA.normal.y * (p1A.x + offsetA.x) + limitA.normal.x * (p1A.y + offsetA.y);
		float limitA2 = -limitA.normal.y * (p2A.x + offsetA.x) + limitA.normal.x * (p2A.y + offsetA.y);

		float limitB1 = -limitA.normal.y * (p1B.x + offsetB.x) + limitA.normal.x * (p1B.y + offsetB.y);
		float limitB2 = -limitA.normal.y * (p2B.x + offsetB.x) + limitA.normal.x * (p2B.y + offsetB.y);

		if(max(limitA1, limitA2) <= min(limitB1, limitB2))
			return false;

		if(min(limitA1, limitA2) >= max(limitB1, limitB2))
			return false;

		return true;
	}
}
