package me.winter.boing.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.shapes.AABB;
import me.winter.boing.physics.shapes.Circle;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static java.lang.Math.abs;
import static java.lang.Math.signum;
import static java.lang.Math.sqrt;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class CollisionDetection
{
	private CollisionDetection() {} //static class

	public static Collision circleCircle(Circle circleA, Circle circleB, Pool<Collision> collisionPool)
	{
		float dx = circleB.getAbsX() - circleA.getAbsX();
		float dy = circleB.getAbsY() - circleA.getAbsY();

		float r = circleA.radius + circleB.radius;

		float dst2 = dx * dx + dy * dy;

		if(dst2 >= r * r)
			return null;

		Collision collision = collisionPool.obtain();

		collision.normalA.set(dx, dy);
		collision.normalB.set(-dx, -dy);
		collision.penetration = r - (float)sqrt(dst2);
		//collision.contact.set(-dx, -dy).scl(circleA.radius / r).add(circleA.getAbsX(), circleA.getAbsY());

		return collision;
	}

	public static Collision boxBox(AABB boxA, AABB boxB, Pool<Collision> collisionPool)
	{
		float dx = boxB.getAbsX() - boxA.getAbsX();
		float dy = boxB.getAbsY() - boxA.getAbsY();

		float peneX = boxA.width / 2 + boxB.width / 2 - abs(dx);
		float peneY = boxA.height / 2 + boxB.height / 2 - abs(dy);

		if(peneX < 0 || peneY < 0)
			return null;

		Collision collision = collisionPool.obtain();

		if(peneX > peneY)
		{
			collision.normalA.set(dx, 0);
			collision.normalB.set(-dx, 0);
			collision.penetration = peneX;
			//collision.contact.set(signum(dx) * boxA.width / 2, 0).add(boxA.getAbsX(), boxA.getAbsY());
		}
		else
		{
			collision.normalA.set(0, dy);
			collision.normalB.set(0, -dy);
			collision.penetration = peneY;
			//collision.contact.set(0, signum(dy) * boxA.height / 2).add(boxA.getAbsX(), boxA.getAbsY());
		}

		return collision;
	}

	public static Collision boxCircle(AABB boxA, Circle circleB, Pool<Collision> collisionPool)
	{
		float dx = circleB.getAbsX() - boxA.getAbsX();
		float dy = circleB.getAbsY() - boxA.getAbsY();

		float absDx = abs(dx);
		float absDy = abs(dy);

		float halfW = boxA.width / 2;
		float halfH = boxA.height / 2;

		//stupid box box collision outside
		if(absDx > halfW + circleB.radius
		|| absDy > halfH + circleB.radius)
			return null;

		//stupid box box collision inside (inverted if)
		if(!(absDx < halfW || absDy < halfH) //if it's not inside
		&& (absDx - halfW) * (absDx - halfW) + (absDy - halfH) * (absDy - halfH) >= circleB.radius * circleB.radius) //and it's not in the middle
			return null;

		float closestX = clamp(dx, -halfW, halfW);
		float closestY = clamp(dy, -halfH, halfH);

		Collision collision = collisionPool.obtain();

		if(absDx - halfW > absDy - halfH)
		{
			closestX = signum(closestX) * halfW;

			collision.normalA.set(dx, 0);
			collision.normalB.set(-dx, 0); //todo better normal detection for circle
			collision.penetration = (float)sqrt((dx - closestX) * (dx - closestX) + (dy - closestY) * (dy - closestY));
			//collision.contact.set(signum(dx) * halfW, 0).add(boxA.getAbsX(), boxA.getAbsY());
		}
		else
		{
			closestY = signum(closestY) * halfH;

			collision.normalA.set(0, dy);
			collision.normalB.set(0, -dy);
			collision.penetration = (float)sqrt((dx - closestX) * (dx - closestX) + (dy - closestY) * (dy - closestY));
			//collision.contact.set(0, signum(dy) * halfH).add(boxA.getAbsX(), boxA.getAbsY());
		}

		return collision;
	}


	public static Collision circleBox(Circle circleA, AABB boxB, Pool<Collision> collisionPool)
	{
		Collision collision = boxCircle(boxB, circleA, collisionPool);

		if(collision == null)
			return null;

		Vector2 tmp = collision.normalA;
		collision.normalA = collision.normalB;
		collision.normalB = tmp;
		return collision;
	}
}
