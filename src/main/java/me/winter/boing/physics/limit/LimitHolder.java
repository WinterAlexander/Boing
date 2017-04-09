package me.winter.boing.physics.limit;

import me.winter.boing.physics.IntVector;

/**
 * A LimitHolder is something that holds limits (a box, a player).
 * Limit themselves keep a reference to their LimitHolder and use it to get their position.
 * <p>
 * Created by Alexander Winter on 2017-01-02.
 */
public interface LimitHolder
{
	/**
	 * @return the position of the LimitHolder
	 */
	IntVector getPosition();

	default IntVector getMovement()
	{
		return IntVector.ZERO;
	}

	default float weightFor(LimitHolder other)
	{
		return 0.5f;
	}
}
