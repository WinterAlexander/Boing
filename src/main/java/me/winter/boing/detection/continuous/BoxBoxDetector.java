package me.winter.boing.detection.continuous;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.World;
import me.winter.boing.colliders.Box;
import me.winter.boing.detection.PooledDetector;
import me.winter.boing.CollisionDynamicVariable;

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
 * Detects collisions between 2 Axis Aligned Bounding Box
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class BoxBoxDetector extends PooledDetector<Box, Box>
{
	//private me.winter.boing.detection.simple.BoxBoxDetector simple;

	private CollisionDynamicVariable[] penetrationFormulas = new CollisionDynamicVariable[4];
	private CollisionDynamicVariable[] contactSurfaceFormulas = new CollisionDynamicVariable[4];

	public BoxBoxDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
		//simple = new me.winter.boing.detection.simple.BoxBoxDetector(collisionPool);

		penetrationFormulas[keyOf(1f, 0f)] = (colliderA, colliderB) -> getPenetration((Box)colliderA, (Box)colliderB, 1f, 0f);
		penetrationFormulas[keyOf(0f, 1f)] = (colliderA, colliderB) -> getPenetration((Box)colliderA, (Box)colliderB, 0f, 1f);
		penetrationFormulas[keyOf(-1f, 0f)] = (colliderA, colliderB) -> getPenetration((Box)colliderA, (Box)colliderB, -1f, 0f);
		penetrationFormulas[keyOf(0f, -1f)] = (colliderA, colliderB) -> getPenetration((Box)colliderA, (Box)colliderB, 0f, -1f);

		contactSurfaceFormulas[keyOf(1f, 0f)] = (colliderA, colliderB) -> getContactSurface((Box)colliderA, (Box)colliderB, 1f, 0f);
		contactSurfaceFormulas[keyOf(0f, 1f)] = (colliderA, colliderB) -> getContactSurface((Box)colliderA, (Box)colliderB, 0f, 1f);
		contactSurfaceFormulas[keyOf(-1f, 0f)] = (colliderA, colliderB) -> getContactSurface((Box)colliderA, (Box)colliderB, -1f, 0f);
		contactSurfaceFormulas[keyOf(0f, -1f)] = (colliderA, colliderB) -> getContactSurface((Box)colliderA, (Box)colliderB, 0f, -1f);
	}

	@Override
	public Collision collides(World world, Box boxA, Box boxB)
	{
		Vector2 vecA = boxA.getMovement(world); //movement of this frame
		Vector2 vecB = boxB.getMovement(world);

		Vector2 shiftA = boxA.getCollisionShifting(world); //collision shifting of previous frame, for various special continuous detection
		Vector2 shiftB = boxB.getCollisionShifting(world);

		float posAx = boxA.getAbsX(); //position AFTER movement
		float posAy = boxA.getAbsY();
		float posBx = boxB.getAbsX(); //same for b, both already have their movement added to position
		float posBy = boxB.getAbsY();

		//precision for all operations
		float epsilon = DEFAULT_ULPS * getGreatestULP(posAx, posAy, posBx, posBy, vecA.x, vecA.y, vecB.x, vecB.y, boxA.width, boxA.height, boxB.width, boxB.height);

		//checks if there's a collision for normal x=0, y=1
		Collision collision = collides(boxA, vecA, shiftA, boxB, vecB, shiftB, epsilon, 0, 1);

		if(collision == null) //if not, check if there's one in the opposite direction
			collision = collides(boxA, vecA, shiftA, boxB, vecB, shiftB, epsilon, 0, -1);

		//checks if there's a collision for normal x=1, y=0
		Collision coll2 = collides(boxA, vecA, shiftA, boxB, vecB, shiftB, epsilon, 1, 0);

		if(coll2 == null) //if not, check if opposite direction
			coll2 = collides(boxA, vecA, shiftA, boxB, vecB, shiftB, epsilon, -1, 0);

		if(coll2 != null)
		{
			if(collision == null)
				collision = coll2;
			else //if there's a collision for both x and y (TODO is this corner corner ?)
			{
				if(collision.priority <= coll2.priority) //take the one with higher priority
					collision = coll2;
			}
		}

		return collision;
	}

	/**
	 * Checks for collision for a specific side of the 2 boxes (check for collisions as bound)
	 *
	 * @param boxA collider A
	 * @param movA movement of body A
	 * @param shiftA collision shifting of body A
	 *
	 * @param boxB collider B
	 * @param movB movement of body B
	 * @param shiftB collision shifting of body B
	 *
	 * @param epsilon precision to use for float comparisons
	 * @param normalX x normal of the collision (normal of surface for boxA)
	 * @param normalY y normal of the collision (normal of surface for boxA)
	 *
	 * @return Collision is a collision occurs, otherwise null
	 */
	public Collision collides(final Box boxA, final Vector2 movA, final Vector2 shiftA,
	                          final Box boxB, final Vector2 movB, final Vector2 shiftB,
	                          final float epsilon, final float normalX, final float normalY)
	{
		//position of the bound (position of box + extend of the box)
		float posAx = boxA.getAbsX() + normalX * boxA.width / 2;
		float posAy = boxA.getAbsY() + normalY * boxA.height / 2;

		//same for b
		float posBx = boxB.getAbsX() - normalX * boxB.width / 2;
		float posBy = boxB.getAbsY() - normalY * boxB.height / 2;


		//movement of the bodies seen from each other
		float vecAx = movA.x, vecAy = movA.y, vecBx = movB.x, vecBy = movB.y;

		//compare values to check if collision occurs, if the other is getting away from the first,
		// his velocity is subtracted to see if any collision could occur in the case where the one getting away gets pushed back on the first
		float compAx = posAx, compAy = posAy, compBx = posBx, compBy = posBy;

		//if the velocity is going along the normal (going where bound is pointing at)
		if(signum(normalX) != signum(vecAx))
			//remove the velocity to feel the other body has it wasn't moving
			compAx -= vecAx;

		if(signum(normalY) != signum(vecAy))
			compAy -= vecAy;

		if(signum(-normalX) != signum(vecBx))
			compBx -= vecBx;

		if(signum(-normalY) != signum(vecBy))
			compBy -= vecBy;

		//if boundB with his movement isn't after boundA with his movement
		//(aka the bounds are still facing each other after having moved)
		if(!isGreaterOrEqual(compAx * normalX + compAy * normalY, compBx * normalX + compBy * normalY, epsilon))
			return null; //no collision


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

		//'previous' position is assumed to be the current position minus
		//the fake movement we just assumed. This fake previous position is
		//used to make sure no collision drops by collision shitfing
		float prevAx = posAx - vecAx;
		float prevAy = posAy - vecAy;
		float prevBx = posBx - vecBx;
		float prevBy = posBy - vecBy;

		//if boundB isn't before boundA
		//(aka the bounds at previous positions are not even facing each other)
		if(!isSmallerOrEqual(prevAx * normalX + prevAy * normalY, prevBx * normalX + prevBy * normalY, epsilon))
			return null;

		//half the size for A and B, used in further calculations
		float hsizeA = abs(normalY * boxA.width / 2 + normalX * boxA.height / 2);
		float hsizeB = abs(normalY * boxB.width / 2 + normalX * boxB.height / 2);

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

		collision.penetration = penetrationFormulas[keyOf(normalX, normalY)];

		//boing v2 priority algorithm
		collision.priority = surface * getPenetration(boxA, boxB, normalX, normalY);

		//contact surface at current position
		collision.contactSurface = contactSurfaceFormulas[keyOf(normalX, normalY)];

		collision.colliderA = boxA;
		collision.colliderB = boxB;
		collision.setImpactVelocities(boxA.getBody(), boxB.getBody());

		//DEBUG
		//if("DABOX".equals(boxA.getTag())
		//|| "DABOX".equals(boxB.getTag()))
		//	System.out.println((collision.normal.x != 0 ? "h" : "v") + collision.hashCode() + ": " + collision.contactSurface.getValue() + " p" + collision.priority);


		return collision;
	}

	public static float getPenetration(Box boxA, Box boxB, float normalX, float normalY)
	{
		return normalX * (boxA.getAbsX() + normalX * boxA.width / 2 - boxB.getAbsX() + normalX * boxB.width / 2)
				- normalY * (boxB.getAbsY() - normalY * boxB.height / 2 - boxA.getAbsY() - normalY * boxA.height / 2);
	}

	public static float getContactSurface(Box boxA, Box boxB, float normalX, float normalY)
	{
		float hsizeA = abs(normalY * boxA.width / 2 + normalX * boxA.height / 2);
		float hsizeB = abs(normalY * boxB.width / 2 + normalX * boxB.height / 2);

		float newAx = boxA.getAbsX() + normalX * boxA.width / 2;
		float newAy = boxA.getAbsY() + normalY * boxA.height / 2;
		float newBx = boxB.getAbsX() - normalX * boxB.width / 2;
		float newBy = boxB.getAbsY() - normalY * boxB.height / 2;

		return getContactSurface(newAx, newAy, hsizeA, newBx, newBy, hsizeB, normalX, normalY);
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

	private int keyOf(float nx, float ny)
	{
		if(nx == 1f)
			return 0;
		if(ny == 1f)
			return 1;
		if(nx == -1f)
			return 2;
		return 3;
	}
}