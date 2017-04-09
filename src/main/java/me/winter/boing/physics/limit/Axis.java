package me.winter.boing.physics.limit;

import me.winter.boing.physics.IntVector;

/**
 * Represents an Axis and let you get the value of a vector for this particular axis
 */
public enum Axis
{
	X {
		@Override
		public int of(IntVector vector2)
		{
			return vector2.x;
		}

		@Override
		public void set(IntVector vector2, int value)
		{
			vector2.x = value;
		}
	},

	Y {
		@Override
		public int of(IntVector vector2)
		{
			return vector2.y;
		}

		@Override
		public void set(IntVector vector2, int value)
		{
			vector2.y = value;
		}
	};

	public abstract int of(IntVector vector3);
	public abstract void set(IntVector vector3, int value);
}
