package me.winter.boing.physics.detection;

import com.badlogic.gdx.math.Vector2;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.shapes.Collider;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-12.
 */
public class DetectorSwapper<A extends Collider, B extends Collider> implements CollisionDetector<A, B>
{
	private CollisionDetector<B, A> detector;

	public DetectorSwapper(CollisionDetector<B, A> detector)
	{
		this.detector = detector;
	}

	@Override
	public Collision collides(A shapeA, B shapeB)
	{
		Collision collision = detector.collides(shapeB, shapeA);

		if(collision == null)
			return null;

		Object tmp = collision.normalA;
		collision.normalA = collision.normalB;
		collision.normalB = (Vector2)tmp;

		tmp = collision.impactVelA;
		collision.impactVelA = collision.impactVelB;
		collision.impactVelB = (Vector2)tmp;

		collision.colliderA = shapeA;
		collision.colliderB = shapeB;
		return collision;
	}
}
