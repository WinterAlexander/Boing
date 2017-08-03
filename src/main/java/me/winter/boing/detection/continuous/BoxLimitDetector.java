package me.winter.boing.detection.continuous;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.World;
import me.winter.boing.colliders.Box;
import me.winter.boing.colliders.Limit;
import me.winter.boing.detection.PooledDetector;

import static com.badlogic.gdx.math.Vector2.dot;
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.signum;
import static me.winter.boing.detection.continuous.BoxBoxDetector.getContactSurface;
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
		float normalX = -limitB.normal.x; //normal X
		float normalY = -limitB.normal.y; //normal Y

		//position of the limit (position of box + extend of the box)
		float posAx = boxA.getAbsX() + normalX * boxA.width / 2; //extends to side
		float posAy = boxA.getAbsY() + normalY * boxA.height / 2; //extends to side

		//b is simply a limit
		float posBx = limitB.getAbsX();
		float posBy = limitB.getAbsY();

		Vector2 movA = boxA.getMovement(world);
		Vector2 movB = limitB.getMovement(world);

		//movement of the bodies seen from each other
		float vecAx = movA.x, vecAy = movA.y, vecBx = movB.x, vecBy = movB.y;

		Vector2 shiftA = boxA.getCollisionShifting(world);
		Vector2 shiftB = limitB.getCollisionShifting(world);

		//if collision shifting of A is going along it's normal
		if(signum(normalX) == signum(shiftA.x))
			//then expect it to be pushed to there this frame to
			//(we assume its getting pushed by something)
			vecAx += shiftA.x;
		//else, collision shifting is going in the other direction
		//ignoring it is safer since we don't know for sure if
		//it will get pushed this frame too or if the pusher is B
		//(in the case were the pusher is B, this collision must happen
		//for the pushing not to drop)

		//collision shifting is per component
		if(signum(normalY) == signum(shiftA.y))
			vecAy += shiftA.y;

		//same for B, B's normal is the opposite of A
		if(signum(-normalX) == signum(shiftB.x))
			vecBx += shiftB.x;

		if(signum(-normalY) == signum(shiftB.y))
			vecBy += shiftB.y;

		float epsilon = DEFAULT_ULPS * getGreatestULP(posAx, posAy, posBx, posBy, vecAx, vecAy, vecBx, vecBy, boxA.width, boxA.height, limitB.size);

		if(!isGreaterOrEqual(posAx * normalX + posAy * normalY, posBx * normalX + posBy * normalY, epsilon)) //if limitB with his velocity isn't after boxA with his velocity
			return null; //no collision

		//'previous' position is assumed to be the current position minus
		//the fake movement we just assumed. This fake previous position is
		//used to make sure no collision drops by collision shitfing
		float prevAx = posAx - vecAx;
		float prevAy = posAy - vecAy;
		float prevBx = posBx - vecBx;
		float prevBy = posBy - vecBy;

		if(!isSmallerOrEqual(prevAx * normalX + prevAy * normalY, prevBx * normalX + prevBy * normalY, epsilon)) //if limitB isn't before boxA
			return null; //no collision

		//half the size for A and B, used in further calculations
		float hsizeA = abs(normalX * boxA.height / 2 + normalY * boxA.width / 2); //half size for A
		float hsizeB = limitB.size / 2; //half size for B

		float diff = ((posBx - vecBx) - (posAx - vecAx)) * normalX + ((posBy - vecBy) - (posAy - vecAy)) * normalY;
		float vecDiff = (vecBx - vecAx) * normalX + (vecBy - vecAy) * normalY;

		float midpoint = vecDiff != 0 ? (diff + vecDiff) / vecDiff : 0f;

		float midAx = posAx - vecAx * midpoint; //midpoint x for A
		float midAy = posAy - vecAy * midpoint; //midpoint y for A
		float midBx = posBx - vecBx * midpoint; //midpoint x for B
		float midBy = posBy - vecBy * midpoint; //midpoint y for B

		//contact surface, used to detect if limits are on the same plane and
		//to fullfill the collision report
		//get surface contact at mid point
		float surface = BoxBoxDetector.getContactSurface(midAx, midAy, hsizeA, midBx, midBy, hsizeB, normalX, normalY);

		//if 0, it might be a corner corner case
		if(!areEqual(surface, 0) && surface < 0)
			return null;

		Collision collision = collisionPool.obtain();

		collision.penetration = (colliderA, colliderB) -> getPenetration((Box)colliderA, (Limit)colliderB);
		collision.contactSurface = (colliderA, colliderB) -> getContactSurface((Box)colliderA, (Limit)colliderB);

		//boing v2 priority algorithm
		collision.priority = surface * getPenetration(boxA, limitB);
		collision.normal.set(normalX, normalY);

		collision.colliderA = boxA;
		collision.colliderB = limitB;
		collision.setImpactVelocities(boxA.getBody(), limitB.getBody());

		return collision;
	}


	public static float getPenetration(Box boxA, Limit limitB)
	{
		return limitB.normal.x * (limitB.getAbsX() - boxA.getAbsX() + limitB.normal.x * boxA.width / 2)
				+ limitB.normal.y * (limitB.getAbsY() - boxA.getAbsY() + limitB.normal.y * boxA.height / 2);
	}

	public static float getContactSurface(Box boxA, Limit limitB)
	{
		float normalX = -limitB.normal.x;
		float normalY = -limitB.normal.y;

		float hsizeA = abs(normalX * boxA.height / 2 + normalY * boxA.width / 2); //half size for A
		float hsizeB = limitB.size / 2; //half size for B

		float newAx = boxA.getAbsX() + normalX * boxA.width / 2;
		float newAy = boxA.getAbsY() + normalY * boxA.height / 2;
		float newBx = limitB.getAbsX();
		float newBy = limitB.getAbsY();

		return BoxBoxDetector.getContactSurface(newAx, newAy, hsizeA, newBx, newBy, hsizeB, normalX, normalY);
	}
}