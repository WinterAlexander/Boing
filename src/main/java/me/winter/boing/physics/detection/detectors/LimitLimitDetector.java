package me.winter.boing.physics.detection.detectors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.DynamicSolid;
import me.winter.boing.physics.detection.PooledDetector;
import me.winter.boing.physics.shapes.Limit;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class LimitLimitDetector extends PooledDetector<Limit, Limit>
{
	private Vector2 tmpVector = new Vector2(), tmpVector2 = new Vector2();

	public LimitLimitDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
	}

	@Override
	public Collision collides(Limit shapeA, Limit shapeB)
	{
		return null;/*
		if(shapeA.normal.dot(shapeB.normal) >= 0)
			return null;

		int dir = forward ? -1 : 1;

		Vector2 thisVec = shapeA.getSolid() instanceof DynamicSolid
				? ((DynamicSolid)shapeA.getSolid()).getMovement()
				: Vector2.Zero;

		Vector2 thatVec = shapeB.getSolid() instanceof DynamicSolid
				? ((DynamicSolid)shapeB.getSolid()).getMovement()
				: Vector2.Zero;

		int thisPos = axisValue();
		int thatPos = that.axisValue();

		if(!(thisPos * dir <= thatPos * dir
		&& (thisPos + axis.of(thisVec)) * dir > (thatPos + axis.of(thatVec)) * dir))
			return null;

		int diff = thatPos - thisPos;

		//finding the collision point
		tmpVector.set(thisVec).scl(diff).divide(axis.of(thisVec) - axis.of(thatVec));
		tmpVector2.set(thatVec).scl(diff).divide(axis.of(thisVec) - axis.of(thatVec));

		if(!contains(shapeA, shapeB, tmpVector, tmpVector2)) //and it was in bounds at the impact point
		{
			//finding the collision point + 1 (to prevent the corner glitch)
			tmpVector.scl((diff + dir) / diff);
			tmpVector2.scl((diff + dir) / diff);

			if(!contains(shapeA, shapeB, tmpVector, tmpVector2))
				return null;
		}

		Collision collision = collisionPool.obtain();

		collision.colliderA = shapeA;
		collision.colliderB = shapeB;

		collision.normalA = shapeA.normal;
		collision.normalB = shapeB.normal;
		collision.setImpactVelocities(shapeA.getSolid(), shapeB.getSolid());

		return collision;*/
	}

	private boolean contains(Limit limitA, Limit limitB, Vector2 offsetA, Vector2 offsetB)
	{
		/*
		for(Axis axis : Axis.values()) //for all axes
		{
			if(this.axis == axis) //but the one of this limit
				continue;

			if(axis.of(limit.end()) + axis.of(otherOffset) <= axis.of(start()) + axis.of(thisOffset)
					|| axis.of(limit.start()) + axis.of(otherOffset) >= axis.of(end()) + axis.of(thisOffset))
				return false;
		}*/

		return true;
	}
}
