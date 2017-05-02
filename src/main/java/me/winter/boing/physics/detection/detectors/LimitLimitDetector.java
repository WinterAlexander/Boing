package me.winter.boing.physics.detection.detectors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicBody;
import me.winter.boing.physics.detection.PooledDetector;
import me.winter.boing.physics.shapes.Limit;

import static com.badlogic.gdx.math.MathUtils.isEqual;
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
	private Vector2 tmpVecA = new Vector2(), tmpVecB = new Vector2();

	public LimitLimitDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
	}

	@Override
	public Collision collides(Limit limitA, Limit limitB)
	{
		if(limitA.normal.dot(limitB.normal) != -1)
			return null;

		Vector2 vecA = limitA.getBody() instanceof DynamicBody
					? ((DynamicBody)limitA.getBody()).getMovement()
					: Zero;

		Vector2 vecB = limitB.getBody() instanceof DynamicBody
					? ((DynamicBody)limitB.getBody()).getMovement()
					: Zero;


		tmpVecA.set(limitA.getAbsX(), limitA.getAbsY());
		tmpVecB.set(limitB.getAbsX(), limitB.getAbsY());

		if(!(tmpVecA.x * limitA.normal.x + tmpVecA.y * limitA.normal.y > tmpVecB.x * limitA.normal.x + tmpVecB.y * limitA.normal.y)) //if limitA after his velocity isn't after limitB with his velocity
			return null; //no collision

		tmpVecA.sub(vecA);
		tmpVecB.sub(vecB);

		float aDiff = (tmpVecA.x * limitA.normal.x + tmpVecA.y * limitA.normal.y) - (tmpVecB.x * limitA.normal.x + tmpVecB.y * limitA.normal.y);

		if(aDiff > 0) //if limitA isn't before limitB
			return null; //no collision

		float vdx = vecB.x - vecA.x;
		float vdy = vecB.y - vecA.y;

		float dx = limitB.getAbsX() - limitA.getAbsX() - vdx;
		float dy = limitB.getAbsY() - limitA.getAbsY() - vdy;

		float diff = dx * limitA.normal.x + dy * limitA.normal.y;
		float vecDiff = vdx * limitA.normal.x  + vdy * limitA.normal.y;

		tmpVecA.set(vecA).scl(-1f);
		tmpVecB.set(vecB).scl(-1f);

		if(vecDiff != 0)
		{
			//finding the collision point
			tmpVecA.scl(1f - (diff + vecDiff) / vecDiff);
			tmpVecB.scl(1f - (diff + vecDiff) / vecDiff);
		}

		float mxA = limitA.getAbsX() + tmpVecA.x; //midpoint for A
		float myA = limitA.getAbsY() + tmpVecA.y;
		float mxB = limitB.getAbsX() + tmpVecB.x; //midpoint for B
		float myB = limitB.getAbsY() + tmpVecB.y;

		float hsA = limitA.size / 2; //half size for A
		float hsB = limitB.size / 2; //half size for B

		float limitA1 = -limitA.normal.y * (mxA + hsA) + limitA.normal.x * (myA + hsA);
		float limitA2 = -limitA.normal.y * (mxA - hsA) + limitA.normal.x * (myA - hsA);
		float limitB1 = -limitA.normal.y * (mxB + hsB) + limitA.normal.x * (myB + hsB);
		float limitB2 = -limitA.normal.y * (mxB - hsB) + limitA.normal.x * (myB - hsB);

		float surface = min(max(limitA1, limitA2), max(limitB1, limitB2)) //minimum of the maximums
					- max(min(limitA1, limitA2), min(limitB1, limitB2)); //maximum of the minimums

		if(surface <= 0)
			return null;

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
