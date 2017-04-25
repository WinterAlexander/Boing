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
		if(shapeA.normal.dot(shapeB.normal) != -1)
			return null;

		Vector2 vecA = shapeA.getSolid() instanceof DynamicSolid
					? ((DynamicSolid)shapeA.getSolid()).getMovement()
					: Zero;

		Vector2 vecB = shapeB.getSolid() instanceof DynamicSolid
					? ((DynamicSolid)shapeB.getSolid()).getMovement()
					: Zero;


		tmpVecA.set(shapeA.getAbsX(), shapeA.getAbsY());
		tmpVecB.set(shapeB.getAbsX(), shapeB.getAbsY());

		if(!(tmpVecA.x * shapeA.normal.x + tmpVecA.y * shapeA.normal.y > tmpVecB.x * shapeA.normal.x + tmpVecB.y * shapeA.normal.y)) //if limitA after his velocity isn't after limitB with his velocity
			return null; //no collision

		tmpVecA.sub(vecA);
		tmpVecB.sub(vecB);

		float aDiff = (tmpVecA.x * shapeA.normal.x + tmpVecA.y * shapeA.normal.y) - (tmpVecA.x * shapeA.normal.x + tmpVecA.y * shapeA.normal.y);

		if(aDiff > 0) //if limitA isn't before limitB
			return null; //no collision

		float dx = shapeB.getAbsX() - shapeA.getAbsX();
		float dy = shapeB.getAbsY() - shapeA.getAbsY();

		float vdx = vecB.x - vecA.x;
		float vdy = vecB.y - vecA.y;

		float diff = dx * shapeA.normal.x + dy * shapeA.normal.y;
		float vecDiff = vdx * shapeA.normal.x  + vdy * shapeA.normal.y;

		tmpVecA.set(vecA).scl(-1f);
		tmpVecB.set(vecB).scl(-1f);

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

	private boolean contains(Limit limitA, Limit limitB, Vector2 offsetA, Vector2 offsetB)
	{
		float limitA1 = -limitA.normal.y * (limitA.getAbsX() + offsetA.x) + limitA.normal.x * (limitA.getAbsY() + offsetA.y);
		float limitA2 = -limitA.normal.y * (limitA.getAbsX() + offsetA.x) + limitA.normal.x * (limitA.getAbsY() + offsetA.y);

		float limitB1 = -limitA.normal.y * (limitB.getAbsX() + offsetB.x) + limitA.normal.x * (limitB.getAbsY() + offsetB.y);
		float limitB2 = -limitA.normal.y * (limitB.getAbsX() + offsetB.x) + limitA.normal.x * (limitB.getAbsY() + offsetB.y);

		return !(max(limitA1, limitA2) <= min(limitB1, limitB2) || min(limitA1, limitA2) >= max(limitB1, limitB2));
	}
}
