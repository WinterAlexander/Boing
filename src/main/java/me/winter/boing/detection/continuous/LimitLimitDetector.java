package me.winter.boing.detection.continuous;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.World;
import me.winter.boing.colliders.Limit;
import me.winter.boing.detection.PooledDetector;

import static com.badlogic.gdx.math.Vector2.dot;
import static me.winter.boing.util.FloatUtil.DEFAULT_ULPS;
import static me.winter.boing.util.FloatUtil.areEqual;
import static me.winter.boing.util.FloatUtil.getGreatestULP;
import static me.winter.boing.util.FloatUtil.isGreaterOrEqual;
import static me.winter.boing.util.FloatUtil.isSmallerOrEqual;

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
	public Collision collides(World world, Limit limitA, Limit limitB)
	{
		if(!areEqual(limitA.normal.dot(limitB.normal), -1))
			return null;

		float normalX = limitA.normal.x; //normal X
		float normalY = limitA.normal.y; //normal Y

		//position of the limit
		float posAx = limitA.getAbsX();
		float posAy = limitA.getAbsY();

		//same for b
		float posBx = limitB.getAbsX();
		float posBy = limitB.getAbsY();

		//movement of the bodies seen from each other
		float vecAx, vecAy, vecBx, vecBy;

		Vector2 shiftA = limitA.getCollisionShifting(world);
		Vector2 shiftB = limitB.getCollisionShifting(world);

		Vector2 movA = limitA.getMovement(world);
		Vector2 movB = limitB.getMovement(world);

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

		float epsilon = DEFAULT_ULPS * getGreatestULP(posAx, posAy, posBx, posBy, vecAx, vecAy, vecBx, vecBy, limitA.size, limitB.size);

		//if limitB with his movement isn't after limitA with his movement
		//(aka the limits are still facing each other after having moved)
		if(!isGreaterOrEqual(posAx * normalX + posAy * normalY, posBx * normalX + posBy * normalY, epsilon))
			return null; //no collision

		//'previous' position is assumed to be the current position minus
		//the fake movement we just assumed. This fake previous position is
		//used to make sure no collision drops by collision shitfing
		float prevAx = posAx - vecAx;
		float prevAy = posAy - vecAy;
		float prevBx = posBx - vecBx;
		float prevBy = posBy - vecBy;

		//if limitB isn't before limitA
		//(aka the limits at previous positions are not even facing each other)
		if(!isSmallerOrEqual(prevAx * normalX + prevAy * normalY, prevBx * normalX + prevBy * normalY, epsilon)) //if limitB isn't before limitA
			return null; //no collision

		float hsizeA = limitA.size / 2; //half size for A
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

		collision.normal.set(normalX, normalY);

		collision.penetration = (cA, cB) -> getPenetration((Limit)cA, (Limit)cB);

		//boing v2 priority algorithm
		collision.priority = surface * getPenetration(limitA, limitB);


		//re-get the position from the original calculation
		//since we are in a CollisionDynamicVariable, posAx, posAy etc. might
		//be outdated (cached values)
		collision.contactSurface = (cA, cB) -> getContactSurface((Limit)cA, (Limit)cB);


		collision.colliderA = limitA;
		collision.colliderB = limitB;
		collision.setImpactVelocities(limitA.getBody(), limitB.getBody());

		return collision;
	}

	public static float getPenetration(Limit limitA, Limit limitB)
	{
		return -((limitB.getAbsX() - limitA.getAbsX()) * limitA.normal.x
				+ (limitB.getAbsY() - limitA.getAbsY()) * limitA.normal.y);
	}

	public static float getContactSurface(Limit limitA, Limit limitB)
	{
		return BoxBoxDetector.getContactSurface(limitA.getAbsX(), limitA.getAbsY(), limitA.size / 2, limitB.getAbsX(), limitB.getAbsY(), limitB.size / 2, limitA.normal.x, limitA.normal.y);
	}
}
