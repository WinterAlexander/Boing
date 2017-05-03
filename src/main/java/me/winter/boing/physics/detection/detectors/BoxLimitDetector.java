package me.winter.boing.physics.detection.detectors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicBody;
import me.winter.boing.physics.detection.PooledDetector;
import me.winter.boing.physics.shapes.Box;
import me.winter.boing.physics.shapes.Limit;

import static com.badlogic.gdx.math.Vector2.Zero;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.signum;

/**
 * Detects collisions between an Axis Aligned Bounding Box and a Limit
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class BoxLimitDetector extends PooledDetector<Box, Limit>
{
	private Vector2 tmpVecA = new Vector2(), tmpVecB = new Vector2();

	public BoxLimitDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
	}

	@Override
	public Collision collides(Box boxA, Limit limitB)
	{
		Vector2 vecA = boxA.getBody() instanceof DynamicBody
				? ((DynamicBody)boxA.getBody()).getMovement()
				: Zero;

		Vector2 vecB = limitB.getBody() instanceof DynamicBody
				? ((DynamicBody)limitB.getBody()).getMovement()
				: Zero;



		float boxAbsX = boxA.getAbsX();
		float boxAbsY = boxA.getAbsY();
		float hsA;

		if(limitB.normal.x > limitB.normal.y)
		{
			boxAbsX += limitB.normal.x * boxA.width / 2;
			hsA = boxA.height / 2;
		}
		else
		{
			boxAbsY += limitB.normal.y * boxA.height / 2;
			hsA = boxA.width / 2;
		}

		tmpVecA.set(boxAbsX, boxAbsY);
		tmpVecB.set(limitB.getAbsX(), limitB.getAbsY());

		if(!(tmpVecA.x * limitB.normal.x + tmpVecA.y * limitB.normal.y >= tmpVecB.x * limitB.normal.x + tmpVecB.y * limitB.normal.y)) //if limitB after his velocity isn't after boxA with his velocity
			return null; //no collision

		tmpVecA.sub(vecA);
		tmpVecB.sub(vecB);

		float aDiff = (tmpVecA.x * limitB.normal.x + tmpVecA.y * limitB.normal.y) - (tmpVecB.x * limitB.normal.x + tmpVecB.y * limitB.normal.y);

		if(aDiff > 0) //if limitB isn't before boxA
			return null; //no collision

		float vdx = vecB.x - vecA.x;
		float vdy = vecB.y - vecA.y;

		float dx = limitB.getAbsX() - limitB.getAbsX() - vdx;
		float dy = limitB.getAbsY() - limitB.getAbsY() - vdy;

		float diff = dx * limitB.normal.x + dy * limitB.normal.y;
		float vecDiff = vdx * limitB.normal.x  + vdy * limitB.normal.y;

		tmpVecA.set(vecA).scl(-1f);
		tmpVecB.set(vecB).scl(-1f);

		if(vecDiff != 0)
		{
			//finding the collision point
			tmpVecA.scl(1f - (diff + vecDiff) / vecDiff);
			tmpVecB.scl(1f - (diff + vecDiff) / vecDiff);
		}

		float mxA = boxAbsX + tmpVecA.x; //midpoint for A
		float myA = boxAbsY + tmpVecA.y;
		float mxB = limitB.getAbsX() + tmpVecB.x; //midpoint for B
		float myB = limitB.getAbsY() + tmpVecB.y;

		float hsB = limitB.size / 2; //half size for B

		float limitA1 = -limitB.normal.y * (mxA + hsA) + limitB.normal.x * (myA + hsA);
		float limitA2 = -limitB.normal.y * (mxA - hsA) + limitB.normal.x * (myA - hsA);
		float limitB1 = -limitB.normal.y * (mxB + hsB) + limitB.normal.x * (myB + hsB);
		float limitB2 = -limitB.normal.y * (mxB - hsB) + limitB.normal.x * (myB - hsB);

		float surface = min(max(limitA1, limitA2), max(limitB1, limitB2)) //minimum of the maximums
				- max(min(limitA1, limitA2), min(limitB1, limitB2)); //maximum of the minimums

		if(surface <= 0)
			return null;

		Collision collision = collisionPool.obtain();

		collision.colliderA = boxA;
		collision.colliderB = limitB;

		collision.normalA.set(-limitB.normal.x, -limitB.normal.y);
		collision.normalB.set(limitB.normal);
		collision.setImpactVelocities(boxA.getBody(), limitB.getBody());
		collision.penetration = -(diff + vecDiff);
		collision.contactSurface = surface;

		return collision;
	}
}