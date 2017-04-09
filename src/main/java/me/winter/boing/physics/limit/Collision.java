package me.winter.boing.physics.limit;

/**
 * Represents a collision or contact between 2 limits,
 * simple object with the only purpose to keep pairs of limits for later use
 * <p>
 * Created by Alexander Winter on 2016-12-04.
 */
public class Collision
{
	private Limit limitA, limitB;

	public Collision() {}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Collision)
			return ((Collision)obj).limitA == limitA && ((Collision)obj).limitB == limitB;

		return super.equals(obj);
	}

	@Override
	public String toString()
	{
		return "A: " + limitA + " B: " + limitB;
	}

	public void set(Limit limitA, Limit limitB)
	{
		this.limitA = limitA;
		this.limitB = limitB;
	}

	public Limit getLimitA()
	{
		return limitA;
	}

	public Limit getLimitB()
	{
		return limitB;
	}
}
