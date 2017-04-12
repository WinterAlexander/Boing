package me.winter.boing.physics.detection;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.physics.Collision;
import me.winter.boing.physics.detection.detectors.BoxBoxDetector;
import me.winter.boing.physics.detection.detectors.BoxCircleDetector;
import me.winter.boing.physics.detection.detectors.BoxLimitDetector;
import me.winter.boing.physics.detection.detectors.CircleCircleDetector;
import me.winter.boing.physics.detection.detectors.CircleLimitDetector;
import me.winter.boing.physics.detection.detectors.LimitLimitDetector;
import me.winter.boing.physics.shapes.AABB;
import me.winter.boing.physics.shapes.Circle;
import me.winter.boing.physics.shapes.Limit;
import me.winter.boing.physics.shapes.Collider;

/**
 * Undocumented :(
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class DetectionHandler
{
	private IntMap<CollisionDetector> detectors = new IntMap<>();
	
	public DetectionHandler(Pool<Collision> collisionPool)
	{
		detectors.put(getKey(Circle.class, Circle.class), new CircleCircleDetector(collisionPool));
		detectors.put(getKey(Circle.class, AABB.class), new DetectorSwapper<>(new BoxCircleDetector(collisionPool)));
		detectors.put(getKey(Circle.class, Limit.class), new CircleLimitDetector(collisionPool));

		detectors.put(getKey(AABB.class, Circle.class), new BoxCircleDetector(collisionPool));
		detectors.put(getKey(AABB.class, AABB.class), new BoxBoxDetector(collisionPool));
		detectors.put(getKey(AABB.class, Limit.class), new BoxLimitDetector(collisionPool));

		detectors.put(getKey(Limit.class, Circle.class), new DetectorSwapper<>(new CircleLimitDetector(collisionPool)));
		detectors.put(getKey(Limit.class, AABB.class), new DetectorSwapper<>(new BoxLimitDetector(collisionPool)));
		detectors.put(getKey(Limit.class, Limit.class), new LimitLimitDetector(collisionPool));
	}

	@SuppressWarnings("unchecked")
	public Collision collides(Collider colliderA, Collider colliderB)
	{
		CollisionDetector detector = detectors.get(getKey(colliderA.getClass(), colliderB.getClass()));

		return detector.collides(colliderA, colliderB);
	}

	public int getKey(Class<? extends Collider> classA, Class<? extends Collider> classB)
	{
		return 713 + classA.hashCode() * 31 + classB.hashCode();
	}
}
