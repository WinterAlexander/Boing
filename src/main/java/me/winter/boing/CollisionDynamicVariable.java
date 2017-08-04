package me.winter.boing;

import me.winter.boing.colliders.Collider;
import me.winter.boing.util.WrapSharer;

/**
 * A variable within a collision, need
 * <p>
 * Created by Alexander Winter on 2017-06-10.
 */
@FunctionalInterface
public interface CollisionDynamicVariable
{
	/**
	 * Not thread safe
	 */
	@Deprecated
	public static final Inverter inverter = new Inverter();

	float getValue(Collider a, Collider b);

	public class Inverter extends WrapSharer<CollisionDynamicVariable, CollisionDynamicVariable>
	{
		@Override
		public CollisionDynamicVariable newObject(CollisionDynamicVariable content)
		{
			return (cA, cB) -> content.getValue(cB, cA);
		}
	}
}
