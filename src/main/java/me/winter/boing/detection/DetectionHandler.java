package me.winter.boing.detection;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.Pool;
import me.winter.boing.Collision;
import me.winter.boing.CollisionDynamicVariable;
import me.winter.boing.World;
import me.winter.boing.detection.continuous.BoxBoxDetector;
import me.winter.boing.detection.continuous.BoxLimitDetector;
import me.winter.boing.detection.continuous.LimitLimitDetector;
import me.winter.boing.colliders.Box;
import me.winter.boing.colliders.Limit;
import me.winter.boing.colliders.Collider;
import me.winter.boing.util.Wrapper;

/**
 * Collision detection handler providing full collision detection by selecting the valid CollisionDetector.
 * <p>
 * Created by Alexander Winter on 2017-04-11.
 */
public class DetectionHandler
{
	private IntMap<CollisionDetector> detectors = new IntMap<>();

	/**
	 * Creates a DetectionHandler by creating a CollisionDetector for each type of collision supported
	 *
	 * @param collisionPool collisionPool to prevent creating new objects
	 */
	public DetectionHandler(Pool<Collision> collisionPool, Wrapper<CollisionDynamicVariable, CollisionDynamicVariable> variableWrapper)
	{
		//continuous.put(getKey(Circle.class, Circle.class), new CircleCircleDetector(collisionPool));
		//continuous.put(getKey(Circle.class, Box.class), new DetectorSwapper<>(new BoxCircleDetector(collisionPool)));
		//continuous.put(getKey(Circle.class, Limit.class), new CircleLimitDetector(collisionPool));

		//continuous.put(getKey(Box.class, Circle.class), new BoxCircleDetector(collisionPool));
		detectors.put(getKey(Box.class, Box.class), new BoxBoxDetector(collisionPool));
		detectors.put(getKey(Box.class, Limit.class), new BoxLimitDetector(collisionPool));

		//continuous.put(getKey(Limit.class, Circle.class), new DetectorSwapper<>(new CircleLimitDetector(collisionPool)));
		detectors.put(getKey(Limit.class, Box.class), new DetectorSwapper<>(new BoxLimitDetector(collisionPool), variableWrapper));
		detectors.put(getKey(Limit.class, Limit.class), new LimitLimitDetector(collisionPool));
	}

	/**
	 * Provides full collision detection between 2 generic colliders.
	 *
	 * @param colliderA first Collider
	 * @param colliderB second Collider
	 * @return the collision if there's one, otherwise null
	 */
	@SuppressWarnings("unchecked")
	public Collision collides(World world, Collider colliderA, Collider colliderB)
	{
		CollisionDetector detector = detectors.get(getKey(colliderA.getClass(), colliderB.getClass()));

		return detector.collides(world, colliderA, colliderB);
	}

	/**
	 * Gives the key from the 2 classes composing this key
	 * @param classA type of the first Collider
	 * @param classB type of the second Collider
	 * @return the key to access the CollisionDetector
	 */
	public int getKey(Class<? extends Collider> classA, Class<? extends Collider> classB)
	{
		return 713 + classA.hashCode() * 31 + classB.hashCode();
	}
}
