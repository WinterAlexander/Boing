package me.winter.boing.detection.continuous;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.World;
import me.winter.boing.colliders.Box;
import me.winter.boing.detection.PooledDetector;
import me.winter.boing.util.DynamicFloat;

import static com.badlogic.gdx.math.Vector2.dot;
import static java.lang.Math.abs;
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
	private me.winter.boing.detection.simple.BoxBoxDetector simple;

	public BoxBoxDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
		simple = new me.winter.boing.detection.simple.BoxBoxDetector(collisionPool);
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
				if(collision.priority < coll2.priority) //take the one with higher priority
					collision = coll2;
			}
		}

		return collision;
	}

	/**
	 * Checks for collision for a specific side of the 2 boxes (check for collisions as limit)
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
		//position of the limit (position of box + extend of the box)
		final float posAx = boxA.getAbsX() + normalX * boxA.width / 2;
		final float posAy = boxA.getAbsY() + normalY * boxA.height / 2;

		//same for b
		final float posBx = boxB.getAbsX() - normalX * boxB.width / 2;
		final float posBy = boxB.getAbsY() - normalY * boxB.height / 2;

		//if limitB with his movement isn't after limitA with his movement
		//(aka the limits are still facing each other after having moved)
		if(!isGreaterOrEqual(posAx * normalX + posAy * normalY, posBx * normalX + posBy * normalY, epsilon))
			return null; //no collision

		//true if limits are touching after their movement
		//(penetration about 0, just touching nothing else)
		final boolean justTouching = areEqual(posAx * normalX + posAy * normalY, posBx * normalX + posBy * normalY, epsilon);

		//movement of the bodies seen from each other
		final float vecAx, vecAy, vecBx, vecBy;

		//if collision shifting of A is going along it's normal
		if(dot(normalX, normalY, shiftA.x, shiftA.y) > 0)
		{
			//then expect it to be pushed to there this frame to
			//(we assume its getting pushed by something)
			vecAx = movA.x + shiftA.x;
			vecAy = movA.y + shiftA.y;
		}
		else
		{
			//else, collision shifting is going in the other direction
			//ignoring it is safer since we don't know for sure if
			//it will get pushed this frame too or if the pusher is B
			//(in the case were the pusher is B, this collision must happen
			//for the pushing not to drop)
			vecAx = movA.x;
			vecAy = movA.y;
		}

		//same for B, B's normal is the opposite of A
		if(dot(-normalX, -normalY, shiftB.x, shiftB.y) > 0)
		{
			vecBx = movB.x + shiftB.x;
			vecBy = movB.y + shiftB.y;
		}
		else
		{
			vecBx = movB.x;
			vecBy = movB.y;
		}

		//'previous' position is assumed to be the current position minus
		//the fake movement we just assumed. This fake previous position is
		//used to make sure no collision drops by collision shitfing
		float prevAx = posAx - vecAx;
		float prevAy = posAy - vecAy;
		float prevBx = posBx - vecBx;
		float prevBy = posBy - vecBy;

		//if limitB isn't before limitA
		//(aka the limits at previous positions are not even facing each other)
		if(!isSmallerOrEqual(prevAx * normalX + prevAy * normalY, prevBx * normalX + prevBy * normalY, epsilon))
			return null;

		//half the size for A and B, used in further calculations
		final float hsizeA = abs(normalY * boxA.width / 2 + normalX * boxA.height / 2);
		final float hsizeB = abs(normalY * boxB.width / 2 + normalX * boxB.height / 2);

		//contact surface, used to detect if limits are on the same plane and
		//to fullfill the collision report
		float surface;

		//if they are just touching after movement
		/*if(justTouching)  //doesn't seem to work or to make sense, don't remember the intention too
		{
			//get surface contact when limits were at their previous position
			surface = getContactSurface(prevAx, prevAy, hsizeA, prevBx, prevBy, hsizeB, normalX, normalY);

			//if nothing (TODO why does this make sense?)
			if(isSmallerOrEqual(surface, 0, epsilon))
				return null;
		}*/

		DynamicFloat surfaceFormula = () -> {

			//re-get the position from the original calculation
			//since we are in a DynamicFloat, posAx, posAy etc. might
			//be outdated (cached values)
			float newAx = boxA.getAbsX() + normalX * boxA.width / 2;
			float newAy = boxA.getAbsY() + normalY * boxA.height / 2;
			float newBx = boxB.getAbsX() - normalX * boxB.width / 2;
			float newBy = boxB.getAbsY() - normalY * boxB.height / 2;

			float diff = ((newBx - vecBx) - (newAx - vecAx)) * normalX + ((newBy - vecBy) - (newAy - vecAy)) * normalY;
			float vecDiff = (vecBx - vecAx) * normalX + (vecBy - vecAy) * normalY;

			float midpoint = vecDiff != 0 ? (diff + vecDiff) / vecDiff : 0f;

			float midAx = newAx - vecAx * midpoint; //midpoint x for A
			float midAy = newAy - vecAy * midpoint; //midpoint y for A
			float midBx = newBx - vecBx * midpoint; //midpoint x for B
			float midBy = newBy - vecBy * midpoint; //midpoint y for B

			return getContactSurface(midAx, midAy, hsizeA, midBx, midBy, hsizeB, normalX, normalY);
		};

		//calculates surface from formula
		surface = surfaceFormula.getValue();

		//if 0, it might be a corner corner case
		if(areEqual(surface, 0, epsilon))
		{
			/*surfaceFormula = () -> {

				float newAx = boxA.getAbsX() + normalX * boxA.width / 2;
				float newAy = boxA.getAbsY() + normalY * boxA.height / 2;
				float newBx = boxB.getAbsX() - normalX * boxB.width / 2;
				float newBy = boxB.getAbsY() - normalY * boxB.height / 2;

				float diff = ((newBx - vecBx) - (newAx - vecAx)) * normalX + ((newBy - vecBy) - (newAy - vecAy)) * normalY;
				float vecDiff = (vecBx - vecAx) * normalX + (vecBy - vecAy) * normalY;
				float sizeDiff = (hsizeB + hsizeA) * abs(normalX) + (hsizeB + hsizeA) * abs(normalY);

				float midpoint = vecDiff != 0 ? (diff + vecDiff + sizeDiff) / vecDiff : 0f;

				float midAx = newAx - vecAx * midpoint; //midpoint x for A
				float midAy = newAy - vecAy * midpoint; //midpoint y for A
				float midBx = newBx - vecBx * midpoint; //midpoint x for B
				float midBy = newBy - vecBy * midpoint; //midpoint y for B

				return getContactSurface(midAx, midAy, hsizeA, midBx, midBy, hsizeB, normalX, normalY);
			};

			surface = surfaceFormula.getValue();

			if(isSmallerOrEqual(surface, 0, epsilon))*/
				return null;
		}
		else if(surface < 0)
			return null;

		Collision collision = collisionPool.obtain();

		collision.normal.set(normalX, normalY);

		collision.penetration = () -> -((boxB.getAbsX() - normalX * boxB.width / 2 - (boxA.getAbsX() + normalX * boxA.width / 2)) * normalX
										+ (boxB.getAbsY() - normalY * boxB.height / 2 - (boxA.getAbsY() + normalY * boxA.height / 2)) * normalY);

		//boing v1 priority algorithm
		collision.priority = ((posBx - posAx) * normalX + (posBy - posAy) * normalY) / ((vecBx - vecAx) * normalX + (vecBy - vecAy) * normalY);

		collision.contactSurface = surfaceFormula;

		collision.colliderA = boxA;
		collision.colliderB = boxB;
		collision.setImpactVelocities(boxA.getBody(), boxB.getBody());

		//DEBUG
		//if("DABOX".equals(boxA.getTag())
		//|| "DABOX".equals(boxB.getTag()))
		//	System.out.println((collision.normal.x != 0 ? "h" : "v") + collision.hashCode() + ": " + collision.contactSurface.getValue() + " p" + collision.priority);


		return collision;
	}

	/**
	 * Finds the contact surface between 2 limits at position (ax, ay) and (bx, by)
	 *
	 * @param ax x position of limit A
	 * @param ay y position of limit A
	 * @param hsA half the size of limit A
	 *
	 * @param bx x position of limit B
	 * @param by y position of limit B
	 * @param hsB half size of limit B
	 *
	 * @param nx normal x of the limit A
	 * @param ny normal y of the limit A
	 *
	 * @return how much of their surface match in relation to the normal
	 */
	public float getContactSurface(float ax, float ay, float hsA,
	                                 float bx, float by, float hsB,
	                                 float nx, float ny)
	{
		float limitA1 = -ny * (ax + hsA) + nx * (ay + hsA);
		float limitA2 = -ny * (ax - hsA) + nx * (ay - hsA);
		float limitB1 = -ny * (bx + hsB) + nx * (by + hsB);
		float limitB2 = -ny * (bx - hsB) + nx * (by - hsB);

		return min(max(limitA1, limitA2), max(limitB1, limitB2)) //minimum of the maximums
				- max(min(limitA1, limitA2), min(limitB1, limitB2)); //maximum of the minimums
	}
}