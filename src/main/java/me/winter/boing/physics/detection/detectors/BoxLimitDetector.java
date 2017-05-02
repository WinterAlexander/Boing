package me.winter.boing.physics.detection.detectors;

import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.detection.PooledDetector;
import me.winter.boing.physics.shapes.Box;
import me.winter.boing.physics.shapes.Limit;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

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
	public Collision collides(Box boxA, Limit limitB)
	{
		float dx = limitB.getAbsX() - boxA.getAbsX();
		float dy = limitB.getAbsY() - boxA.getAbsY();

		float limitW = limitB.size * limitB.normal.y;
		float limitH = limitB.size * limitB.normal.x;

		float peneX = boxA.width / 2 + limitW / 2 - abs(dx);
		float peneY = boxA.height / 2 + limitH / 2 - abs(dy);

		if(peneX < 0 || peneY < 0)
			return null;

		Collision collision = collisionPool.obtain();

		if(peneX < peneY)
		{
			collision.normalA.set(dx < 0 ? -1 : 1, 0);
			collision.normalB.set(limitB.normal);
			collision.penetration = peneX;
			collision.contactSurface = peneY;
		}
		else
		{
			collision.normalA.set(0, dy < 0 ? -1 : 1);
			collision.normalB.set(limitB.normal);
			collision.penetration = peneY;
			collision.contactSurface = peneX;
		}

		collision.colliderA = boxA;
		collision.colliderB = limitB;
		collision.setImpactVelocities(boxA.getBody(), limitB.getBody());

		return collision;
	}
}