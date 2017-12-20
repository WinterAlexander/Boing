package me.winter.boing.detection.continuous;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.World;
import me.winter.boing.colliders.Bound;
import me.winter.boing.detection.PooledDetector;

import static java.lang.Math.abs;
import static java.lang.Math.signum;
import static me.winter.boing.util.FloatUtil.DEFAULT_ULPS;
import static me.winter.boing.util.FloatUtil.areEqual;
import static me.winter.boing.util.FloatUtil.getGreatestULP;
import static me.winter.boing.util.FloatUtil.isGreaterOrEqual;
import static me.winter.boing.util.FloatUtil.isSmallerOrEqual;
import static me.winter.boing.util.FloatUtil.max;
import static me.winter.boing.util.FloatUtil.min;

/**
 * Detects collisions between 2 Bounds. This is the only
 * continous collision detector since bounds do not have any area.
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class BoundBoundDetector extends PooledDetector<Bound, Bound>
{
	public BoundBoundDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
	}

	@Override
	public Collision collides(World world, Bound boundA, Bound boundB)
	{
		//if(("" + boundA.getTag()).endsWith("_BOUND") && ("" + boundB.getTag()).endsWith("_BOUND"))
		//{
		//	System.console();
		//}

		if(!areEqual(boundA.normal.dot(boundB.normal), -1))
			return null;

		float normalX = boundA.normal.x; //normal X
		float normalY = boundA.normal.y; //normal Y

		//position of the bound
		float posAx = boundA.getAbsX();
		float posAy = boundA.getAbsY();

		//same for b
		float posBx = boundB.getAbsX();
		float posBy = boundB.getAbsY();

		Vector2 movA = boundA.getMovement(world);
		Vector2 movB = boundB.getMovement(world);

		//movement of the bodies seen from each other
		float vecAx = movA.x, vecAy = movA.y, vecBx = movB.x, vecBy = movB.y;

		Vector2 shiftA = boundA.getCollisionShifting(world);
		Vector2 shiftB = boundB.getCollisionShifting(world);

		//compare values to check if collision occurs, if the other is getting away from the first,
		// his velocity is subtracted to see if any collision could occur in the case where the one getting away gets pushed back on the first
		float compAx = posAx, compAy = posAy, compBx = posBx, compBy = posBy;

		//if the velocity is not going along the normal (going against the direction bound is pointing at)
		if(signum(normalX) != signum(vecAx))
			//remove the velocity to feel the other body as it isn't moving
			compAx -= vecAx;

		//per component
		if(signum(normalY) != signum(vecAy))
			compAy -= vecAy;

		//same for B, B's normal is the opposite of A
		if(signum(-normalX) != signum(vecBx))
			compBx -= vecBx;

		if(signum(-normalY) != signum(vecBy))
			compBy -= vecBy;

		//if collision shifting is going along it's normal
		if(signum(normalX) == signum(shiftA.x))
			//then expect it to be pushed to there this frame too
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

		float epsilon = DEFAULT_ULPS * getGreatestULP(posAx, posAy, posBx, posBy, vecAx, vecAy, vecBx, vecBy, boundA.size, boundB.size);

		//if boundB with his movement isn't after boundA with his movement
		//(aka the bounds are still facing each other after having moved)
		if(!isGreaterOrEqual(compAx * normalX + compAy * normalY, compBx * normalX + compBy * normalY, epsilon))
			return null;

		//'previous' position is assumed to be the current position minus
		//the fake movement we just assumed. This fake previous position is
		//used to make sure no collision drops by collision shitfing
		float prevAx = posAx - vecAx;
		float prevAy = posAy - vecAy;
		float prevBx = posBx - vecBx;
		float prevBy = posBy - vecBy;

		//if boundB isn't before boundA
		//(aka the bounds at previous positions are not even facing each other)
		if(!isSmallerOrEqual(prevAx * normalX + prevAy * normalY, prevBx * normalX + prevBy * normalY, epsilon)) //if boundB isn't before boundA
			return null; //no collision

		float hsizeA = boundA.size / 2; //half size for A
		float hsizeB = boundB.size / 2; //half size for B

		float diff = ((posBx - vecBx) - (posAx - vecAx)) * normalX + ((posBy - vecBy) - (posAy - vecAy)) * normalY;
		float vecDiff = (vecBx - vecAx) * normalX + (vecBy - vecAy) * normalY;

		float midpoint = vecDiff != 0 ? (diff + vecDiff) / vecDiff : 0f;

		float midAx = posAx - vecAx * midpoint; //midpoint x for A
		float midAy = posAy - vecAy * midpoint; //midpoint y for A
		float midBx = posBx - vecBx * midpoint; //midpoint x for B
		float midBy = posBy - vecBy * midpoint; //midpoint y for B

		//contact surface, used to detect if bounds are on the same plane and
		//to fullfill the collision report
		//get surface contact at mid point
		float surface = getContactSurface(midAx, midAy, hsizeA, midBx, midBy, hsizeB, normalX, normalY);

		//if 0, it might be a corner corner case
		if(!areEqual(surface, 0) && surface < 0)
			return null;

		Collision collision = collisionPool.obtain();

		collision.normal.set(normalX, normalY);

		collision.penetration = (cA, cB) -> getPenetration((Bound)cA, (Bound)cB);

		//boing v2 priority algorithm
		//collision.priority = surface * getPenetration(compAx, compAy, compBx, compBy, normalX, normalY);
		collision.priority = surface * getPenetration(boundA, boundB);
		//System.out.println(collision.priority);

		//re-get the position from the original calculation
		//since we are in a CollisionDynamicVariable, posAx, posAy etc. might
		//be outdated (cached values)
		collision.contactSurface = (cA, cB) -> getContactSurface((Bound)cA, (Bound)cB);

		collision.colliderA = boundA;
		collision.colliderB = boundB;
		collision.setImpactVelocities(boundA.getBody(), boundB.getBody());

		return collision;
	}

	public static float getPenetration(Bound boundA, Bound boundB)
	{
		return (boundA.getAbsX() - boundB.getAbsX()) * boundA.normal.x + (boundA.getAbsY() - boundB.getAbsY()) * boundA.normal.y;
	}

	public static float getPenetration(float ax, float ay, float bx, float by, float nx, float ny)
	{
		return (ax - bx) * nx + (ay - by) * ny;
	}

	public static float getContactSurface(Bound boundA, Bound boundB)
	{
		return getContactSurface(boundA.getAbsX(), boundA.getAbsY(), boundA.size / 2, boundB.getAbsX(), boundB.getAbsY(), boundB.size / 2, boundA.normal.x, boundA.normal.y);
	}

	/**
	 * Finds the contact surface between 2 bounds at position (ax, ay) and (bx, by)
	 *
	 * @param ax x position of bound A
	 * @param ay y position of bound A
	 * @param extentA half the size of bound A
	 *
	 * @param bx x position of bound B
	 * @param by y position of bound B
	 * @param extentB half size of bound B
	 *
	 * @param nx normal x of the bound A
	 * @param ny normal y of the bound A
	 *
	 * @return how much of their surface match in relation to the normal
	 */
	public static float getContactSurface(float ax, float ay, float extentA,
	                                      float bx, float by, float extentB,
	                                      float nx, float ny)
	{
		//we take the 2 extremities of the 2 bounds
		float boundA1 = ny * (ax + extentA) + nx * (ay + extentA);
		float boundA2 = ny * (ax - extentA) + nx * (ay - extentA);
		float boundB1 = ny * (bx + extentB) + nx * (by + extentB);
		float boundB2 = ny * (bx - extentB) + nx * (by - extentB);

		//yields the overlapping length
		return min(max(boundA1, boundA2), max(boundB1, boundB2)) //minimum of the maximums
				- max(min(boundA1, boundA2), min(boundB1, boundB2)); //maximum of the minimums
	}
}
