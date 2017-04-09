package me.winter.boing.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Mutable vector class for movements and positions
 * <p>
 * Created by Alexander Winter on 2016-10-10 at 12:38.
 */
public class IntVector
{
	public static final IntVector ZERO = new IntVector(0, 0);
	public int x, y;

	public IntVector()
	{
		set(0, 0);
	}

	public IntVector(int x, int y)
	{
		set(x, y);
	}

	public IntVector(IntVector vec)
	{
		set(vec);
	}

	public IntVector(IntVector loc1, IntVector loc2)
	{
		this.x = loc2.x - loc1.x;
		this.y = loc2.y - loc1.y;
	}

	public boolean equals(int x, int y)
	{
		return this.x == x && this.y == y;
	}

	public boolean equals(float x, float y)
	{
		return this.x == Math.round(x) && this.y == Math.round(y);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof IntVector)
			return equals(((IntVector)obj).x, ((IntVector)obj).y);

		if(obj instanceof Vector3)
			return equals(((Vector3)obj).x, ((Vector3)obj).y);

		if(obj instanceof Vector2)
			return equals(((Vector2)obj).x, ((Vector2)obj).y);

		return super.equals(obj);
	}

	@Override
	public String toString()
	{
		return "IntVector(" + x + ", " + y + ")";
	}

	public double length()
	{
		return Math.sqrt(lengthSquared());
	}

	public int lengthSquared()
	{
		return x * x + y * y;
	}

	public IntVector add(int x, int y)
	{
		this.x += x;
		this.y += y;
		return this;
	}

	public IntVector add(Vector2 vec, float scale)
	{
		return add((int)(vec.x * scale), (int)(vec.y * scale));
	}

	public IntVector add(IntVector vector)
	{
		return add(vector.x, vector.y);
	}

	public IntVector substract(int x, int y)
	{
		this.x -= x;
		this.y -= y;
		return this;
	}

	public IntVector substract(IntVector vector)
	{
		return substract(vector.x, vector.y);
	}

	public IntVector scale(double scalar)
	{
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}

	public IntVector divide(double scalar)
	{
		this.x /= scalar;
		this.y /= scalar;
		return this;
	}

	public IntVector normalize()
	{
		double length = length();

		this.x /= length;
		this.y /= length;
		return this;
	}

	public boolean isNull()
	{
		return (x | y) == 0;
	}

	@Override
	public IntVector clone()
	{
		return new IntVector(this);
	}

	public IntVector copyTo(IntVector destination)
	{
		return destination.set(this);
	}

	public Vector2 copyTo(Vector2 destination)
	{
		return destination.set(x, y);
	}

	public Vector2 copyTo(Vector2 destination, float scale)
	{
		return destination.set(x * scale, y * scale);
	}

	public Vector3 copyTo(Vector3 destination)
	{
		return destination.set(x, y, 0);
	}

	public Vector3 copyTo(Vector3 destination, float scale)
	{
		return destination.set(x * scale, y * scale, 0);
	}

	public IntVector floor(float x, float y)
	{
		this.x = MathUtils.floor(x);
		this.y = MathUtils.floor(y);
		return this;
	}

	public IntVector floor(float x, float y, float scale)
	{
		this.x = MathUtils.floor(x * scale);
		this.y = MathUtils.floor(y * scale);
		return this;
	}

	public IntVector floor(Vector2 gdxVec)
	{
		return floor(gdxVec.x, gdxVec.y);
	}

	public IntVector floor(Vector2 gdxVec, float scale)
	{
		return floor(gdxVec.x, gdxVec.y, scale);
	}

	public IntVector floor(Vector3 gdxVec)
	{
		return floor(gdxVec.x, gdxVec.y);
	}

	public IntVector floor(Vector3 gdxVec, float scale)
	{
		return floor(gdxVec.x, gdxVec.y, scale);
	}

	public IntVector set(int x, int y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	public IntVector set(IntVector vec)
	{
		return set(vec.x, vec.y);
	}

	public IntVector round(float x, float y)
	{
		this.x = Math.round(x);
		this.y = Math.round(y);
		return this;
	}

	public IntVector round(Vector2 gdxVec)
	{
		return round(gdxVec.x, gdxVec.y);
	}

	public IntVector round(Vector2 gdxVec, float scale)
	{
		return round(gdxVec.x * scale, gdxVec.y * scale);
	}


	public IntVector round(Vector3 gdxVec)
	{
		return round(gdxVec.x, gdxVec.y);
	}

	public IntVector round(Vector3 gdxVec, float scale)
	{
		return round(gdxVec.x * scale, gdxVec.y * scale);
	}

}
