package me.winter.boing;

import me.winter.boing.colliders.Collider;

/**
 * A variable within a collision, need
 * <p>
 * Created by Alexander Winter on 2017-06-10.
 */
@FunctionalInterface
public interface CollisionDynamicVariable
{
	float getValue(Collider a, Collider b);

	public class Inverter implements CollisionDynamicVariable
	{
		private CollisionDynamicVariable var;

		public Inverter(CollisionDynamicVariable var)
		{
			this.var = var;
		}

		@Override
		public float getValue(Collider a, Collider b)
		{
			return var.getValue(b, a);
		}
	}
}
