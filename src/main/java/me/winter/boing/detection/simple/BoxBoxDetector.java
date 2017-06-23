package me.winter.boing.detection.simple;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.World;
import me.winter.boing.colliders.Box;
import me.winter.boing.detection.PooledDetector;
import me.winter.boing.util.DynamicFloat;

import static java.lang.Math.abs;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-05-23.
 */
public class BoxBoxDetector extends PooledDetector<Box, Box>
{
	public BoxBoxDetector(Pool<Collision> collisionPool)
	{
		super(collisionPool);
	}

	@Override
	public Collision collides(World world, Box shapeA, Box shapeB)
	{
		float dx = shapeB.getAbsX() - shapeA.getAbsX();
		float dy = shapeB.getAbsY() - shapeA.getAbsY();

		DynamicFloat peneX = () -> shapeA.width / 2 + shapeB.width / 2 - abs(dx);
		DynamicFloat peneY = () -> shapeA.height / 2 + shapeB.height / 2 - abs(dy);

		float peneXVal = peneX.getValue();
		float peneYVal = peneY.getValue();

		if(peneXVal < 0 || peneYVal < 0)
			return null;

		Collision collision = collisionPool.obtain();

		if(peneXVal < peneYVal)
		{
			collision.normal.set(dx < 0 ? -1 : 1, 0);
			collision.penetration = peneX;
			collision.contactSurface = peneY;
		}
		else
		{
			collision.normal.set(0, dy < 0 ? -1 : 1);
			collision.penetration = peneY;
			collision.contactSurface = peneX;
		}

		collision.colliderA = shapeA;
		collision.colliderB = shapeB;
		collision.setImpactVelocities(shapeA.getBody(), shapeB.getBody());

		return collision;
	}
}
